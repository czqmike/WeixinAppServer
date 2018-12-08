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
 * @author czqmike
 * @date 2018年8月20日
 * @description 接受搜索关键字并返回商品数组
 * @param 字符串 keyword 
 */
@WebServlet("/GiveSearchInfoServlet")
public class GiveSearchInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 203L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GiveSearchInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		String keyword = request.getParameter("keyword");
		JSONArray jsonarr = new JSONArray();
		if(keyword != null){
			keyword = new String(keyword.getBytes("ISO-8859-1"),"UTF-8");
			GoodsDAO dao = new GoodsDAO();
			GoodTypeDAO goodType = new GoodTypeDAO();
			
			ArrayList list = dao.selectGoodsWithKeyword(keyword);

			jsonarr = JSONArrayUtil.createJSONArray(list); // 替换以下代码↓↓↓↓↓
//			for(int i = 0; i < list.size(); ++i){
//				Goods good = (Goods) list.get(i);
//				ArrayList listType = goodType.selectTypeId(good.getType_id());
//				good.setType_name((String)listType.get(0));
//				good.setMain_class((String)listType.get(1));
//				jsonarr.put(new JSONObject(good));
//			}
			// 替换以上代码↑↑↑↑↑
		}
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
