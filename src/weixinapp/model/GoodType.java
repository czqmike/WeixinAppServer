package weixinapp.model;

public class GoodType {
	
	private int type_id;
	private String type_name;
	private String main_class;
	
	
	public int getType_id() {
		return type_id;
	}
	public void setType_id(int type_id) {
		this.type_id = type_id;
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
	
	public GoodType(String type_name, String main_class) {
		super();
		this.type_name = type_name;
		this.main_class = main_class;
	}
	
	public GoodType() {
		super();
	}
	
	
	
}
