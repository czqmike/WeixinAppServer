package weixinapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;

import weixinapp.dao.*;
import weixinapp.model.Goods;
import weixinapp.util.ResponseUtil;

/**
 * Servlet implementation class SetSecondHandGoodsServlet
 */
/**
 * @author zzc
 * @date 2018年8月13日
 * @description 获取小程序传过来的转成JSON字符串的good对象,并插入到good表
 */
@WebServlet("/GetSubmittedGoodServlet")
public class GetSubmittedGoodServlet extends HttpServlet {
	private static final long serialVersionUID = 103L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSubmittedGoodServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String returnmsg = "unknown error";

		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		String good = request.getParameter("good");
		if (good != null) {
			good = new String(good.getBytes("ISO-8859-1"), "UTF-8");
//			System.out.println("good = " + good);
			
			JSONObject jsonobj = null;
			int user_id;
			String good_name = "";
			String good_detail = "";
			double price = 0;
			boolean is_new;
			String trade_type = "";
			String good_class = "";
			String good_type = "";
			int type_id;
			String image_url;
			boolean sold;
			String counseling_time_map = "";
			try {
				jsonobj = new JSONObject(good);
				good_name = jsonobj.getString("goodsName");
				user_id = jsonobj.getInt("userId");
				good_detail = jsonobj.getString("describe");
				price = jsonobj.getDouble("price");
				is_new = jsonobj.getBoolean("is_new");
				trade_type = jsonobj.getString("tradeType");
				good_class = jsonobj.getString("goodsClass");
				good_type = jsonobj.getString("goodsType");
				JSONArray jsonarr = jsonobj.getJSONArray("img");
//				JSONObject jsonobj1 = jsonobj.getJSONObject("img");
				sold = jsonobj.getBoolean("sold");
				counseling_time_map = jsonobj.getString("counseling_time_map");

				type_id = GoodTypeDAO.selectTypeId(good_class,good_type);
				
				Goods goods = new Goods();
				goods.setUser_id(user_id);
				goods.setGood_name(good_name);
				goods.setGood_detail(good_detail);
				goods.setPrice(price);
				goods.setIs_new(is_new);
				goods.setTrade_type(trade_type);
				goods.setType_id(type_id);
				goods.setSold(sold);
				goods.setCounseling_time_map(counseling_time_map);

//				System.out.println("good = " + good);
//				System.out.println("jsonobj = " + jsonobj);

				if (jsonarr.length() != 0) {
					goods.setImage_url(jsonarr.getString(0));
					String str = jsonarr.getString(0);
					for (int i = 1; i < jsonarr.length(); ++i) {
						str += '`' + jsonarr.getString(i);
					}
					goods.setImage_url(str);
				} else {
					goods.setImage_url("needImageURL");
				}
				GoodsDAO dao = new GoodsDAO();

				dao.InsertGoods(goods);
				
				ResponseUtil.showSuccess(response);
			} catch (JSONException e) {
				e.printStackTrace();
				ResponseUtil.showError(response);
			}
			
//			System.out.println(good_name);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
