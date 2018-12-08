package weixinapp.model;

import java.time.LocalDateTime;

public class Order {
	int order_id;
	int user_id;
	int good_id;
	boolean done;
	LocalDateTime tradetime;
	
	public Order() {
		order_id = user_id = good_id = 0;
		done = false;
		tradetime = LocalDateTime.now();
	}
	public Order(int user_id, int good_id) {
		this.user_id = user_id;
		this.good_id = good_id;
		this.done = false;
		tradetime = LocalDateTime.now();
	}
	
	public Order(int user_id, int good_id, boolean done) {
		this.user_id = user_id;
		this.good_id = good_id;
		this.done = done;
		tradetime = LocalDateTime.now();
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

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

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public LocalDateTime getTradetime() {
		return tradetime;
	}

	public void setTradetime(LocalDateTime tradetime) {
		this.tradetime = tradetime;
	}
	
	
}
