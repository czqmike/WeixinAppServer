package weixinapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import weixinapp.model.Goods;
import weixinapp.util.JDBCUtil;

public class GoodsDAO {
	private static int MAXSIZE = 30;
	
	private static ArrayList Select(String sql, Object[] values){		
		Connection conn = JDBCUtil.getConn();	//����ҵ�goods_id�򷵻�id�����򷵻�-1
		ResultSet rs = null;
		ArrayList<Goods> list = new ArrayList<Goods>();
		PreparedStatement preStmt = null;
		try {
			preStmt = conn.prepareStatement(sql);
			for(int i = 0; i < values.length; ++i){
				preStmt.setObject(i + 1, values[i]);
			}
			rs = preStmt.executeQuery();
			while(rs.next()){
				Goods goods = new Goods();
				goods.setGood_id(rs.getInt("good_id"));
				goods.setUser_id(rs.getInt("user_id"));
				goods.setGood_name(rs.getString("good_name"));
				goods.setGood_detail(rs.getString("good_detail"));
				goods.setPrice(rs.getDouble("price"));
				goods.setIs_new(rs.getBoolean("is_new"));
				goods.setType_id(rs.getInt("type_id"));
				goods.setTrade_type(rs.getString("trade_type"));
				goods.setImage_url(rs.getString("image_url"));
				goods.setSold(rs.getBoolean("sold"));
				goods.setCounseling_time_map(rs.getString("counseling_time_map"));
				list.add(goods);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JDBCUtil.closeConn(rs, preStmt, conn);
		}
		return list;
	}
	
	public int SelectIsEmpty(String sql, Object[] values){
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		int successCount = 0;
		try {
			preStmt = conn.prepareStatement(sql);
			for (int i = 0; i < values.length; ++i){
				preStmt.setObject(i + 1, values[i]);
			}
			rs = preStmt.executeQuery();
			if(rs.next()){
				successCount = 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConn(rs, preStmt, conn);
		}
		return successCount;
		
	}
	
	public static ArrayList selectByGoodName(String name){
		String sql = "select * from goods where good_name=?";
		Object[] values = new Object[] {name};
		ArrayList list = Select(sql, values);
		return list;
	}
	
	public static ArrayList selectByTypeId(int type_id){
		String sql = "select * from goods where type_id=?";
		Object[] values = new Object[]{type_id};
		ArrayList list = Select(sql,values);
		return list;
	}
	
	public static ArrayList selectByUserId(int user_id){
		String sql = "select * from goods where user_id=?";
		Object[] values = new Object[] {user_id};
		ArrayList list = Select(sql,values);
		return list;
	}
	
	public static Goods selectByGoodId(int good_id){
		String sql = "select * from goods where good_id=?";
		Object[] values = new Object[]{good_id};
		ArrayList list = Select(sql,values);
		Goods goods = null;
		if(list.size() > 0){
			goods = (Goods) list.get(0);
		}
		return goods;
	}
	
	public static ArrayList<Goods> selectByGoodIds(ArrayList<Integer> good_ids) {
		String sql = "select * from weixindb.goods where good_id in (";
		for (int i = 0; i < good_ids.size(); ++i) {
			if (i != 0) {
				sql += ',';
			}
			sql += good_ids.get(i).toString();
		}
		sql += ')';
		
		ArrayList<Goods> list = Select(sql, new Object[] {});
		
		return list;
	}
	
	public static ArrayList<Goods> selectSecondHandGoods() {
		String sql = "select * from weixindb.goods " + //注意尾部空格
					 "where goods.sold = false AND goods.type_id in ( " + 
						 "select good_type.type_id " + 
						 "from weixindb.good_type " + 
						 "where main_class = \"二手交易\") " + 
							 "order by goods.good_id " + 
							 "limit " + Integer.toString(MAXSIZE);
		
		ArrayList<Goods> list = Select(sql, new Object[] {});
		
		return list;
	}

	public static ArrayList<Goods> selectOfflineCourseGoods(){
		String sql = "select * from goods where goods.sold = false AND goods.type_id in " +
					 "(select good_type.type_id from good_type where main_class=?) " + 
							 "order by goods.good_id " + 
							 "limit " + Integer.toString(MAXSIZE);
		Object[] values = new Object[]{"线下辅导"};
		ArrayList<Goods> list = Select(sql,values);
		return list;
	}

	public static ArrayList<Goods> selectWhatIBought(int user_id){
		String sql = "select * " + 
					 "from weixindb.goods " + 
					 "where goods.good_id in ( " + 
						"select order.good_id " + 
						"from weixindb.order " + 
						"where user_id = ?);";

		Object[] values = new Object[]{user_id};
		ArrayList<Goods> list = Select(sql,values);

		return list;
	}

	public ArrayList selectUserIdSold(int user_id,boolean sold){
		String sql = "select * from goods where user_id = ? and sold = ?";
		Object[] values = new Object[] {user_id,sold};
		ArrayList list = Select(sql,values);
		return list;
	}
	
	public static ArrayList<Goods> selectGoodsinShoppingcart(int user_id) {
		String sql = "select * " + 
					 "from weixindb.goods " + 
					 "where goods.good_id in ( " + 
						 "select shoppingcart.good_id " + 
						 "from shoppingcart " + 
						 "where shoppingcart.user_id = ? );";
		Object[] values = new Object[] {user_id};
		ArrayList list = Select(sql,values);
		return list;
	}
	
	public static ArrayList<Goods> selectGoodsWithKeyword(String keyword) {
		String sql = "select * from weixindb.goods " + 
				 	 "where goods.sold = false AND (goods.good_name like ? OR goods.good_detail like ?) " + 
				 	 "order by good_id DESC " +
				 	 "limit 0, 100"; 
		keyword = '%' + keyword + '%';		// 匹配任意含有此关键字的项
		Object[] values = new Object[] {keyword, keyword};
		ArrayList<Goods> list = Select(sql, values);
		return list;
	}

	// 参数为详细地址，精确到楼号，如：栎苑5号
	public static ArrayList<Goods> selectPushedGoods(String key_address) {
		String sql = "select * " + 
					 "from weixindb.goods " + 
					"where goods.sold = false AND goods.user_id in ( " + 
					"	select user.user_id " + 
					"	from weixindb.user " + 
					"	where user.address like ? AND user.credit_point >= 80 " + 
					"	order by credit_point DESC " + 
					") " + 
					"limit 0, " + Integer.toString(MAXSIZE);
		String keyword = '%' + key_address + '%';
		Object[] values = new Object[] {keyword};
	
		// 备用的sql
		String sql_op = "select * " + 
						"from weixindb.goods " + 
						"where goods.sold = false AND goods.user_id in ( " + 
						"	select user.user_id " + 
						"	from weixindb.user " + 
						"	where user.credit_point >= 80 " + 
						"	order by credit_point DESC " + 
						") " + 
						"limit 0, 30";

		ArrayList<Goods> list = Select(sql, values);

		// 没有找到符合条件的货物时
		if (list.size() <= 3) {
			list = Select(sql_op, new Object[] {});
		}
		return list;
	}

	private int Insert(String sql,Object[] values){
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
	
	/**
	 * 被注释掉的部分：
	 * @param goods
	 * @return 插入一条good并返回其good_id, 若插入不成功，返回-1
	 */
	public int InsertGoods(Goods goods){
		String sql = "insert into goods(user_id,good_name,good_detail,price,is_new,trade_type,type_id,image_url,sold,counseling_time_map) "
			       + "values(?,?,?,?,?,?,?,?,?,?)";
		Object[] values = new Object[] {
				goods.getUser_id(), goods.getGood_name(), goods.getGood_detail(), goods.getPrice(), goods.isIs_new(), 
				goods.getTrade_type(), goods.getType_id(), goods.getImage_url(), goods.isSold(), goods.getCounseling_time_map()};
		int success = Insert(sql,values);
		return success;
//		String sql = 
//					 "INSERT INTO `weixindb`.`goods` (`user_id`, `good_name`, " + 
//								  " `good_detail`, `price`, `is_new`, `trade_type`, `type_id`, " + 
//								  "  `image_url`, `sold`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?); " + 
//					 " SELECT MAX(good_id) from `weixindb`.`goods`; ";
//		Object[] values = new Object[] {goods.getUser_id(), goods.getGood_name(), goods.getGood_detail(), goods.getPrice(), goods.isIs_new(), goods.getTrade_type(), goods.getType_id(), goods.getImage_url(), goods.isSold()};
//		
//		Connection conn = JDBCUtil.getConn();
//		int successCount = 0;
//		PreparedStatement preStmt = null;
//		ResultSet rs = null;
//		int good_id = -1;
//		try {
//			preStmt = conn.prepareStatement(sql);
//			// 设置参数
//			for(int i = 0; i < values.length; ++i){
//				preStmt.setObject(i + 1, values[i]);
//			}
//			// 执行这两条语句，如果插入成功，则获取插入成功的good_id，否则返回-1
//			if (preStmt.execute()) {
//				preStmt.getMoreResults();
//				rs = preStmt.getResultSet();
//				good_id = rs.getInt("good_id");
//			}
//
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			JDBCUtil.closeConn(null, preStmt, conn);
//		}
//		return good_id;
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

	static public boolean updateImageURL(String image_url, int good_id) {
		String sql = "UPDATE `weixindb`.`goods` SET `image_url` = ? WHERE (`good_id` = ?)";
		Object[] para = new Object[] {image_url, good_id};
		return update(sql, para);
	}
	
	public int updateGood(Goods goods){
		String sql = "update goods set good_name=?,good_detail=?,price=?,is_new=?,trade_type=?,"
				   + "type_id=?,image_url=?,sold=?,counseling_time_map=? where good_id=?";
		Object[] values = new Object[]{
				goods.getGood_name(), goods.getGood_detail(),goods.getPrice(),goods.isIs_new(),goods.getTrade_type(),
				goods.getType_id(),goods.getImage_url(),goods.isSold(),goods.getCounseling_time_map(), goods.getGood_id()};
		int successCount = Insert(sql,values);
		return successCount;
		
	}
	
	public static boolean updateSoldState(int good_id) {
		boolean ok = false;
		String sql = "UPDATE `weixindb`.`goods` SET `sold` = '1' WHERE (`good_id` = ?);";
		Object[] values = new Object[] {good_id};
		ok = update(sql, values);
		return ok;
	}

	public boolean deleteGood(int good_id){
		String sql = "delete from goods where good_id=?";
		Object[] values = new Object[]{good_id};
//		int successCount = Insert(sql,values);
		boolean ok = update(sql, values);
//		return successCount;
		return ok;
	}
	
	
}
