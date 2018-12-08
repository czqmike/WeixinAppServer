package weixinapp.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import weixinapp.dao.GoodsDAO;
import weixinapp.dao.UserDAO;
import weixinapp.model.*;
import weixinapp.util.JSONArrayUtil;

/**
 * @author czqmike
 * @date 2018年9月26日
 * @description a)	说明：获取推送给用户的商品信息 
				b)	参数：user_id
				c)	返回：成功时返回goods（JSON数组），失败时返回空数组
				d)	UID：212
 */
@WebServlet("/GivePushedGoodServlet")
public class GivePushedGoodServlet extends HttpServlet {
	private static final long serialVersionUID = 212L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GivePushedGoodServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");

		String user_idstr = request.getParameter("user_id");
		int user_id = -1;
		User user = null;
		String address = null;
		String key_add = null;
		JSONArray jsonarr = new JSONArray();
		try {
			user_id = Integer.parseInt(user_idstr);
			user = UserDAO.selectByUserId(user_id);
			address = user.getAddress();
			key_add = null;

			// 获取详细楼号
			if (!"".equals(address)) {
				key_add = address.split(",")[1];
			}
			
			ArrayList<Goods> list = new ArrayList<Goods>();
			list = GoodsDAO.selectPushedGoods(key_add);
			
			jsonarr = JSONArrayUtil.createJSONArray(list);
		} catch (Exception e) {
			e.printStackTrace();
		}

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
