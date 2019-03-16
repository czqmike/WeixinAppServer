package weixinapp.model;

public class ConvStatus {

	private int user_lower;
	private int user_upper;
	private boolean chating;
	
	public ConvStatus() {
		this.user_lower = -1;
		this.user_lower = -1;
		this.chating = false;
	}

	public ConvStatus(int user_lower, int user_upper, boolean chating) {
		this.user_lower = user_lower;
		this.user_upper = user_upper;
		this.chating = chating;
	}

	public int getUser_lower() {
		return user_lower;
	}
	public void setUser_lower(int user_lower) {
		this.user_lower = user_lower;
	}
	public int getUser_upper() {
		return user_upper;
	}
	public void setUser_upper(int user_upper) {
		this.user_upper = user_upper;
	}
	public boolean isChating() {
		return chating;
	}
	public void setChating(boolean chating) {
		this.chating = chating;
	}
	
}
