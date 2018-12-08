package weixinapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weixinapp.dao.GoodTypeDAO;
import weixinapp.dao.GoodsDAO;
import weixinapp.model.Goods;
import weixinapp.util.ResponseUtil;

/**
 * Servlet implementation class UpdateGoodServlet
 */
@WebServlet("/UpdateGoodServlet")
public class UpdateGoodServlet extends HttpServlet {
	private static final long serialVersionUID = 5L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateGoodServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		String good = request.getParameter("good");
		if (good != null) {
			good = new String(good.getBytes("ISO-8859-1"), "UTF-8");
//			System.out.println("good = " + good);
			
			JSONObject jsonobj = null;
			int good_id;
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
				good_id = jsonobj.getInt("goodsId");
				good_name = jsonobj.getString("goodsName");
				user_id = jsonobj.getInt("userId");
				good_detail = jsonobj.getString("describe");
				price = jsonobj.getDouble("price");
				is_new = jsonobj.getBoolean("is_new");
				trade_type = jsonobj.getString("tradeType");
				good_class = jsonobj.getString("goodsClass");
				good_type = jsonobj.getString("goodsType");
				JSONArray arr = jsonobj.getJSONArray("img");
				image_url = arr.getString(0);
				for(int i = 1; i < arr.length(); ++i){
					image_url += '`' + arr.getString(i);
				}
				sold = jsonobj.getBoolean("sold");
				counseling_time_map = jsonobj.getString("counseling_time_map");

				type_id = GoodTypeDAO.selectTypeId(good_class,good_type);
				
				Goods goods = new Goods();
				goods.setGood_id(good_id);
				goods.setUser_id(user_id);
				goods.setGood_name(good_name);
				goods.setGood_detail(good_detail);
				goods.setPrice(price);
				goods.setIs_new(is_new);
				goods.setTrade_type(trade_type);
				goods.setType_id(type_id);
				goods.setSold(sold);
				goods.setImage_url(image_url);
				goods.setCounseling_time_map(counseling_time_map);
				
				GoodsDAO dao = new GoodsDAO();
				dao.updateGood(goods);
				
				ResponseUtil.showSuccess(response);
			} catch (JSONException e) {
				e.printStackTrace();	
				ResponseUtil.showError(response);
			}
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
