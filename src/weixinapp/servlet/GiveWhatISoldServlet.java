package weixinapp.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import weixinapp.dao.GoodTypeDAO;
import weixinapp.dao.GoodsDAO;
import weixinapp.model.Goods;
import weixinapp.util.JSONArrayUtil;

/**
 * @author zzc
 * @date 2018年8月22日
 * @description 给出某人发布的，且sold为true的商品列表 
 */
@WebServlet("/GiveWhatISoldServlet")
public class GiveWhatISoldServlet extends HttpServlet {
	private static final long serialVersionUID = 208L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GiveWhatISoldServlet() {
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
		JSONArray jsonarr = new JSONArray();
		if(user_id != null){
			user_id = new String(user_id.getBytes("ISO-8859-1"),"UTF-8");
//			System.out.println("user_id = " + user_id);
			GoodsDAO dao = new GoodsDAO();
			GoodTypeDAO goodType = new GoodTypeDAO();
			int userId = userId = Integer.parseInt(user_id);

			ArrayList list = dao.selectUserIdSold(userId,true);

			jsonarr = JSONArrayUtil.createJSONArray(list); // 替换以下代码↓↓↓↓↓
//			for(int i = 0; i < list.size(); ++i){
//				Goods good = (Goods) list.get(i);
//				ArrayList listType = goodType.selectTypeId(good.getType_id());
//				good.setType_name((String)listType.get(0));
//				good.setMain_class((String)listType.get(1));
//				jsonarr.put(new JSONObject(good));
//			}
		}
//		System.out.println(jsonarr);
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
