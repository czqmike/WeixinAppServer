package weixinapp.dao;

import java.sql.*;
import java.sql.ResultSet;
import java.util.ArrayList;

import weixinapp.util.JDBCUtil;
import weixinapp.model.*;


public class ShoppingcartDAO {
	public static ArrayList<Integer> selectGoodIdByUserId(int user_id) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT good_id FROM weixindb.shoppingcart where user_id = ?;";
		
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
			JDBCUtil.closeConn(rs, stmt, conn);
		}
		
		return list;
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

	public static boolean insert(Shoppingcart sc) {
		boolean ok = true;
		
		Connection conn = JDBCUtil.getConn();
		PreparedStatement stmt = null;
		
		String sql = "INSERT INTO `weixindb`.`shoppingcart` (`user_id`, `good_id`) VALUES (?, ?);";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, sc.getUser_id());
			stmt.setInt(2, sc.getGood_id());

			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ok = false;
		} finally {
			JDBCUtil.closeConn(stmt, conn);
		}

		return ok;
	}

	public static boolean deleteOneCart(Shoppingcart sc) {
		boolean ok = false;
		String sql = "DELETE FROM `weixindb`.`shoppingcart` WHERE (`user_id` = ?) and (`good_id` = ?) ";
		
		Object[] params = {sc.getUser_id(), sc.getGood_id()};

		ok = update(sql, params);

		return ok;
	}

}
