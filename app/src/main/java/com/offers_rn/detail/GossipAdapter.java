package com.offers_rn.detail;

import java.io.IOException;
import java.util.ArrayList;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Checkable;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shamanland.facebook.likebutton.FacebookLikeButton;
import com.offers_rn.MainApplication;
import com.offers_rn.R;
import com.offers_rn.db.ObjectUil;



public class GossipAdapter extends RecyclerView.Adapter<GossipAdapter.GossipViewHolder>  {

	
	ArrayList<Gossip> Savelist;
	
	public GossipAdapter(ArrayList<Gossip> access){
		this.Savelist = access ; 
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return Savelist.size();
	}
	
    public void remove(int position){
	  	
	    Savelist.remove(position);
		notifyItemRemoved(position);
		notifyItemRangeChanged(position, getItemCount());
		
		
	}

	@Override
	public GossipViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
		// TODO Auto-generated method stub
		
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.gossip_card, viewGroup, false);
		
		return new GossipViewHolder(itemView);
	}
	
	

	@Override
	public void onBindViewHolder(final GossipViewHolder saveView, final int position) {
		// TODO Auto-generated method stub
		
		Log.d(this.Savelist.get(position).getObjectId(), this.Savelist.get(position).getTextContent() );
		
		saveView.vTextContent.setText(this.Savelist.get(position).getTextContent());
		saveView.vCardTitle.setText(this.Savelist.get(position).getSender()+" "+this.Savelist.get(position).getGossipType()+"'s "+this.Savelist.get(position).getPostType());
		saveView.vHelpful.setText("Helpful ("+Integer.toString(this.Savelist.get(position).getHelpful())+")");
		saveView.vHelpful.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(!v.isSelected()){
				   v.setSelected(true);	
				   ParseQuery<ParseObject> query = ParseQuery.getQuery("Gossip");
			       query.getInBackground(Savelist.get(position).getObjectId(), new GetCallback<ParseObject>(){

					@Override
					public void done(ParseObject gossip, ParseException e) {
						// TODO Auto-generated method stub
					if(e==null){
						
						gossip.put("Helpful",gossip.getInt("Helpful")+1);
						gossip.saveInBackground();	
					}
					
					else{
					
						
					}
						
						
					}
			    	
			    });
				((TextView) v).setText("Helpful ("+Integer.toString(Savelist.get(position).getHelpful()+1)+")");
				
				}
				
				else{
					
					Log.d("clicked","clicked");
				}
				
				//onclick ends here
			}
		});
		
		saveView.itemView.setOnLongClickListener(new View.OnLongClickListener() {
	        @Override
	        public boolean onLongClick(View v) {
	//          Toast.makeText(v.getContext(), "Long Click Test "+Integer.toString(position), Toast.LENGTH_LONG).show();
	          v.showContextMenu();
	          //remove(i);
	          return true;
	        }
	      });

saveView.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, final View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		menu.add(0, v.getId(), 0, "Starred Messages ?")
		.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item){
            	
            	try {
            		Log.d("Test","1");
					String temp = ObjectUil.serializeObjectToString(GossipAdapter.this.Savelist.get(position));
					Log.d("temp=",temp);
					Log.d("class_name=",GossipAdapter.this.Savelist.get(position).getClass().getName());
					if(MainApplication.mydb.insert_StarMessage(temp, GossipAdapter.this.Savelist.get(position).getClass().getName())){
						Toast.makeText(saveView.itemView.getContext(), "Message Starred", Toast.LENGTH_LONG).show();
					}
	/*				Gossip temp_gossip = new Gossip();
					temp_gossip.duplicateGossip((Gossip)ObjectUil.deserializeObjectFromString(temp));
					Log.d("gossip=",temp_gossip.textcontent); */
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				//	Log.d("wes",e.getMessage());
				}
            	
                return true;
            }
        });//groupId, itemId, order, title
		
	}
	
	});
		
		
		
		
		
		
	}
	
	public static class GossipViewHolder extends RecyclerView.ViewHolder {      

		protected Gossip gossip;
		protected TextView vTextContent;
		protected TextView vCardTitle;
		protected FacebookLikeButton vHelpful;
 
		GossipViewHolder(View v) {
            super(v);
            
            vTextContent = (TextView) v.findViewById(R.id.gossip_card_context);
			vCardTitle = (TextView) v.findViewById(R.id.gossip_card_title);
			vHelpful = (FacebookLikeButton)v.findViewById(R.id.helpful_like);
        }
	}

	
		
}		
			
			


		
	
	
