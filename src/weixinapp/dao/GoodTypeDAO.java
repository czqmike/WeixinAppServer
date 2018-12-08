package weixinapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import weixinapp.util.JDBCUtil;

public class GoodTypeDAO {
	
	private static HashSet Select(String sql, Object[] values){
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		HashSet hash = new HashSet();
		try {
			preStmt = conn.prepareStatement(sql);
			for(int i = 0; i < values.length; ++i){
				preStmt.setObject(i + 1, values[i]);
			}
			rs = preStmt.executeQuery();
			while(rs.next()){
				hash.add(rs.getInt("type_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConn(rs, preStmt, conn);;
		}
		return hash;
	}
	
	public static HashSet selectByMainClass(String main_class){
		String sql = "select type_id from good_type where main_class=?";
		Object[] values = new Object[] {main_class};
		HashSet hash = Select(sql,values);
		return hash;
	}
	
	public static int selectTypeId(String main_class, String type_name){
		String sql = "select type_id from good_type where main_class=? and type_name=?";
		Object[] values = new Object[] {main_class,type_name};
		HashSet hash = Select(sql,values);
		int type_id = 0;
		if(hash.size() != 0){
			Iterator it = hash.iterator();
			while(it.hasNext()){
				type_id = (int) it.next();
			}
		}
		return type_id;
	}
	
	public Map selectTypeName(String main_class){
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		Map<Integer,String> map = new HashMap<Integer, String>();
		String sql = "select type_id,type_name from good_type where main_class=?";
		try {
			preStmt = conn.prepareStatement(sql);
			preStmt.setObject(1, main_class);
			rs = preStmt.executeQuery();
			while(rs.next()){
				map.put(rs.getInt("type_id"), rs.getString("type_name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConn(rs, preStmt, conn);;
		}
		return map;
		
	}

	public Map selectTypeName(){
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		Map<Integer,String> map = new HashMap<Integer, String>();
		String sql = "select type_id,type_name from good_type ";

		try {
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while(rs.next()){
				map.put(rs.getInt("type_id"), rs.getString("type_name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConn(rs, preStmt, conn);;
		}
		return map;
	}
	
	public static ArrayList selectTypeId(int type_id){
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();
		String sql = "select type_name,main_class from good_type where type_id=?";
		try {
			preStmt = conn.prepareStatement(sql);
			preStmt.setObject(1, type_id);
			rs = preStmt.executeQuery();
			if(rs.next()){
				list.add(rs.getString("type_name"));
				list.add(rs.getString("main_class"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConn(rs, preStmt, conn);;
		}
		
//		for (int i = 0; i < list.size(); ++i) {
//			System.out.println("list[" + i + "] = " + list.get(i));
//		}
		
		return list;
	}
	
}
