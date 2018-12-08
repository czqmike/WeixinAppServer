package weixinapp.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {

	public static void showSuccess(HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			out.print("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void showError(HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			response.setStatus(303);
			out.print("error");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
