package weixinapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;

import weixinapp.dao.GoodsDAO;
import weixinapp.dao.ShoppingcartDAO;
import weixinapp.model.Shoppingcart;
import weixinapp.util.ResponseUtil;

/**
 * @author czqmike
 * @date 2018年8月23日
 * @description 删除某人的购物车的某些商品的Servlet
 * @param user_id, good_ids（JSON数组）
 * @return 成功时返回success，失败时返回error并将HTTP状态码设为303
 */
@WebServlet("/DeleteShoppingcartServlet")
public class DeleteShoppingcartServlet extends HttpServlet {
	private static final long serialVersionUID = 4L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteShoppingcartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		
		String user_ids = request.getParameter("user_id");
		String good_ids = request.getParameter("good_ids"); // 获取客户端传来的字符串

//		System.out.println(user_ids);
//		System.out.println(good_ids);
		
		int user_id = 0;
		
		JSONArray jsonarr = null;
		try {
			jsonarr = new JSONArray(good_ids);
			user_id = Integer.parseInt(user_ids); // 将字符串转化为实例或对象

			for (int i = 0; i < jsonarr.length(); ++i) {
				Shoppingcart sc;
				sc = new Shoppingcart(user_id, jsonarr.getInt(i));

				ShoppingcartDAO.deleteOneCart(sc); // 在shoppingcart表中删除这条记录

//				GoodsDAO.updateSoldState(jsonarr.getInt(i)); // 将goods.sold设为true
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			ResponseUtil.showError(response);
			return ;
		}
		ResponseUtil.showSuccess(response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
