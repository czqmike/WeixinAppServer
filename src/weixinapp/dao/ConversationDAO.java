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
				     "order by conv_id asc";
				     
		list = select(sql, new Object[] {user_src, user_des, user_des, user_src});
		return list;
	}

	// TODO: Add Insert into conversation

}
