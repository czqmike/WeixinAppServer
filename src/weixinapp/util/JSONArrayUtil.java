package weixinapp.util;

import org.json.*;
import java.util.*;

import weixinapp.dao.*;
import weixinapp.model.*;

/**
 * @author czqmike
 * @date 2018年8月20日
 * @description 将给定的ArrayList转化成用于传递给客户端的json数组，并且添加进商品总类型，分类型，以及发布者昵称、发布者微信号、头像等信息。
 */
public class JSONArrayUtil {

	@SuppressWarnings("rawtypes")
	public static JSONArray createNormalJSONArray(ArrayList list) {
		JSONArray jsonarr = new JSONArray();
		for (Object obj : list) {
			jsonarr.put(new JSONObject(obj));
		}
		return jsonarr;
	}

	public static JSONArray createJSONArray(ArrayList<Goods> list) {
		JSONArray jsonarr = new JSONArray();
		
		for(int i = 0; i < list.size(); ++i){
			Goods good = (Goods) list.get(i);

			User user = UserDAO.selectByUserId(good.getUser_id()); // 用给定的good.user_id查询出此用户

			ArrayList listType = GoodTypeDAO.selectTypeId(good.getType_id()); // 查询商品类型

			good.setType_name((String)listType.get(0));
			good.setMain_class((String)listType.get(1));

			GoodsWithUser gwu = null;
			if (user != null) {
				gwu = new GoodsWithUser(good, user); // 构造含有用户信息的Goods 
			}
			
			jsonarr.put(new JSONObject(gwu));
		}

		return jsonarr;
	}
	
	// 将ArrayList<Message>  转化为JSONArray, 并添加发信人头像以及用户名等信息, 以方便传输
	public static JSONArray createMessageJSONArray(ArrayList<Message> list) {
		JSONArray jsonarr = new JSONArray();
		
		for (int i = 0; i < list.size(); ++i) {
			Message message = list.get(i);
			
			User user = UserDAO.selectByUserId(message.getUser_src());
			message.setAvatar_url(user.getAvatar_url());
			message.setUser_name(user.getUser_name());

			jsonarr.put(new JSONObject(message));
		}

		return jsonarr;
	}

	/**
	 * @param list
	 * @return 除了包含GoodsWithUser外，还有order.done以及 order.tradetime
	 */
	public static JSONArray createOrderJSONArray(ArrayList<Goods> list) {
		JSONArray jsonarr = new JSONArray();
		
		for(int i = 0; i < list.size(); ++i){
			Goods good = (Goods) list.get(i);

			User user = UserDAO.selectByUserId(good.getUser_id()); // 用给定的good.user_id查询出此用户
			
			Order order = OrderDAO.selectByGoodId(good.getGood_id());
			
			ArrayList listType = GoodTypeDAO.selectTypeId(good.getType_id()); // 查询商品类型

			good.setType_name((String)listType.get(0));
			good.setMain_class((String)listType.get(1));

			GoodsWithOrder gwo = null;
			if (user != null) {
				gwo = new GoodsWithOrder(good, user, order); // 构造含有用户信息的Goods 
			}
			
			jsonarr.put(new JSONObject(gwo));
		}

		return jsonarr;
	}
}
