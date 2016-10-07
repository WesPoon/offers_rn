package com.offers_rn.chatroom;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shamanland.facebook.likebutton.FacebookLikeButton;
import com.squareup.picasso.Picasso;
import com.offers_rn.MainApplication;
import com.offers_rn.R;
import com.offers_rn.db.ObjectUil;
import com.offers_rn.detail.GossipAdapter;
import com.offers_rn.uielement.RoundedImageView;

public class MessageAdapter extends BaseAdapter {

    Context messageContext;
    ArrayList<Message> messageList;

    public MessageAdapter(Context context, ArrayList<Message> messages){
        messageList = messages;
        messageContext = context;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
        return messageList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
        return messageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
        return position;
	}

    @SuppressWarnings("deprecation")
	@Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final MessageViewHolder holder;
     // get the message from its position in the ArrayList
        final Message message = (Message) getItem(position);
        
        Log.d("Making ListView","Making");

        // if there is not already a view created for an item in the Message list.

        if (convertView == null){
            LayoutInflater messageInflater = (LayoutInflater) messageContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            // create a view out of our `message.xml` file
            convertView = messageInflater.inflate(R.layout.message, null);

            // create a MessageViewHolder 
            holder = new MessageViewHolder();
            
            Log.d("Making ListView","Making");
            
            // set the holder's properties to elements in `message.xml`
            holder.thumbnailImageView = (RoundedImageView) convertView.findViewById(R.id.img_thumbnail);
            holder.senderView = (TextView) convertView.findViewById(R.id.message_sender);
            holder.bodyView = (TextView) convertView.findViewById(R.id.message_body);
            
             // assign the holder to the view we will return
            convertView.setTag(holder);
        } else {

              // otherwise fetch an already-created view holder
            holder = (MessageViewHolder) convertView.getTag();
        }

     
        holder.LiveLikeView = (FacebookLikeButton) convertView.findViewById(R.id.live_like);
        holder.LiveLikeView.setDrawableLeft(convertView.getContext().getResources().getDrawable(R.drawable.live_like));
        holder.LiveLikeView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(!message.selected){
				   v.setSelected(true);
				   message.setSelected(true);
				   notifyDataSetChanged();
				   ParseQuery<ParseObject> query = ParseQuery.getQuery("UserChatHistory");
				
			       query.getInBackground(message.getObjectId(), new GetCallback<ParseObject>(){

					@Override
					public void done(ParseObject msg, ParseException e) {
						// TODO Auto-generated method stub
					if(e==null){
						
						msg.put("livelike",msg.getInt("livelike")+1);
						msg.saveInBackground();	
					}
					
					else{
						Log.d("123",e.getMessage());
						
					}
						
						
					}
			    	
			    });
				
				
				}
				
				else{
					
					Log.d("clicked","clicked"); 
				}
				//onclick ends here
			}
		});
        // set the elements' contents
        holder.bodyView.setText(message.text);
        holder.senderView.setText(message.name);
        holder.LiveLikeView.setText(Integer.toString(message.livelikecount));
        holder.LiveLikeView.setSelected(message.IsSelected());
        
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
	        @Override
	        public boolean onLongClick(View v) {
	//          Toast.makeText(v.getContext(), "Long Click Test "+Integer.toString(position), Toast.LENGTH_LONG).show();
	          v.showContextMenu();
	          //remove(i);
	          return true;
	        }
	      });

        convertView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
	
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
					String temp = ObjectUil.serializeObjectToString(MessageAdapter.this.messageList.get(position));
					Log.d("temp=",temp);
					Log.d("class_name=",MessageAdapter.this.messageList.get(position).getClass().getName());
					if(MainApplication.mydb.insert_StarMessage(temp, MessageAdapter.this.messageList.get(position).getClass().getName())){
						Toast.makeText(holder.LiveLikeView.getContext(), "Message Starred", Toast.LENGTH_LONG).show();
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

         // fetch the user's Twitter avatar from their username
         // and place it into the thumbnailImageView.
        Picasso.with(messageContext).
                load("https://twitter.com/" + message.name + "/profile_image?size=original").
                //placeholder(R.mipmap.ic_launcher).
                placeholder(R.drawable.ic_launcher).
                into(holder.thumbnailImageView); 
        
    /*    Picasso.with(messageContext).
        load("http://lorempixel.com/400/200/").
        //placeholder(R.mipmap.ic_launcher).
        placeholder(R.drawable.ic_launcher).
        into(holder.thumbnailImageView); */
        
        
        
        return convertView;
    }

    private static class MessageViewHolder{
        
    	
            
    	public RoundedImageView thumbnailImageView;
        public TextView senderView;
        public TextView bodyView;
        public FacebookLikeButton LiveLikeView;
        
        
    }

    public void add(Message message){
        messageList.add(message);
        notifyDataSetChanged();
        
    }

}

	
	