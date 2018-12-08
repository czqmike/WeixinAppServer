package weixinapp.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;

import weixinapp.dao.GoodTypeDAO;
import weixinapp.dao.GoodsDAO;
import weixinapp.model.Goods;
import weixinapp.util.JSONArrayUtil;

/**
 * @author czqmike
 * @date 2018年8月22日
 * @description 返回二手交易栏目的商品信息
 */
@WebServlet("/GiveSecondHandGoodsServlet")
public class GiveSecondHandGoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 204L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GiveSecondHandGoodsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		ArrayList<Goods> list = GoodsDAO.selectSecondHandGoods();
		
		JSONObject jsonobj = new JSONObject();
		JSONArray jsonarr = new JSONArray();
		GoodTypeDAO goodTypeDao = new GoodTypeDAO();
		Map map = goodTypeDao.selectTypeName("二手交易");
		Iterator it = null;

		jsonarr = JSONArrayUtil.createJSONArray(list); // 替换以下代码↓↓↓↓↓
//		for (int i = 0; i < list.size(); ++i) {
//			Goods good = list.get(i);
//			it = map.keySet().iterator(); 
//			while (it.hasNext()){ 
//				int key; 
//				key=(int)it.next(); 
//				if(key == good.getType_id()){
//					good.setType_name((String)map.get(key));
////					System.out.println("good_id = " + good.getType_id() +  "key = " + key + " " + "get = " + (String)map.get(key));
//				}
//			} 
//			jsonarr.put(new JSONObject(good));
//		}
		
//		System.out.println(jsonarr);
		response.getWriter().print(jsonarr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
