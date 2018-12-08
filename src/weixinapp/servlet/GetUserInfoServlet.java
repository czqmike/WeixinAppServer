package weixinapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import weixinapp.dao.UserDAO;
import weixinapp.model.User;
import weixinapp.util.ResponseUtil;

/**
 * @author zzc
 * @date 2018年8月21日
 * @description 更新用户信息的Servlet
 */
@WebServlet("/GetUserInfoServlet")
public class GetUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 105L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		String userInfo = request.getParameter("user");
		if(userInfo != null){
			userInfo = new String(userInfo.getBytes("ISO-8859-1"),"UTF-8");
//			System.out.println("user = " + userInfo);
			JSONObject jsonobj = null;
			int user_id;
			String user_name = "";
			int sex = 0;
			String weixin_no = "";
			String tel = "";
			String mail = "";
			String address = "";
			try {
				jsonobj = new JSONObject(userInfo);
				user_id = jsonobj.getInt("userId");
				user_name = jsonobj.getString("user_name");
				sex = jsonobj.getInt("sex");
				weixin_no = jsonobj.getString("weixin_no");
				tel = jsonobj.getString("tel");
				mail = jsonobj.getString("mail");
				address = jsonobj.getString("address");
				
				User user = new User();
				user.setUser_id(user_id);
				user.setUser_name(user_name);
				user.setSex(sex);
				user.setWeixin_no(weixin_no);
				user.setTel(tel);
				user.setMail(mail);
				user.setAddress(address);
				
				UserDAO dao = new UserDAO();
				dao.updateUser(user);
				ResponseUtil.showSuccess(response);
				
			} catch (JSONException e) {
				e.printStackTrace();
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
