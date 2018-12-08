package weixinapp.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import weixinapp.dao.GoodsDAO;
import weixinapp.model.Goods;
import weixinapp.util.JSONArrayUtil;
import weixinapp.util.ResponseUtil;

/**
 * @author czqmike
 * @date 2018年9月15日
 * @description 
 *  a)	说明：获取某些商品的信息
	b)	参数：good_ids（JSON数组）
	c)	返回：成功时返回goods（JSON数组），失败时返回空数组
	d)	UID：211
 */
@WebServlet("/GiveGoodsServlet")
public class GiveGoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 211L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GiveGoodsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		
		String good_idsstr = request.getParameter("good_ids");
//		String good_idsstr = "[1, 2, 3]";
		JSONArray ret_arr = new JSONArray();
		
		try {
			JSONArray good_ids = new JSONArray(good_idsstr);
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < good_ids.length(); ++i) {
				list.add((int)good_ids.get(i));
			}
			
			ArrayList<Goods> ret_list = GoodsDAO.selectByGoodIds(list);
			
			ret_arr = JSONArrayUtil.createJSONArray(ret_list);

			response.getWriter().print(ret_arr);
		} catch (JSONException e) {
			e.printStackTrace();
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
