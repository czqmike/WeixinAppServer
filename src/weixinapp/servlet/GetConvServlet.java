package weixinapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinapp.dao.ConversationDAO;
import weixinapp.model.Conversation;
import weixinapp.util.ResponseUtil;

/**
 * @author czqmike
 * @date 2019年3月9日
 * @description 插入一条记录到Conversation表中，表示发出了一条消息
 * @param user_src, user_des, content
 * @return success or error info
 */
@WebServlet("/GetConvServlet")
public class GetConvServlet extends HttpServlet {
	private static final long serialVersionUID = 106L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetConvServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		String user_srcstr = request.getParameter("user_src");
		String user_desstr = request.getParameter("user_des");
		String content = new String(request.getParameter("content").getBytes("ISO-8859-1"), "UTF-8");

		int user_src = -1;
		int user_des = -1;
		if (user_srcstr != null && user_desstr != null) {
			user_src = Integer.parseInt(user_srcstr);
			user_des = Integer.parseInt(user_desstr);
			Conversation conv = new Conversation(user_src, user_des, content);
			
			if (ConversationDAO.insertConversation(conv) != true) {
				ResponseUtil.showError(response, "Insert Error!");
			} else {
				ResponseUtil.showSuccess(response);
			}
			return;
		} else {
			ResponseUtil.showError(response, "Parameter Error!");
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
