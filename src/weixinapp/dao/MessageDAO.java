package weixinapp.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

import weixinapp.model.Message;
import weixinapp.util.FormatterUtil;
import weixinapp.util.JDBCUtil;

public class MessageDAO {

	public static ArrayList Select(String sql, Object[] values){
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preStmt = null;
		ArrayList<Message> list = new ArrayList<Message>();
		ResultSet rs = null;
		try{
			preStmt = conn.prepareStatement(sql);
			for(int i = 0; i < values.length; ++i){
				preStmt.setObject(i + 1, values[i]);
			}
			rs = preStmt.executeQuery();
			while(rs.next()){
				Message message = new Message();
				message.setMessage_id(rs.getInt("message_id"));
				message.setGood_id(rs.getInt("good_id"));
				message.setUser_src(rs.getInt("user_src"));
				message.setUser_des(rs.getInt("user_des"));
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getObject("time")); // 将数据库中取出的时间后的【.0】去j掉
				message.setTime(LocalDateTime.parse(time, FormatterUtil.formatter)); // 将String 转为 LocalDateTime以赋值到message
				message.setContent(rs.getString("content"));
				
				// *************************************************************
				// 添加了获取发信人名称与头像的代码
				String user_name = null;
				String avatar_url = null;
				try {
					user_name = rs.getString("user_name");
					avatar_url = rs.getString("avatar_url");
				} catch(SQLException e) {
				}
				if (user_name != null && avatar_url != null) {
					message.setUser_name(user_name);
					message.setAvatar_url(avatar_url);
				}
				// *************************************************************
				
				list.add(message);
			}
		} catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConn(rs, preStmt, conn);
		}
		return list;
	}
	
	private static int Insert(String sql, Object[] values){
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preStmt = null;
		int successCount = 0;
		try {
			preStmt = conn.prepareStatement(sql);
			for (int i = 0; i < values.length; ++i){
				preStmt.setObject(i + 1, values[i]);
			}
			preStmt.executeUpdate();
			successCount = preStmt.getUpdateCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConn(preStmt, conn);
		}
		return successCount;
	}
	
	public static ArrayList selectByUserDes(int user_des){
		String sql = "select * from message where user_des=?";
		Object[] values = new Object[] {user_des};
		ArrayList list = Select(sql,values);
		return list;
	}
	
	public static ArrayList<Message> selectByGoodId(int good_id) {
//		String sql = "select * from weixindb.message where message.good_id = ? ";
		// 选出含有留言人用户名和头像的message信息
		String sql = "select message.*, user.user_name, user.avatar_url " +
					 "from message, user " + 
					 "where message.user_src = user.user_id AND message.good_id = ? ";
		Object[] values = new Object[] {good_id};
		ArrayList list = Select(sql, values);
		return list;
	}

	public static int InsertMessage(Message message){
		String sql = "insert into message(good_id, user_src, user_des, time, content) values(?,?,?,?,?)";
		Object[] values = new Object[] {message.getGood_id(), message.getUser_src(), message.getUser_des(),
									    message.getTime(), message.getContent()}; // 将message中的LocalDateTime转为String
		int successCount = Insert(sql,values);
		return successCount;
	}
	
}
