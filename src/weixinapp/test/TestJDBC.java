package weixinapp.test;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import weixinapp.util.JDBCUtil;
import weixinapp.dao.*;
import weixinapp.model.*;

class TestJDBC {

	@Test
	void test() {
		ArrayList<Integer> list = ShoppingcartDAO.selectGoodIdByUserId(1);
		for (int i = 0; i < list.size(); ++i) {
			System.out.println(list.get(i));
		}
//		fail("Not yet implemented");
	}

}
