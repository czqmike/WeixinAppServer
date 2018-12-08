package weixinapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinapp.dao.MessageDAO;
import weixinapp.model.Message;
import weixinapp.util.ResponseUtil;

/**
 * @author zzc
 * @date 2018年8月21日
 * @description 向某个商品提交留言时使用的Servlet
 * @param user_src, user_des（均为user_id）, content
 */
@WebServlet("/GetMessageServlet")
public class GetMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 102L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMessageServlet() {
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
		String user_srcs = request.getParameter("user_src");
		String user_dess = request.getParameter("user_des");
		String content = new String(request.getParameter("content").getBytes("ISO-8859-1"), "UTF-8");

		System.out.println("good_ids = " + good_ids);

		int good_id = 0;
		int user_src = 0;
		int user_des = 0;
		if (good_ids != null && user_srcs != null && user_dess != null) {
			good_id = Integer.parseInt(good_ids);
			user_src = Integer.parseInt(user_srcs);
			user_des = Integer.parseInt(user_dess);
			Message message = new Message(good_id, user_src, user_des, content);
			
			MessageDAO.InsertMessage(message);
			
			ResponseUtil.showSuccess(response);
		} else {
			ResponseUtil.showError(response);
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
