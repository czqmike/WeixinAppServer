package weixinapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import weixinapp.dao.*;
import weixinapp.model.*;
import weixinapp.util.ResponseUtil;

/**
 * @author czqmike
 * @date 2018年8月20日
 * @description 立即购买按钮的Servlet, 用于修改 goods.sold 为true，并插入一条记录到 order表中
 * 				成功时返回 success ，否则返回error，并且状态码为303
 * @param user_id, good_ids(JSON数组)
 */
@WebServlet("/BuyGoodServlet")
public class BuyGoodServlet extends HttpServlet {
	private static final long serialVersionUID = 2L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyGoodServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean ok = false;
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		
		String user_ids = request.getParameter("user_id");
		String good_ids = request.getParameter("good_ids");

		int user_id = 0;
		try {
			JSONArray jsonarr = new JSONArray(good_ids);
			user_id = Integer.parseInt(user_ids);
			for (int i = 0; i < jsonarr.length(); ++i) {
				Goods good = GoodsDAO.selectByGoodId(jsonarr.getInt(i));
						
				if (good != null && good.isSold() == false) {
					GoodsDAO.updateSoldState(jsonarr.getInt(i));  // 修改sold 为 true
					
					Order order = new Order(user_id, jsonarr.getInt(i));
					OrderDAO.insert(order);  			// 插入order记录到order表中
					ok = true;
				} else { // 购买发生错误，比如商品已被购买等
					ResponseUtil.showError(response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ResponseUtil.showError(response);
		}
		
		
		if (ok) {
			ResponseUtil.showSuccess(response);
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
