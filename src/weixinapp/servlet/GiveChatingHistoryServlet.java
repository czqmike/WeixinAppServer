package weixinapp.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import weixinapp.dao.ConversationDAO;
import weixinapp.model.Conversation;
import weixinapp.util.JSONArrayUtil;

/**
 * @author czqmike
 * @date 2019年3月9日
 * @description 客户端获取某个人与某个人详细的聊天记录的Servlet
 * @param user_src, user_des
 * @return 成功时返回JSON数组convs，包含若干个conv，并以时间先后顺序排序，
 *         失败时返回error或其他错误信息并将HTTP状态码设为303
 */
@WebServlet("/GiveChatingHistoryServlet")
public class GiveChatingHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 214L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GiveChatingHistoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		
		String user_srcstr = request.getParameter("user_src");
		String user_desstr = request.getParameter("user_des");
		int user_src = -1, user_des = -1;
		user_src = Integer.parseInt(user_srcstr);
		user_des = Integer.parseInt(user_desstr);
		
		JSONArray jsonarr = new JSONArray();
		
		ArrayList<Conversation> list = new ArrayList<Conversation>();
		list = ConversationDAO.selectByID(user_src, user_des);

		jsonarr = JSONArrayUtil.createNormalJSONArray(list);
		
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
