package weixinapp.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinapp.dao.*;
import weixinapp.model.*;
import weixinapp.util.OpenIdUtil;

import org.json.*;


/**
 * @author czqmike
 * @date 2018年8月9日
 * @description 通过wx.login()提供的js_code向腾讯的服务器发送GET请求以获取OpenID
 *   			然后传回user_id，如果没在user表中找到此用户则插入后再传回其user_id
 *   			每次登陆时更新user表的avatar_url（用户头像）
 */
@WebServlet("/GetJsCodeServlet")
public class GetJsCodeServlet extends HttpServlet {
	

	private static final long serialVersionUID = 101L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetJsCodeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nick_name = new String(request.getParameter("nick_name").getBytes("ISO-8859-1"), "UTF-8");
		String avatar_url = request.getParameter("avatar_url");		String js_code = request.getParameter("js_code");
		
		String open_id = null;

		try {
			open_id = OpenIdUtil.getOpenIdByJsCode(js_code);	// 根据js_code查询此用户的open_id
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		int user_id = UserDAO.selectByOpenId(open_id);	// 根据open_id查询此用户的user_id，查询到了就返回，否则插入创建新用户后返回其user_id
		
		User user = new User(nick_name, 0, open_id, "", "", "", "", avatar_url); // 创建user实体，包含用户名，open_id，头像
		if (user_id == -1) {
			UserDAO.Insert(user);
			user_id = UserDAO.selectByOpenId(open_id);
		}
		
		JSONObject jsonobj = new JSONObject();
		try {
			jsonobj.put("user_id", user_id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		response.getWriter().print(jsonobj);
//		response.getWriter().print("{\"user_id\":\""+ user_id + "\"}");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
