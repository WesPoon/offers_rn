package com.offers_rn.detail;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseObject;


@ParseClassName("Gossip")
public class Gossip extends ParseObject implements Parcelable,java.io.Serializable {
	
	private String objectID;
	public String textcontent;
	private String gossiptype;
	private String posttype;
	private String sender;
	private int helpful;
	
	
	public void duplicateGossip(Gossip gossip1){
		
		this.objectID = gossip1.objectID;
		this.textcontent = gossip1.textcontent;
		this.gossiptype = gossip1.gossiptype;
		this.posttype = gossip1.posttype;
		this.helpful = gossip1.helpful;
		this.sender = gossip1.sender;
		
	}
	

	public String getTextContent(){
		
		
		if(this.textcontent==null){
			this.textcontent = getString("TextContent"); 
		}
		return this.textcontent;
		
	}
	
	public String getGossipType(){
		
		if(this.gossiptype==null){
			this.gossiptype = getString("GossipType");
		}	
		return this.gossiptype;
	}
	
	public String getPostType(){
		
		if(this.posttype==null){
			this.posttype = getString("PostType");
		}
		return this.posttype;
	}
	
	public int getHelpful()
	{
	    if(this.helpful==0){
	    	this.helpful = getInt("Helpful");
	    }
		return this.helpful;
		
	}
	
	public String getSender(){
		if(this.sender==null){
			this.sender = getString("Sender");
		}
		return this.sender;
	}
	
	public void setObjectID(String id){
		
		this.objectID = id;
	}
	
	public void setHelpful(int helpful){
		
		this.helpful = helpful;
	}
	
	public void setTextContent(String textcontent){
		
		this.textcontent = textcontent;
		
	}
	
	public void setGossipType(String gossiptype){
		
		this.gossiptype = gossiptype;
	}
	
	public void setPostType(String posttype){
		
		this.posttype = posttype;
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(this.objectID);
		dest.writeString(this.gossiptype);
		dest.writeString(this.posttype);
		dest.writeString(this.textcontent);
		dest.writeInt(this.helpful);
		
	}
	
	
}
