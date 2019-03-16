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
import weixinapp.dao.UserDAO;
import weixinapp.model.*;
import weixinapp.util.JSONArrayUtil;
import weixinapp.util.ResponseUtil;

/**
 * @author czqmike
 * @date 2019年3月9日
 * @description 进入我的消息时触发的Servlet，以此获取与正在聊天的人(可能是复数个)的最近的一条聊天记录
 * @param user_id（这个id对应者Conv_Status中的user_lower）
 * @return 成功时返回convs（JSON数组），包含若干个conv 和 user信息。
			每个conv都包含conv_id, user_src, user_des, time, content，been_read对应着与这个人的最近的一条聊天记录，
				有多少个记录就意味着与多少个人保持着聊天状态（没有删除对话窗口）
			user信息包括user_name, user_id, avatal_url
 */
@WebServlet("/CheckChatingServlet")
public class CheckChatingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckChatingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");

		// 检测user_id合法性
		String user_lowerstr = request.getParameter("user_id");
		int user_lower = -1;
		try {
			user_lower = Integer.parseInt(user_lowerstr);
		} catch (Exception e) {
			ResponseUtil.showError(response, "Integer Parse Error!");
			return;
		}
		
		// 创建JSON数组并返回
		JSONArray jsonarr = new JSONArray();
		
		ArrayList<Conversation> list = ConversationDAO.selectLatest(user_lower);
		ArrayList<ConvWithUser> retlist = new ArrayList<ConvWithUser>();
		
		for (Conversation conv : list) {
			int uidtmp = (conv.getUser_des() != user_lower ? conv.getUser_des() : conv.getUser_src()) ;

			User user = UserDAO.selectByUserId(uidtmp);
			
			retlist.add(new ConvWithUser(conv, user));
		}
		
		jsonarr = JSONArrayUtil.createNormalJSONArray(retlist);
		
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
