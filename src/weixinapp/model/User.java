package weixinapp.model;

import weixinapp.dao.UserDAO;

public class User {
	static private int normalcp = 80; // 一般的credit_point 

	private int user_id;
	private String user_name;
	private int sex;
	private String open_id;

	private String tel;
	private String mail;
	private String address;
	private String weixin_no;
	private String avatar_url;
	private int credit_point;
	

	public User(String user_name, int sex, String open_id, String weixin_no, String tel, String mail, String address) {
		super();
		this.user_name = user_name;
		this.sex = sex;
		this.open_id = open_id;
		this.weixin_no = weixin_no;
		this.tel = tel;
		this.mail = mail;
		this.address = address;
		this.credit_point = normalcp;
	}
	
	public User(String user_name, int sex, String open_id, String weixin_no, String tel, 
				String mail, String address, String avatar_url) {
		this.user_name = user_name;
		this.sex = sex;
		this.open_id = open_id;
		this.weixin_no = weixin_no;
		this.tel = tel;
		this.mail = mail;
		this.address = address;
		this.avatar_url = avatar_url;
		this.credit_point = normalcp;
	}

	public User() {
		super();
	}

	public String getWeixin_no() {
		return weixin_no;
	}

	public void setWeixin_no(String weixin_no) {
		this.weixin_no = weixin_no;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getOpen_id() {
		return open_id;
	}

	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAvatar_url() {
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	
	public int getCredit_point() {
		return credit_point;
	}

	public void setCredit_point(int credit_point) {
		this.credit_point = credit_point;
	}


}
