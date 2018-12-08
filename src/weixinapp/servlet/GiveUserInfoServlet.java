package weixinapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weixinapp.dao.UserDAO;
import weixinapp.model.User;

/**
 * @author zzc
 * @date 2018年8月22日
 * @description 返回某人的详细信息，如微信号，昵称等。 
 */
@WebServlet("/GiveUserInfoServlet")
public class GiveUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 206L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GiveUserInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		String user_id = request.getParameter("user_id");
		if(user_id != null){
			user_id = new String(user_id.getBytes("ISO-8859-1"),"UTF-8");
//			System.out.println("user_id" + user_id);
			JSONObject jsonuser = null;
			User user = null;
			int userId;
			userId = Integer.parseInt(user_id);
			UserDAO dao = new UserDAO();

			user = dao.selectByUserId(userId);

			jsonuser = new JSONObject(user);
			response.getWriter().println(jsonuser);
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
