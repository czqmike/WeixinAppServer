package weixinapp.model;

import java.time.LocalDateTime;

import weixinapp.util.FormatterUtil;

public class Message {
	private int message_id;
	private int good_id;
	private int user_src;
	private int user_des;
	private LocalDateTime time;
	private String  content;

	private String user_name;  // 发信人的微信名以及头像
	private String avatar_url;

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
	public int getGood_id() {
		return good_id;
	}
	public void setGood_id(int good_id) {
		this.good_id = good_id;
	}
	public int getMessage_id() {
		return message_id;
	}
	public void setMessage_id(int message_id) {
		this.message_id = message_id;
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
	
	// 返回原始格式
//	public LocalDateTime getLocalDateTime() {
//		return time;
//	}

	// 返回String类型的格式化后的时间
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

	public Message(int good_id, int user_src, int user_des, String content) {
		this.good_id = good_id;
		this.user_src = user_src;
		this.user_des = user_des;
		this.time = LocalDateTime.now();
		this.content = content;
	}
	public Message() {
		this.time = LocalDateTime.now();
	}
	
	
	
}
