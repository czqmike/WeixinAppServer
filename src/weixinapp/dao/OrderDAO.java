package weixinapp.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

import weixinapp.model.Order;
import weixinapp.util.FormatterUtil;
import weixinapp.util.JDBCUtil;

public class OrderDAO {
	private static Order select(String sql, Object[] params) {
		Order order = new Order(); 
		Connection conn = JDBCUtil.getConn();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			for (int i = 1; i <= params.length; ++i) {
				stmt.setObject(i, params[i - 1]);
			}

			rs = stmt.executeQuery();
			
			while (rs.next()) {
				order.setOrder_id(rs.getInt("order_id"));
				order.setGood_id(rs.getInt("good_id"));
				order.setUser_id(rs.getInt("user_id"));
				order.setDone(rs.getBoolean("done"));
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getObject("tradetime")); // 将数据库中取出的时间后的【.0】去j掉
				order.setTradetime(LocalDateTime.parse(time, FormatterUtil.formatter));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConn(stmt, conn);
		}
		
		return order;
	}
	
	public static ArrayList<Integer> selectGoodIdByUserId(int user_id) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM weixindb.`order`where user_id = ?";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user_id);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				list.add(rs.getInt("good_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConn(stmt, conn);
		}
		
		return list;
	}

	public static Order selectByUserId(int user_id) {
		Order order = new Order();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM weixindb.`order`where user_id = ?";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user_id);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				order.setOrder_id(rs.getInt("order_id"));
				order.setGood_id(rs.getInt("good_id"));
				order.setUser_id(rs.getInt("user_id"));
				order.setDone(rs.getBoolean("done"));
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getObject("tradetime")); // 将数据库中取出的时间后的【.0】去j掉
				order.setTradetime(LocalDateTime.parse(time, FormatterUtil.formatter));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConn(stmt, conn);
		}
		
		return order;
	}
	
	public static Order selectByGoodId(int good_id) {
		String sql = "SELECT * FROM weixindb.`order` where good_id = ?";
		Object[] params = new Object[] {good_id};
		Order order = select(sql, params);
		
		return order;
	}

	public static boolean insert(Order order) {
		boolean ok = false;
		
		Connection conn = JDBCUtil.getConn();
		PreparedStatement stmt = null;
		
		String sql = "INSERT INTO `weixindb`.`order` (`user_id`, `good_id`, `done`, `tradetime`) VALUES (?, ?, ?, ?)";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, order.getUser_id());
			stmt.setInt(2, order.getGood_id());
			stmt.setBoolean(3, order.isDone());
			stmt.setString(4, order.getTradetime().format(FormatterUtil.formatter));
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConn(stmt, conn);
		}
		
		
		return ok;
	}

	private static boolean update(String sql, Object[] params) {
		boolean ok = false;
		
		Connection conn = JDBCUtil.getConn();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			
			for (int i = 0; i < params.length; ++i) {
				stmt.setObject(i + 1, params[i]);
			}
			
			if (stmt.executeUpdate() != 0) {
				ok = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConn(stmt, conn);
		}
		
		return ok;
	}
	
	public static boolean updateDone(int user_id, int good_id) {
		boolean ok = false;
		String sql = "UPDATE `weixindb`.`order` SET `done` = '1' WHERE (`user_id` = ?) AND (`good_id` = ?) ";
		Object[] params = {user_id, good_id};
		
		ok = update(sql, params);
		return ok;
	}
	
}
