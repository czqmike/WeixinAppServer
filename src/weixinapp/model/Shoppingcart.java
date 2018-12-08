package weixinapp.model;

public class Shoppingcart {
	private int user_id;
	private int good_id;
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getGood_id() {
		return good_id;
	}
	public void setGood_id(int good_id) {
		this.good_id = good_id;
	}
	public Shoppingcart(int user_id, int good_id) {
		super();
		this.user_id = user_id;
		this.good_id = good_id;
	}
	public Shoppingcart() {
		super();
	}
	
	
}
