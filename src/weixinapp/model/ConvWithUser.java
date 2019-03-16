package weixinapp.model;

import java.time.LocalDateTime;

public class ConvWithUser {
	
	private int conv_id;
	private int user_src;
	private int user_des;
	private String time;	// String类型的time，由util中的工具从LocalDatetime转化而来
	private String content;
	private boolean been_read;

	private int user_id;
	private String user_name;
	private String avatar_url;
	
	public ConvWithUser(Conversation conv, User user) {
		this.conv_id = conv.getConv_id();
		this.user_src = conv.getUser_src();
		this.user_des = conv.getUser_des();
		this.time = conv.getTime();
		this.content = conv.getContent();
		this.been_read = conv.isBeen_read();
		
		this.user_id = user.getUser_id();
		this.user_name = user.getUser_name();
		this.avatar_url = user.getAvatar_url();
	}

	public int getConv_id() {
		return conv_id;
	}

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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
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

	public String getAvatar_url() {
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
}
