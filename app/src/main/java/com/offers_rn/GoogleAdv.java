package com.offers_rn;

/**
 * Created by user on 26/8/2017.
 */
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import android.widget.Toast;
import com.google.android.gms.ads.NativeExpressAdView;


public class GoogleAdv extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private Boolean screenDisable;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admob_1);
        getSupportActionBar().hide();
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        NativeExpressAdView adView = (NativeExpressAdView)findViewById(R.id.adView1);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        NativeExpressAdView adView2 = (NativeExpressAdView)findViewById(R.id.adView2);
        AdRequest request2 = new AdRequest.Builder().build();
        adView2.loadAd(request2);


        counter_start();
    }

    private void counter_start() {
        countDownTimer: new CountDownTimer(10 * 1000, 1000) {

            @Override
            public void onTick(long um) {

                screenDisable = true;
            }

            @Override
            public void onFinish() {
                screenDisable = false;
                Toast.makeText(getApplicationContext(), "10 squeek pts added ~", Toast.LENGTH_SHORT).show();
            }

        }.start();
    }

    private void counter_finish(){
            screenDisable = false;
    }

   @Override
        public void onBackPressed() {
            //super.onBackPressed();
            if(screenDisable){
                //THIS BLOCK WILL NOT DO ANYTHING AND WOULD DISABLE BACK BUTTON

            }else{
                super.onBackPressed();
//THIS BLOCK WILL BE CALLED IF ABOVE COND IS FALSE, AND WOULD ENABLE BACK BUTTON
            }

        }



}