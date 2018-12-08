package weixinapp.model;

import weixinapp.dao.GoodsDAO;

public class Goods {
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
	private String counseling_time_map;
	
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
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getMain_class() {
		return main_class;
	}
	public void setMain_class(String main_class) {
		this.main_class = main_class;
	}
	public String getCounseling_time_map() {
		return counseling_time_map;
	}
	public void setCounseling_time_map(String counseling_time_map) {
		this.counseling_time_map = counseling_time_map;
	}

	public Goods(int good_id, int user_id, String good_name, String good_detail, double price, boolean is_new,
			String trade_type, int type_id, String image_url, boolean sold) {
		super();
		this.good_id = good_id;
		this.user_id = user_id;
		this.good_name = good_name;
		this.good_detail = good_detail;
		this.price = price;
		this.is_new = is_new;
		this.trade_type = trade_type;
		this.type_id = type_id;
		this.image_url = image_url;
		this.sold = sold;
	}
	public Goods() {
		super();
	}
	
	public Goods(Goods good) {
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
	}

	
}
