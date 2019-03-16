package weixinapp.dao;

import weixinapp.util.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

import weixinapp.model.*;

public class ConversationDAO {
	
	private static ArrayList<Conversation> select(String sql, Object[] params) {
		ArrayList<Conversation> list = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = JDBCUtil.getConn();
		try{
			stmt = conn.prepareStatement(sql);
			for(int i = 0; i < params.length; ++i){
				stmt.setObject(i + 1, params[i]);
			}
			rs = stmt.executeQuery();
			while(rs.next()){
				Conversation conv = new Conversation();
				conv.setConv_id(rs.getInt("conv_id"));
				conv.setUser_src(rs.getInt("user_src"));
				conv.setUser_des(rs.getInt("user_des"));
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getObject("time")); // 将数据库中取出的时间后的【.0】去掉
				conv.setTime(LocalDateTime.parse(time, FormatterUtil.formatter)); // 将String 转为 LocalDateTime以赋值到content
				conv.setContent(rs.getString("content"));
				
				list.add(conv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConn(rs, stmt, conn);
		}

		return list;
	}
	
	public static ArrayList<Conversation> selectByID(int user_src, int user_des) {
		ArrayList<Conversation> list = new ArrayList<>();
		String sql = "select * " +
				     "from conversation " +
					 "where (user_src = ? and user_des = ?) or (user_src = ? and user_des = ?) " +
				     "order by conversation.time asc";
				     
		list = select(sql, new Object[] {user_src, user_des, user_des, user_src});
		return list;
	}

	public static ArrayList<Conversation> selectLatest(int user_lower) {
		ArrayList<Conversation> list = new ArrayList<> ();
		String sql = "select * from conversation " + 
					 "where (user_src = ? and user_des = ?) or (user_src = ? and user_des = ?) " + 
					 "order by conversation.time desc " + 
					 "limit 0, 1";

		// 选出和user_lower正在聊天的用户
		ArrayList<Integer> chaings = ConvStatusDAO.selectChatingWith(user_lower);

		// 每次选出和user_upper的最新的聊天记录，并合并list
		for (int user_upper : chaings) {
			list.addAll( select(sql, new Object[] {user_lower, user_upper, user_upper, user_lower}) );
		}
		
		return list;
	}

	static private boolean update(String sql, Object[] para) {
		boolean ok = false;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preStmt = null;
		try {
			preStmt = conn.prepareStatement(sql);
			for(int i = 0; i < para.length; ++i){
				preStmt.setObject(i + 1, para[i]);
			}
			preStmt.executeUpdate();
			ok = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConn(preStmt, conn);
		}
		return ok;
	}

	// 在往Conversation表中插入消息时，会自动往conv_status中插入一条记录，表示这两个人正在聊天
	public static boolean insertConversation(Conversation conv) {
		String sql = "INSERT INTO `weixindb`.`conversation` (`conv_id`, `user_src`, `user_des`, `time`, `content`, `been_read`) " +
					 "VALUES (?, ?, ?, ?, ?, ?);"; 

		boolean ok1 = update(sql, 
				new Object[] {conv.getConv_id(), conv.getUser_src(), conv.getUser_des(), conv.getTime(), conv.getContent(), conv.isBeen_read()}); 

		int user_lower = conv.getUser_src();
		int user_upper = conv.getUser_des();
		if (user_lower > user_upper) {
			int tmp = user_lower; user_lower = user_upper; user_upper = tmp;
		}

		boolean ok2 = ConvStatusDAO.insertConvStatus(user_lower, user_upper);
		
		return ok1 && ok2;
	}

	
	
	
}
