package com.offers_rn.chatroom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceActivity.Header;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.offers_rn.R;
import com.offers_rn.parseobject.Exchange;
import com.offers_rn.parseobject.GradJobs;

import org.json.JSONObject;
import org.json.JSONStringer;

public class Chatroom extends ActionBarActivity implements View.OnClickListener {

    EditText messageInput;
    Button sendButton;
    final String MESSAGES_ENDPOINT="http://54.65.1.149/offers/";
    String username;
    String profileUrl;
    Pusher pusher = new Pusher("8057e8f52fc2b9ecf004");
    Channel channel;
    
    List<Message> history = new ArrayList<Message>();
    ListView messagesView;
    
    MessageAdapter messageAdapter;
    private BroadcastReceiver mReceiver;
    SubscriptionEventListener sEventListener;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom);

        Log.d("Created","Chatroom");
        messageInput = (EditText) findViewById(R.id.message_input);
        sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(this);
      
        messageAdapter = new MessageAdapter(this, new ArrayList<Message>());
        messagesView = (ListView) findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);
        
        ParseQuery<Message> query = ParseQuery.getQuery("UserChatHistory");
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // substract 7 days
        // If we give 7 there it will give 8 days back
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-6);
        // convert to date
        Date retrive_date = cal.getTime();
        query.whereGreaterThan("createdAt",retrive_date);
        //query.addDescendingOrder("createdAt");
        query.addAscendingOrder("createdAt");
        query.findInBackground(new FindCallback<Message>() {

			@Override
			public void done(List<Message> arg0, ParseException arg1) {
				// TODO Auto-generated method stub
				
				for (Message retrive_message : arg0){
					 Message msg = new Message();
					 msg.setName(retrive_message.getName());
					 msg.setTextContent(retrive_message.getTextContent());
					 msg.setObjectId(retrive_message.getObjectId());
					 msg.setLiveLikeCount(retrive_message.getLiveLikeCount());
                     msg.setProfilePicUrl(retrive_message.getProfilePicUrl());
					 history.add(msg);
				     messageAdapter.add(msg);
				}
				messagesView.setSelection(messageAdapter.getCount() - 1);
			}
        	
        	
        });
        
        // initialize Pusher
        
       // connect to the Pusher API
        pusher.connect();
        // subscribe to our "messages" channel
        channel = pusher.subscribe("messages");
         // listen for the "new_message" event
        
        sEventListener = new SubscriptionEventListener() {
            public void onEvent(String channelName, String eventName, final String data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        Message message = gson.fromJson(data, Message.class);
                        message.setLiveLikeCount(0);
              //          messageAdapter = new MessageAdapter(getContext(), history);
                        messageAdapter.add(message);
          //              messageAdapter.notifyDataSetInvalidated();
                        // have the ListView scroll down to the new message
                        messagesView.setSelection(messageAdapter.getCount() - 1);
                    }
                });
            }
         };
         
         channel.bind("new_message",sEventListener);
        
 /*       channel.bind("new_message", new SubscriptionEventListener() {
            public void onEvent(String channelName, String eventName, final String data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        Message message = gson.fromJson(data, Message.class);
                        messageAdapter.add(message);
                        // have the ListView scroll down to the new message
                        messagesView.setSelection(messageAdapter.getCount() - 1);
                    }
                });
            }
        }); */
		SharedPreferences settings = getSharedPreferences(this.getApplicationContext().getString(R.string.app_name), 0);
	    username = settings.getString("username", "/");
        profileUrl = settings.getString("profile_pic_url","/");
   }
    
    @Override
    public void onClick(View v) {
    	postMessage();
    }

    private void postMessage()  {
        String text = messageInput.getText().toString();
         // return if the text is blank
        if (text.equals("")) {
            return;
        }
        RequestParams params = new RequestParams();
        // set our JSON object
        params.put("text", text);
        params.put("name", username);
        params.put("time", new Date().getTime());
        params.put("url", profileUrl);
            
        // create our HTTP client
        AsyncHttpClient client = new AsyncHttpClient();        
        client.post(MESSAGES_ENDPOINT, params, new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            	messageInput.setText("");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageInput.setText("");
                      //  Log.d("Chatroom","success");
                    }
                });
            }
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(
                        getApplicationContext(),
                        "Something went wrong :(",
                        Toast.LENGTH_LONG
                ).show();                
            }
        });
        messageInput.setText("");
    }
    
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
 
		IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
 
		mReceiver = new BroadcastReceiver() {
 
			@Override
			public void onReceive(Context context, Intent intent) {
				
				 if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
					 ConnectivityManager cm =
						        (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
						NetworkInfo networkInfo = cm.getActiveNetworkInfo();
					 //		            NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
			            if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
			                
			            	 Toast.makeText(context, "Reconnecting...", Toast.LENGTH_LONG).show();
/*			            	 pusher.disconnect();
			            	// connect to the Pusher API
			                 pusher.connect();
			                 // subscribe to our "messages" channel
			            	 pusher.unsubscribe("messages");
			                 channel = pusher.subscribe("messages");
			                  // listen for the "new_message" event
			            	 Log.d("Debug","Debug network");
			            	 channel.unbind("new_message",sEventListener);
			            	 channel.bind("new_message",sEventListener); */
			            	 
/*			                 channel.bind("new_message", new SubscriptionEventListener() {
			                     public void onEvent(String channelName, String eventName, final String data) {
			                         runOnUiThread(new Runnable() {
			                             @Override
			                             public void run() {
			                                 Gson gson = new Gson();
			                                 Message message = gson.fromJson(data, Message.class);
			                                 messageAdapter.add(message);
			                                 // have the ListView scroll down to the new message
			                                 messagesView.setSelection(messageAdapter.getCount() - 1);
			                             }
			                         }); 
			                     } 
			                 }); */
			            } else if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.DISCONNECTED) {
			                Log.d("Network", "No internet :("); 
			            } 
			        } 
 
			}

		};
		//registering our receiver
		this.registerReceiver(mReceiver, intentFilter);
	}
    
    @Override
   	protected void onPause() {
    	super.onPause();
    	this.unregisterReceiver(mReceiver);
    }
    
   
}