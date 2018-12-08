package weixinapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

import org.json.*;

import weixinapp.model.*;
import weixinapp.util.JSONArrayUtil;
import weixinapp.dao.*;

/**
 * @author zzc
 * @date 2018年8月22日
 * @description 返回某人的购物车信息
 * @param user_id
 * @return JSONArray 
 */
@WebServlet("/GiveShoppingcartServlet")
public class GiveShoppingcartServlet extends HttpServlet {
	private static final long serialVersionUID = 205L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GiveShoppingcartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		
		String user_id = request.getParameter("user_id");
		if(user_id != null){
			user_id = new String(user_id.getBytes("ISO-8859-1"),"UTF-8");
			ArrayList<Goods> list = GoodsDAO.selectGoodsinShoppingcart(Integer.parseInt(user_id));
			
			JSONObject jsonobj = new JSONObject();
			JSONArray jsonarr = new JSONArray();
			GoodTypeDAO goodTypeDao = new GoodTypeDAO();

			jsonarr = JSONArrayUtil.createJSONArray(list); // 替换以下代码↓↓↓↓↓
//			for (int i = 0; i < list.size(); ++i) {
//				Goods good = list.get(i);
//				ArrayList listType = goodTypeDao.selectTypeId(good.getType_id());
//				good.setType_name((String)listType.get(0));
//				good.setMain_class((String)listType.get(1));
//				jsonarr.put(new JSONObject(good));
//			}
			
			System.out.println(jsonarr);
			response.getWriter().print(jsonarr);
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
