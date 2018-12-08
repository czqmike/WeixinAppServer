package weixinapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import weixinapp.dao.OrderDAO;
import weixinapp.util.ResponseUtil;

/**
 * Servlet implementation class ConfirmGoodServlet
 */
@WebServlet("/ConfirmGoodServlet")
public class ConfirmGoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmGoodServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		
		String suser_id = request.getParameter("user_id");
		String sgood_ids = request.getParameter("good_ids");
		int user_id = 0;
		
		try {
			user_id = Integer.parseInt(suser_id);
			JSONArray good_ids = new JSONArray(sgood_ids);
			
			for (int i = 0; i < good_ids.length(); ++i) {
				OrderDAO.updateDone(user_id, good_ids.getInt(i)); // 挨个修改 order.done 为 true
			}
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
