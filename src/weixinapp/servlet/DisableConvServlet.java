package weixinapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinapp.dao.ConvStatusDAO;
import weixinapp.util.ResponseUtil;

/**
 * @author czqmike
 * @date 2019年3月9日
 * @description 删除我的消息中的消息时触发的Servlet，执行后调用CheckChating不再显示这个对话
 * @param user_lower, user_upper
 * @return 成功时返回success，失败时返回错误信息并将HTTP状态码设为303
 */
@WebServlet("/DisableConvServlet")
public class DisableConvServlet extends HttpServlet {
	private static final long serialVersionUID = 8L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisableConvServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");

		String user_lowerstr = request.getParameter("user_lower");
		String user_upperstr = request.getParameter("user_upper");
		int user_lower = -1, user_upper = -1;
		try {
			user_lower = Integer.parseInt(user_lowerstr);
			user_upper = Integer.parseInt(user_upperstr);
		} catch (Exception e) {
			ResponseUtil.showError(response, "Parse params error!");
		}
		
		if (ConvStatusDAO.disableConvnt(user_lower, user_upper)) {
			ResponseUtil.showSuccess(response);
		} else {
			ResponseUtil.showError(response, "Update database error!");
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
