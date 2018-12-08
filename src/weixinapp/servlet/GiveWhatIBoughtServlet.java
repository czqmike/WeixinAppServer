package weixinapp.servlet;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;

import weixinapp.dao.*;
import weixinapp.model.*;
import weixinapp.util.JSONArrayUtil;

/**
 * Servlet implementation class GiveWhatIBoughtServlet
 */
/**
 * @author czqmike
 * @date 2018年8月16日
 * @description 从order表中选取good_id，然后用这（些）id到goods表中查询商品并返回
 * @param 需要在 GET 或 POST 中给出 user_id = ? 
 */
@WebServlet("/GiveWhatIBoughtServlet")
public class GiveWhatIBoughtServlet extends HttpServlet {
	private static final long serialVersionUID = 207L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GiveWhatIBoughtServlet() {
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
			ArrayList<Goods> list = GoodsDAO.selectWhatIBought(Integer.parseInt(user_id));

			JSONObject jsonobj = new JSONObject();
			JSONArray jsonarr = new JSONArray();
			GoodTypeDAO goodTypeDao = new GoodTypeDAO();

			jsonarr = JSONArrayUtil.createOrderJSONArray(list); // 替换以下代码↓↓↓↓↓

//			for (int i = 0; i < list.size(); ++i) {
//				Goods good = list.get(i);
//				ArrayList typeList = goodTypeDao.selectTypeId(good.getType_id());
//				good.setType_name((String)typeList.get(0));
//				good.setMain_class((String)typeList.get(1));
//				jsonarr.put(new JSONObject(good));
//			}
			
//			System.out.println(jsonarr);
			response.getWriter().print(jsonarr);
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
