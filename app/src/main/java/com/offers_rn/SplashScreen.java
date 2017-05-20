package com.offers_rn;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.offers_rn.parseobject.Competition;
import com.offers_rn.parseobject.Exchange;
import com.offers_rn.parseobject.GradJobs;
import com.offers_rn.parseobject.Jobs;
import com.offers_rn.profile.ChooseUniActivity;
import com.offers_rn.profile.Profile;
import com.offers_rn.service.OfferService;

public class SplashScreen extends Activity {
	
	private static boolean InternQueryDone = false;
	private static boolean GradJobQueryDone = false;
    private static boolean ExchangeQueryDone = false;
    private static boolean CompetitionQueryDone = false;
	
	
	Profile profile = new Profile();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.splash_screen);
		
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		
		if(!isNetworkConnected()) {
		buildDialog(SplashScreen.this).show();

		}
		
		else{
			
			if (!LoadSP()) {
				
				
				startBuildProfile();
				onDestroy();
				
			}
			
			else{
		
	    Thread logoTimer = new Thread() {    //wes: using thread to delay 5s for splash screen
            public void run(){
                try{
                	
                	if (((MainApplication)SplashScreen.this.getApplication()).getInternListCount()==0){
                	InternJobQuery();
                	GradJobQuery();
                	ExchangeQuery();
                	CompetitionQuery();
                	}
                	
                	while(true){
                		if(InternQueryDone){
                			if(ExchangeQueryDone){
                				if(GradJobQueryDone){
                					if(CompetitionQueryDone){
                						break;
                					}
                				}
                			}
                		}
						break;
                	}
                	
                	Log.d("Test","0");
                	Log.d("competition",Integer.toString(Singleton.getInstance().getCompListCount()));
                	Log.d("internship",Integer.toString(Singleton.getInstance().getInternListCount()));
                	Log.d("exchange",Integer.toString(Singleton.getInstance().getExchangeListCount()));
                	Log.d("gradjob",Integer.toString(Singleton.getInstance().getGradListCount()));
                    
                } 
                 
                finally{
                	Intent Main= new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(Main);
                    Log.d("Test","1");
                    Intent startService = new Intent(SplashScreen.this, OfferService.class);
                    startService(startService);
                	interrupt();
                    finish();
                    System.gc();
                    
                }
            }
	    
        };
        
			
         
        logoTimer.start();
			}
		}
    }
	
