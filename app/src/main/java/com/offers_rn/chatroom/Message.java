package com.offers_rn.chatroom;

import com.parse.ParseClassName;
import com.parse.ParseObject;


@ParseClassName("UserChatHistory")
public class Message extends ParseObject implements java.io.Serializable {
    public String text;
    public String name;
    public long time;
    public int livelikecount;
    public boolean selected = false;
    
    public void setTextContent(String text){
    	this.text = text;
    }
    
    public void setName(String name){
    	this.name = name;
    }
    
    public String getTextContent(){
    	if(this.text==null){
    		return getString("text");
    	}
    	else{
    		return this.text;
    	}
    }
    
    public String getName(){
    	if(this.name==null){
    		return getString("name");
    	}
    	else{
    		return this.name;
    	}
    }
    
    public long getTime(){
        return Long.valueOf(getString("time")).longValue();
    }
    
    public int getLiveLikeCount(){
    	return getInt("livelike");
    }
    
    public void setLiveLikeCount(int temp){
    	this.livelikecount = temp;
    }
    
    public void setSelected(boolean choice){
    	this.selected = choice;
    }
    
    public boolean IsSelected(){
    	return this.selected;
    }
    
}