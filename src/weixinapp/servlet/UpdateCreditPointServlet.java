package weixinapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinapp.dao.UserDAO;
import weixinapp.util.ResponseUtil;

/**
 * @author czqmike
 * @date 2018年9月15日
 * @description 
 * a)	说明：更新用户信誉值的Servlet
   b)	参数：user_id, incre（信誉值的增量，可为负数）
   c)	返回：成功时返回success，失败时返回error并将HTTP状态码设为303
   d)	UID: 6
 */
@WebServlet("/UpdateCreditPointServlet")
public class UpdateCreditPointServlet extends HttpServlet {
	private static final long serialVersionUID = 6L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCreditPointServlet() {
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
		String increstr = request.getParameter("incre");
		
		int user_id = -1;
		int incre = 0;
		boolean ok = false;

		try {
			user_id = Integer.parseInt(user_idstr);
			incre = Integer.parseInt(increstr);
			
			ok = UserDAO.updateUserCreditPoint(user_id, incre);
		} catch (Exception e) {
			e.printStackTrace();
			ResponseUtil.showError(response);
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