/*	public static boolean hasActiveInternetConnection(Context context) {
	    if (isNetworkAvailable(context)) {
	        try {
	            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
	            urlc.setRequestProperty("User-Agent", "Test");
	            urlc.setRequestProperty("Connection", "close");
	            urlc.setConnectTimeout(1500); 
	            urlc.connect();
	            return (urlc.getResponseCode() == 200);
	        } catch (IOException e) {
	            Log.e(LOG_TAG, "Error checking internet connection", e);
	        }
	    } else {
	        Log.d(LOG_TAG, "No network available!");
	    }
	    return false;
	}  */

	public AlertDialog buildDialog(Context c) {

	    AlertDialog.Builder builder1 = new AlertDialog.Builder(c);
	    AlertDialog builder=builder1.create();
	    builder
	    .setCancelable(false);
	    builder.setCanceledOnTouchOutside(false);
	   
	    builder.setTitle("No Internet connection.");
	    builder.setMessage("You have no internet connection");

	    builder.setButton(AlertDialog.BUTTON_NEUTRAL,"Ok", new DialogInterface.OnClickListener() {

	        @Override
	        public void onClick(DialogInterface dialog, int which) {

	            dialog.dismiss();
	    		SplashScreen.this.onDestroy();
	        }
	    });

	    return builder;
	}
	
	public void InternJobQuery(){
		
		 
		
		 ParseQuery<Jobs> query = ParseQuery.getQuery("InternJobs");
		 
		 query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);

         query.include("Company_Symbol");
         Calendar c = Calendar.getInstance(); 
  //       query.whereGreaterThanOrEqualTo("Deadline",c.getTime());
  //       query.whereEqualTo("Description", "Elite");
         query.findInBackground(new FindCallback<Jobs>() {
           @Override
           public void done(List<Jobs> list, ParseException e) {
        	   
        	   
           	
    
               if (e != null){
       //            Toast.makeText(rootView.getContext(), "Error " + e, Toast.LENGTH_SHORT ).show();
               }
               
               else{
            	   
            	   Collections.sort(list,new Comparator<Jobs>() {
            		    @Override
            		    public int compare(Jobs r1, Jobs r2) {
            		    	
            		        if(r1.getDeadline().before(r2.getDeadline())){
            		    	return -1;
            		        }
            		    	
            		    		else{return 0;}
            		    		
            		    	
            		    
            		    }
            		});
               
               for (final Jobs tempjob : list){
               	final Jobs newJob = new Jobs();
               	//https://www.parse.com/docs/android/guide#objects-relational-data
              
					newJob.duplicateFrom(tempjob);
//					((MainApplication)SplashScreen.this.getApplication()).addIntern(tempjob);
					Singleton.getInstance().addIntern(tempjob);
                   
                   
               }

       		Log.d("RETURN", "InternJobs, size of array"+Integer.toString(Singleton.getInstance().getInternListCount()));
       		if(Singleton.getInstance().getInternListCount()>=0)
       		InternQueryDone = true;
       		else
       			InternJobQuery();
           }
               
           }

       });
	}
	
	 public void GradJobQuery(){
		
		 
		
		 ParseQuery<GradJobs> query = ParseQuery.getQuery("GradJobs");
		 
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.include("Company_Symbol");
        Calendar c = Calendar.getInstance(); 
//        query.whereGreaterThanOrEqualTo("Deadline",c.getTime());
        query.findInBackground(new FindCallback<GradJobs>() {
          @Override
          public void done(List<GradJobs> list, ParseException e) {
          	
   
              if (e != null){
      //            Toast.makeText(rootView.getContext(), "Error " + e, Toast.LENGTH_SHORT ).show();
              }
              else{
            	  
            	  
            	  Collections.sort(list,new Comparator<GradJobs>() {
          		    @Override
          		    public int compare(GradJobs r1, GradJobs r2) {
          		    	
          		        if(r1.getDeadline().before(r2.getDeadline())){
          		    	return -1;
          		        }
          		    	
          		    		else{return 0;}
          		    		
          		    	
          		    }
          		    
          		});
            	  
              for (final GradJobs tempjob : list){
              	final GradJobs newJob = new GradJobs();
              	//https://www.parse.com/docs/android/guide#objects-relational-data
             
					newJob.duplicateFrom(tempjob);
//					((MainApplication)SplashScreen.this.getApplication()).addGrad(tempjob);
					Singleton.getInstance().addGrad(tempjob);
                  
                  
              }

      		Log.d("RETURN", "GradJobs, size of array"+Integer.toString(Singleton.getInstance().getGradListCount()));
      		if(Singleton.getInstance().getGradListCount()>=0)
      		GradJobQueryDone = true;
      		else
      			GradJobQuery();
          }
        
         
          }
          
      });
        
        
        
        
	}
	 
	 
	
	
	public void ExchangeQuery(){
		
		 
		
		 ParseQuery<Exchange> query = ParseQuery.getQuery("Exchange");
		 
	     query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
         query.include("Company_Symbol");
         query.findInBackground(new FindCallback<Exchange>() {
         @Override
         public void done(List<Exchange> list, ParseException e) {
         	
  
             if (e != null){
     //            Toast.makeText(rootView.getContext(), "Error " + e, Toast.LENGTH_SHORT ).show();
             }
             else{
           	  
           	  
           	  Collections.sort(list,new Comparator<Exchange>() {
         		    @Override
         		    public int compare(Exchange r1, Exchange r2) {
         		    	
         		        if(r1.getDeadline().before(r2.getDeadline())){
         		    	return -1;
         		        }
         		    	
         		    		else{return 0;}
         		    		
         		    	
         		    }
         		    
         		});
           	  
             for (final Exchange tempjob : list){
             	final Exchange newJob = new Exchange();
             	//https://www.parse.com/docs/android/guide#objects-relational-data
            
					newJob.duplicateFrom(tempjob);
//					((MainApplication)SplashScreen.this.getApplication()).addExchange(tempjob);
					Singleton.getInstance().addExchange(tempjob);
                 
             }

     		Log.d("RETURN", "Exchanges, size of array"+Integer.toString(Singleton.getInstance().getExchangeListCount()));
     		if(Singleton.getInstance().getExchangeListCount()>=0)
     		ExchangeQueryDone = true;
     		else
     			ExchangeQuery();
         }
       
        
         }
         
     });
       
       
       
       
	}
	
	
	
	public void CompetitionQuery(){
		
		 
		
		ParseQuery<Competition> query = ParseQuery.getQuery("Competition");
		 
	    query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.include("Company_Symbol");
        query.findInBackground(new FindCallback<Competition>() {
        @Override
        public void done(List<Competition> list, ParseException e) {
        	
 
            if (e != null){
    //            Toast.makeText(rootView.getContext(), "Error " + e, Toast.LENGTH_SHORT ).show();
            }
            else{
          	  
          	  
          	  Collections.sort(list,new Comparator<Competition>() {
        		    @Override
        		    public int compare(Competition r1, Competition r2) {
        		    	
        		        if(r1.getDeadline().before(r2.getDeadline())){
        		    	return -1;
        		        }
        		    	
        		    		else{return 0;}
        		    		
        		    	
        		    }
        		    
        		});
          	  
            for (final Competition tempjob : list){
            	final Competition newJob = new Competition();
            	//https://www.parse.com/docs/android/guide#objects-relational-data
           
					newJob.duplicateFrom(tempjob);
					((MainApplication)SplashScreen.this.getApplication()).addCompetition(tempjob);
					Singleton.getInstance().addCompetition(tempjob);
          
            }

    		Log.d("RETURN", "Comp, size of array"+Integer.toString(Singleton.getInstance().getCompListCount()));
    		if(Singleton.getInstance().getCompListCount()>=0)
    		CompetitionQueryDone = true;
    		else
    			CompetitionQuery();
        }
      
       
        }
        
    });
      
      
      
      
	}
	
	
	private boolean isNetworkConnected() {
		  ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo ni = cm.getActiveNetworkInfo();
		  if (ni == null) {
		   // There are no active networks.
		   return false;
		  } else
		   return true;
		 }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	   // ignore orientation change
	   if (newConfig.orientation != Configuration.ORIENTATION_LANDSCAPE) {
	       super.onConfigurationChanged(newConfig);
	   }
	}
	
	public boolean LoadSP(){
		 SharedPreferences settings = getSharedPreferences(getString(R.string.app_name), 0);
		 //if the settings exists, tv1 shows the chosen uni, press again to delete the shared preference 
		 if ( !settings.contains("university") )
			 return false;
		 if ( !settings.contains("major") )
			 return false;
/*		 if ( !settings.contains("gpa") )
			 return false; */
		 if ( !settings.contains("industry1") )
			return false;
		 if ( !settings.contains("industry2") )
			return false;
		 if ( !settings.contains("career_style") )
			return false;
		 profile.setUniversity(settings.getString("university", "/"));
		 profile.setMajor(settings.getString("major", "/"));
//		 profile.setGPA(settings.getInt("gpa", Profile.UNDEFINED));
		 profile.setIndustries(settings.getString("industry1", "/"), settings.getString("industry2", "/"));
		 profile.setCareerStyle(settings.getInt("career_style", Profile.UNDEFINED));
		 
		 
		return true;
	}
	
	public void startBuildProfile(){
		//Toast.makeText(getBaseContext(), "No saved uni", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, ChooseUniActivity.class);
		startActivity(intent);
	}
	
	
}
