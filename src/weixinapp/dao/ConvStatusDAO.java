package weixinapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import weixinapp.util.JDBCUtil;

public class ConvStatusDAO {

	public static ArrayList<Integer> selectChatingWith(int user_lower) {
		ArrayList<Integer> list = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = JDBCUtil.getConn();
		String sql = "select * from conv_status where (user_lower = ? or user_upper = ?) and chating = true ";
		try{
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user_lower);
			stmt.setInt(2, user_lower);

			rs = stmt.executeQuery();
			while(rs.next()){
				int ul = rs.getInt("user_lower");
				int up = rs.getInt("user_upper");
				
				list.add(ul != user_lower? ul : up);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConn(rs, stmt, conn);
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
	
	public static boolean insertConvStatus(int user_lower, int user_upper) {
		String sql = "INSERT INTO `weixindb`.`conv_status` (`user_lower`, `user_upper`, `chating`) VALUES (?, ?, ?);";

		return update(sql, new Object[] {user_lower, user_upper, true});
	}
	
	public static boolean disableConvnt (int user_lower, int user_upper) {
		String sql = "UPDATE `weixindb`.`conv_status` SET `chating` = '0' WHERE (`user_lower` = ?) and (`user_upper` = ?)";
		
		return update(sql, new Object[] {user_lower, user_upper});
	}
}
