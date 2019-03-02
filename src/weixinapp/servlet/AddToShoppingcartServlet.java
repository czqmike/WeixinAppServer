package weixinapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinapp.dao.ShoppingcartDAO;
import weixinapp.model.Shoppingcart;
import weixinapp.util.ResponseUtil;

/**
 * @author czqmike
 * @date 2018年8月18日
 * @description 添加至购物车的Servlet
 * @param user_id, good_id
 */
@WebServlet("/AddToShoppingcartServlet")
public class AddToShoppingcartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToShoppingcartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_id = request.getParameter("user_id");
		String good_id = request.getParameter("good_id");
		
		boolean insertFlag = true;
		if (user_id != null && good_id != null) {
			Shoppingcart sc = new Shoppingcart(Integer.parseInt(user_id), Integer.parseInt(good_id));

			insertFlag = ShoppingcartDAO.insert(sc);

			if (insertFlag == false) {
				String errmsg = "Insert into Database Error in [AddToShoppingcartServlet]!";
				System.err.println(errmsg);

				ResponseUtil.showError(response, errmsg);
				return;
			} else {
				ResponseUtil.showSuccess(response);
			}

		} else {
			String errmsg = "Get user_id or good_id error in [AddToShoppingcartServlet]!";
			System.err.println(errmsg);
			ResponseUtil.showError(response, errmsg);
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
