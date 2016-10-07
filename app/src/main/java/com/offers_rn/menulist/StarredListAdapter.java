package com.offers_rn.menulist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.shamanland.facebook.likebutton.FacebookLikeButton;
import com.offers_rn.R;
import com.offers_rn.chatroom.Message;
import com.offers_rn.db.ObjectUil;
import com.offers_rn.detail.Gossip;
import com.offers_rn.menulist.SaveAndBlackListAdapter.SaveViewHolder;
import com.offers_rn.parseobject.Jobs;

import android.opengl.Visibility;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StarredListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private List<Object> Savelist = new ArrayList<Object>();
	
	public enum ITEM_TYPE { 
		        ITEM_TYPE_GOSSIP, 
		        ITEM_TYPE_MESSAGE
		     } 
	
	public static <T> T cast(Object o, String clazz) throws ClassNotFoundException {
        return (T) Class.forName(clazz).cast(o);
    }

	
	public StarredListAdapter(ArrayList<StarredMessage> templist){
		for(StarredMessage wes:templist){
			try {
				Savelist.add(cast(ObjectUil.deserializeObjectFromString(wes.get_message()),wes.get_type()));
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return Savelist.size();
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder saveView, int position) {
		// TODO Auto-generated method stub
	//	StarredMessage msg = (StarredMessage)this.Savelist.get(position);
		if (saveView instanceof MessageViewHolder) {
	//		((MessageViewHolder) saveView).vSender.setText("wes");
			((MessageViewHolder) saveView).vSender.setText(((Message)this.Savelist.get(position)).getName());
			((MessageViewHolder) saveView).vText.setText(((Message)this.Savelist.get(position)).getTextContent());
		}
		else if (saveView instanceof GossipViewHolder){
			Gossip gp = (Gossip)this.Savelist.get(position);
			((GossipViewHolder) saveView).vGossip_Title.setText(gp.getSender()+" "+gp.getGossipType()+"'s "+gp.getPostType());
			((GossipViewHolder) saveView).vGossip_Context.setText(gp.getTextContent());
		}
		
		
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewtype) {
		
		if (viewtype==ITEM_TYPE.ITEM_TYPE_GOSSIP.ordinal()){
			View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.gossip_card, viewGroup, false);
			Log.d("OncreateViewHolder","Gossip");
			return new GossipViewHolder(itemView);
		}
		else{
			View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
					R.layout.message, viewGroup, false);
			Log.d("OncreateViewHolder","Message");
			return new MessageViewHolder(itemView);
		}
	}
	
	@Override
	public int getItemViewType(int position) {
		if(this.Savelist.get(position).getClass() == Gossip.class)
			return ITEM_TYPE.ITEM_TYPE_GOSSIP.ordinal() ;
		else 
			return ITEM_TYPE.ITEM_TYPE_MESSAGE.ordinal();
	}
	
	
	
	public static class GossipViewHolder extends RecyclerView.ViewHolder {      
		
		protected TextView vGossip_Title;
		protected TextView vGossip_Context;
		protected FacebookLikeButton helpful;
		
        GossipViewHolder(View v) {
            super(v);     
            vGossip_Title = (TextView) v.findViewById(R.id.gossip_card_title);
            vGossip_Context = (TextView) v.findViewById(R.id.gossip_card_context);
			helpful = (FacebookLikeButton)v.findViewById(R.id.helpful_like);
			helpful.setVisibility(View.GONE);
        }
        
	}
	
	public static class MessageViewHolder extends RecyclerView.ViewHolder { 
		
		protected FacebookLikeButton like_button;
		protected TextView vSender;
		protected TextView vText;
		
        MessageViewHolder(View v) {
            super(v);     
            like_button = (FacebookLikeButton)v.findViewById(R.id.live_like);
            like_button.setVisibility(View.GONE);
            ((RecyclerView.LayoutParams) v.getLayoutParams()).setMargins(4,5,4,1);
            vSender = (TextView) v.findViewById(R.id.message_sender);
            vText = (TextView)v.findViewById(R.id.message_body);
        }
        
	}
	
	

}
