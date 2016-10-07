package com.offers_rn.menulist;

public class StarredMessage {
	
	private String type;
	private String message;
	
	public StarredMessage(String message, String type){
		this.type = type;
		this.message = message;
	}
	
	public String get_message(){
		return this.message;
	}
	
	public String get_type(){
		return this.type;
	}
}
