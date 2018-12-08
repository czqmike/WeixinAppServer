package weixinapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import weixinapp.model.User;
import weixinapp.util.JDBCUtil;

public class UserDAO {
	static public int Select(String sql, Object[] values){		
		Connection conn = JDBCUtil.getConn();	
		ResultSet rs = null;
		int user_id = -1;
		PreparedStatement preStmt = null;
		try {
			preStmt = conn.prepareStatement(sql);
			for(int i = 0; i < values.length; ++i){
				preStmt.setObject(i + 1, values[i]);
			}
			rs = preStmt.executeQuery();
			if(rs.next()){
				user_id = rs.getInt("user_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCUtil.closeConn(rs, preStmt, conn);
		}
		return user_id;
	}

	static public int selectByOpenId(String open_id){
		String sql = "select user_id from user where open_id=?";
		Object[] values = new Object[] {open_id};
		int id = Select(sql, values);
		return id;
	}
	
	public static User selectByUserId(int user_id){
		Connection conn = JDBCUtil.getConn();	
		ResultSet rs = null;
		PreparedStatement preStmt = null;
		User user = null;
		String sql = "select * from user where user_id=?";
		try {
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, user_id);
			rs = preStmt.executeQuery();
			if(rs.next()){
				user = new User();
				user.setUser_id(rs.getInt("user_id"));
				user.setUser_name(rs.getString("user_name"));
				user.setSex(rs.getInt("sex"));
				user.setTel(rs.getString("tel"));
				user.setMail(rs.getString("mail"));
				user.setAddress(rs.getString("address"));
				user.setWeixin_no(rs.getString("weixin_no"));
				user.setAvatar_url(rs.getString("avatar_url"));
				user.setCredit_point(rs.getInt("credit_point"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JDBCUtil.closeConn(rs, preStmt, conn);
		}
		return user;
	}

	static private int update(String sql,Object[] values){
		Connection conn = JDBCUtil.getConn();
		int successCount = 0;
		PreparedStatement preStmt = null;
		try {
			preStmt = conn.prepareStatement(sql);
			for(int i = 0; i < values.length; ++i){
				preStmt.setObject(i + 1, values[i]);
			}
			preStmt.executeUpdate();
			successCount = preStmt.getUpdateCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConn(null, preStmt, conn);
		}
		return successCount;
	}
	
	static public int Insert(User user){
		String sql = "INSERT INTO `weixindb`.`user` (`user_name`, `sex`, `open_id`, `weixin_no`, `tel`, `mail`, `address`, `avatar_url`, `credit_point`) "
				   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int i = -1;
		int id = selectByOpenId(user.getOpen_id());	
		if(id == -1){
			Object[] values = new Object[]{user.getUser_name(), user.getSex(), user.getOpen_id(), user.getWeixin_no(), user.getTel(),
										   user.getMail(), user.getAddress(), user.getAvatar_url(), user.getCredit_point()};
			i = update(sql, values);
//			System.out.println("i=" + i);
		}
		return i;
	}
	
	public int updateUser(User user){
		String sql = "update user set user_name=?, sex=?, weixin_no=?, tel=?, mail=?,address=? where user_id=?";
		Object[] values = new Object[]{user.getUser_name(), user.getSex(), user.getWeixin_no(), 
									   user.getTel(), user.getMail(), user.getAddress(), user.getUser_id()};
		int i = update(sql,values);
		return i;
	}
	
	public static int updateUserAvatar(int user_id, String avatar_url) {
		boolean ok = false;
		String sql = "UPDATE `weixindb`.`user` SET `avatar_url` = ? WHERE `user`.`user_id` = ? ";
		Object[] values = new Object[] {avatar_url, user_id};
		
		int count = -1;
		count = update(sql, values);
		
		return count; 
	}
	
	public static boolean updateUserCreditPoint(int user_id, int incre) {
		boolean ok = false;
		
		User user = selectByUserId(user_id);
		int credit_point = user.getCredit_point();
		
		// credit_point只能在0~100之间
		if (incre < 0 && credit_point + incre < 0) {
			credit_point = 0;
		} else if (incre > 0 && credit_point + incre > 100) {
			credit_point = 100;
		} else {
			credit_point += incre;
		}
		
		String sql = "UPDATE `weixindb`.`user` SET `credit_point` = ? WHERE (`user_id` = ?)";
		Object[] values = new Object[] {credit_point, user_id};
		
		int count = -1;
		count = update(sql, values);
		
		return count != -1;
	}
}
