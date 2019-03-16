package weixinapp.model;

import java.time.LocalDateTime;

import weixinapp.util.FormatterUtil;

public class Conversation {
	
	private int conv_id;
	private int user_src;
	private int user_des;
	private LocalDateTime time;	// 类中存储的是LocalDateTime格式，但是getTime()返回的是格式化后的String
	private String content;
	private boolean been_read;

	public Conversation() {
		this.conv_id = this.user_src = this.user_des = -1;
		this.time = LocalDateTime.now();
		this.content = "";
		this.been_read = false;
	}

	public Conversation(Conversation con) {
		this.conv_id = con.conv_id;
		this.user_src = con.user_src;
		this.user_des = con.user_des;
		this.time = con.time;
		this.content = con.content;
		this.been_read = con.isBeen_read();
	}

	public Conversation(int user_src, int user_des, String content) {
		this.user_src = user_src;
		this.user_des = user_des;
		this.time = LocalDateTime.now();
		this.content = content;
		this.been_read = false;
	}

	public int getConv_id() {
		return conv_id;
	}

//	conv_id is set by MySQL
	public void setConv_id(int conv_id) {
		this.conv_id = conv_id;
	}

	public int getUser_src() {
		return user_src;
	}

	public void setUser_src(int user_src) {
		this.user_src = user_src;
	}

	public int getUser_des() {
		return user_des;
	}

	public void setUser_des(int user_des) {
		this.user_des = user_des;
	}

//	public LocalDateTime getTime() {
//		return time;
//	}
	public String getTime() {
		return FormatterUtil.formatter.format(this.time);
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isBeen_read() {
		return been_read;
	}

	public void setBeen_read(boolean been_read) {
		this.been_read = been_read;
	}


}
