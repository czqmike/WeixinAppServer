package weixinapp.test;

import static org.junit.jupiter.api.Assertions.*;

import org.json.*;
import org.junit.jupiter.api.Test;

class TestJSON {

	@Test
	void test() {
	    JSONObject object = new JSONObject();
	    Object nullObj = null;
	    try {
	        object.put("name", "王小二");
	        object.put("age", 25.2);
	        object.put("birthday", "1990-01-01");
	        object.put("school", "蓝翔");
	        object.put("major", new String[] {"理发", "挖掘机"});
	        object.put("has_girlfriend", false);
	        object.put("car", nullObj);
	        object.put("house", nullObj);
	        object.put("comment", "这是一个注释");

	        System.out.println(object.toString());

	    } catch (JSONException e) {
	        e.printStackTrace();
	    }
//		fail("Not yet implemented");
	}

}
