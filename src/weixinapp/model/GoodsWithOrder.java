package weixinapp.model;

import java.time.LocalDateTime;

import weixinapp.util.FormatterUtil;

public class GoodsWithOrder{

	private int good_id;
	private int user_id;
	private String good_name;
	private String good_detail;
	private double price;
	private boolean is_new;
	private String trade_type;
	private int type_id;
	private String image_url;
	private boolean sold;
	private String type_name;
	private String main_class;
	private String countseling_time_map;

	private String user_name;
	private String weixin_no;
	private String avatar_url;
	private String address;
	private int credit_point;
	
	private boolean done = false;
	LocalDateTime tradetime;

	public GoodsWithOrder() {
		super();
	}

	public GoodsWithOrder(Goods good, User user, Order order) {
		this.good_id = good.getGood_id();
		this.user_id = good.getUser_id();
		this.good_name = good.getGood_name();
		this.good_detail = good.getGood_detail();
		this.price = good.getPrice();
		this.is_new = good.isIs_new();
		this.trade_type = good.getTrade_type();
		this.type_id = good.getType_id();
		this.image_url = good.getImage_url();
		this.sold = good.isSold();
		
		this.type_name = good.getType_name();
		this.main_class = good.getMain_class();
		
		this.countseling_time_map = good.getCounseling_time_map();

		this.user_name = user.getUser_name();
		this.weixin_no = user.getWeixin_no();
		this.avatar_url = user.getAvatar_url();
		this.address = user.getAddress();
		this.credit_point = user.getCredit_point();
		
		this.done = order.isDone();
		this.tradetime = order.getTradetime();
	}

	public String getAvatar_url() {
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public String getMain_class() {
		return main_class;
	}

	public void setMain_class(String main_class) {
		this.main_class = main_class;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public int getGood_id() {
		return good_id;
	}
	public void setGood_id(int good_id) {
		this.good_id = good_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getGood_name() {
		return good_name;
	}
	public void setGood_name(String good_name) {
		this.good_name = good_name;
	}
	public String getGood_detail() {
		return good_detail;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setGood_detail(String good_detail) {
		this.good_detail = good_detail;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public boolean isIs_new() {
		return is_new;
	}
	public void setIs_new(boolean is_new) {
		this.is_new = is_new;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public int getType_id() {
		return type_id;
	}
	public void setType_id(int type_id) {
		this.type_id = type_id;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public boolean isSold() {
		return sold;
	}
	public void setSold(boolean sold) {
		this.sold = sold;
	}
	public String getCountseling_time_map() {
		return countseling_time_map;
	}

	public void setCountseling_time_map(String countseling_time_map) {
		this.countseling_time_map = countseling_time_map;
	}

	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getWeixin_no() {
		return weixin_no;
	}
	public void setWeixin_no(String weixin_no) {
		this.weixin_no = weixin_no;
	}
	
	public int getCredit_point() {
		return credit_point;
	}

	public void setCredit_point(int credit_point) {
		this.credit_point = credit_point;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public String getTradetime() {
		return FormatterUtil.formatter.format(this.tradetime);
	}

	public void setTradetime(LocalDateTime tradetime) {
		this.tradetime = tradetime;
	}
}
