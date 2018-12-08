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
 * @date 2018年8月21日
 * @description 获取某用户收到的留言
 * @param user_id
 */
@WebServlet("/GiveMessageServlet")
public class GiveMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 201L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GiveMessageServlet() {
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
		int user_id = 0;
		if (user_ids != null) {
			user_id = Integer.parseInt(user_ids);
			ArrayList<Message> list = MessageDAO.selectByUserDes(user_id);
			
			JSONArray jsonarr = JSONArrayUtil.createMessageJSONArray(list);
			
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
