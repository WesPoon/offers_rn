package com.offers_rn.service;

import java.util.ArrayList;
import java.util.List;

import com.offers_rn.Constants;
import com.offers_rn.MainActivity;
import com.offers_rn.R;
import com.offers_rn.Singleton;
import com.offers_rn.parseobject.Jobs;
import com.offers_rn.parseobject.RubbishJobs;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.support.v7.app.NotificationCompat.Builder;
import android.util.Log;

public class OfferService extends Service{
	
	
	private NotificationManager mNotificationManager;
	private NotificationCompat.Builder mNotifyBuilder;
	private List<Jobs> Recent = new ArrayList<Jobs>();
	
	
	public void onCreate() {
		super.onCreate();
		
		Log.d("Test","2");
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
			super.onStartCommand(intent, flags, startId);
			
			Log.d("Test","3");
			
			for(int i=0;i<=1;i++){
				
				try{
					
					Recent.add(Singleton.getInstance().getList(Constants.internship).get(i));
					Recent.add(Singleton.getInstance().getList(Constants.exchange).get(i));
					Recent.add(Singleton.getInstance().getList(Constants.gradjob).get(i));
					Recent.add(Singleton.getInstance().getList(Constants.competition).get(i));
				}
				catch(NullPointerException e){
					
				}
				catch(IndexOutOfBoundsException e){
					
				}
			}
			
			
			NotificationCompat.InboxStyle inboxStyle =
			        new NotificationCompat.InboxStyle();
			// Sets a title for the Inbox in expanded layout
			inboxStyle.setBigContentTitle("Closing Event:");
			// Moves events into the expanded layout
			for(Jobs recent1 : Recent){
				inboxStyle.addLine(recent1.getJobTitle()+" - "+recent1.getCompany());
				
			}
			mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			mNotifyBuilder = (Builder) new NotificationCompat.Builder(this)
			.setContentTitle("OFFERS")
			.setContentText("Hurry ! Following offers would close soon:")
			.setStyle(inboxStyle)
	        .setSmallIcon(R.drawable.ic_launcher).setOngoing(true);
			
			
			// Moves the expanded layout object into the notification object.
			

			
			Intent notificationIntent = new Intent(this, MainActivity.class);
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
	                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
	        PendingIntent intent1 = PendingIntent.getActivity(this, 0,
	                notificationIntent, 0);

	        mNotifyBuilder.setContentIntent(intent1);
			
			Notification note = mNotifyBuilder.build();
		    note.defaults |= Notification.DEFAULT_VIBRATE;
		    note.defaults |= Notification.DEFAULT_SOUND;
		    note.priority = Notification.PRIORITY_MAX;
			mNotificationManager.notify(1, note);
			
		    return Service.START_STICKY;
	}
	


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onTaskRemoved(Intent intent){
		super.onTaskRemoved(intent);
		Intent intent1=new Intent(this,this.getClass()); 
		startService(intent1); 
	}

}
