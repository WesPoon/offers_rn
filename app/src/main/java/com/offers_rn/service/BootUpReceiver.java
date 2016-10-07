package com.offers_rn.service;

import com.offers_rn.SplashScreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class BootUpReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        /****** For Start Activity *****/

    	if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            //Intent.ACTION_BOOT_COMPLETED == android.intent.action.BOOT_COMPLETED

            Intent intent1 = new Intent(context , SplashScreen.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
            Log.d("receive","booting");
            Intent myIntent = new Intent(context, OfferService.class);
            context.startService(myIntent);
    	}
        /***** For start Service  ****/
    
    }   

}