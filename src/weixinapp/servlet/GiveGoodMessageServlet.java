package weixinapp.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import weixinapp.dao.MessageDAO;
import weixinapp.model.Message;
import weixinapp.util.JSONArrayUtil;

/**
 * @author czqmike
 * @date 2018年8月23日
 * @description 给出商品下的留言信息，即数据库中message.good_id为此id的留言信息
 * @param good_id
 * @return 成功时返回JSON数组message，失败时返回空数组
 */
@WebServlet("/GiveGoodMessageServlet")
public class GiveGoodMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 210L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GiveGoodMessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		
		String good_ids = request.getParameter("good_id");
		int good_id = 0;
		JSONArray jsonarr = new JSONArray();

		if (good_ids != null) {
			good_id = Integer.parseInt(good_ids);
			
			ArrayList<Message> list = MessageDAO.selectByGoodId(good_id);
			
			jsonarr = JSONArrayUtil.createMessageJSONArray(list);
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
