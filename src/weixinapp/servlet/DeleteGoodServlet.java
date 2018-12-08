package weixinapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import weixinapp.dao.GoodsDAO;
import weixinapp.util.ResponseUtil;

/**
 * Servlet implementation class DeleteGoodServlet
 */
@WebServlet("/DeleteGoodServlet")
public class DeleteGoodServlet extends HttpServlet {
	private static final long serialVersionUID = 3L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteGoodServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		String good_id = request.getParameter("good_id");
		if(good_id != null){
			good_id = new String(good_id.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println("good_id = " + good_id);
			JSONObject jsongood = null;
			int goodId;
			goodId = Integer.parseInt(good_id);
			GoodsDAO dao = new GoodsDAO();

			boolean ok = dao.deleteGood(goodId);
			
			if (ok) {
				ResponseUtil.showSuccess(response);
			} else {
				ResponseUtil.showError(response);
			}
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
