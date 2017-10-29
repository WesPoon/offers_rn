package com.offers_rn;

import android.app.Application;
import android.util.Log;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import com.google.android.gms.ads.MobileAds;
import com.offers_rn.parseobject.GT;
import com.offers_rn.parseobject.MT;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.offers_rn.chatroom.Message;
import com.offers_rn.db.DBHelper;
import com.offers_rn.detail.Gossip;
import com.offers_rn.parseobject.Competition;
import com.offers_rn.parseobject.Exchange;
import com.offers_rn.parseobject.Govern;
import com.offers_rn.parseobject.GradJobs;
import com.offers_rn.parseobject.Jobs;
import com.offers_rn.parseobject.Mentorship;
import com.offers_rn.parseobject.RubbishJobs;
import com.offers_rn.parseobject.Scholar;
import com.offers_rn.parseobject.Workshop;
import com.offers_rn.parseobject.SummerTrip;

public class MainApplication extends Application implements ReactApplication {

  private static String appsid, clientid;

  private List<Jobs> InternJobslist = new ArrayList<Jobs>();
  private List<GradJobs> GradJobslist = new ArrayList<GradJobs>();
  private List<Exchange> Exchangelist = new ArrayList<Exchange>();
  private List<Competition> Comp_list = new ArrayList<Competition>();
  private List<Mentorship> Mentor_list = new ArrayList<Mentorship>();
  private List<Scholar> Scholar_list = new ArrayList<Scholar>();
  private List<Workshop> Workshop_list = new ArrayList<Workshop>();
  private List<Govern> Govern_list = new ArrayList<Govern>();
  private List<RubbishJobs> normal_list = new ArrayList<RubbishJobs>();


  static public DBHelper mydb;

  public static String tempjd;
//hi
  private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
    @Override
    protected boolean getUseDeveloperSupport() {
      return BuildConfig.DEBUG;
    }

    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage()
      );
    }
  };

  @Override
  public void onCreate(){

    super.onCreate();

    MobileAds.initialize(getApplicationContext(), "ca-app-pub-2233864871659362~1708118710");
    mydb=new DBHelper(this);
    //    blacklistdb=new DBHelper(this);

    appsid=getString(R.string.parse_app_id);
    clientid=getString(R.string.parse_client_id);


/*		Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
        .applicationId(appsid)
        .build()); */

    ParseObject.registerSubclass(Jobs.class);
    ParseObject.registerSubclass(GradJobs.class);
    ParseObject.registerSubclass(Exchange.class);
    ParseObject.registerSubclass(Competition.class);
    ParseObject.registerSubclass(Gossip.class);
    ParseObject.registerSubclass(Mentorship.class);
    ParseObject.registerSubclass(Workshop.class);
    ParseObject.registerSubclass(SummerTrip.class);
    ParseObject.registerSubclass(RubbishJobs.class);
    ParseObject.registerSubclass(MT.class);
    ParseObject.registerSubclass(GT.class);

    ParseObject.registerSubclass(Message.class);
    //Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_id));
    //Parse.initialize(this, appsid, clientid);
    Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId(appsid)
            .clientKey("")
            .server("http://54.65.1.149:1337/parse/")   // '/' important after 'parse'
            .build());

    if(ParseInstallation.getCurrentInstallation()==null){
      ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    ParsePush.subscribeInBackground("offers");
    ParseUser.enableRevocableSessionInBackground();

    ParseUser currentUser = ParseUser.getCurrentUser();
    if (currentUser != null) {
      // do stuff with the user

    } else {
      // show the signup or login screen
    }
    // PushService.setDefaultPushCallback(getApplicationContext(), MainActivity.class);  //always listening push from push, Activity created when u click the notification

    Log.d("Parse","notification set");

  }

  @Override
  public ReactNativeHost getReactNativeHost() {
      return mReactNativeHost;
  }

  public static String getTempJD(){

    return tempjd;
  }

  public int getInternListCount(){

    return this.InternJobslist.size();
  }

  public int getExchangeListCount(){
    return this.Exchangelist.size();
  }

  public int getGradListCount(){
    return this.GradJobslist.size();
  }

  public int getCompListCount(){

    return this.Comp_list.size();
  }

  public void addIntern(Jobs intern){
    InternJobslist.add(intern);
  }

  public void addGrad(Jobs grad){
    GradJobslist.add((GradJobs) grad);
  }

  public void addExchange(Jobs exchange){
    Exchangelist.add((Exchange)exchange);
  }

  public void addCompetition(Jobs comp){

    Comp_list.add((Competition)comp);
  }

  public List<Jobs> getInternList(){

    return InternJobslist;
  }

  public List<GradJobs> getGradList(){

    return GradJobslist;
  }

  public List<Exchange> getExchange(){

    return Exchangelist;
  }

  public List<Jobs> getList(String type){

    switch(type){

      case "GradsJobs":
        return (List<Jobs>)(Object)GradJobslist;

      case "Competition":
        return (List<Jobs>)(Object)Comp_list;

      case "Workshop":
        return (List<Jobs>)(Object)Govern_list;

      case "Govern":
        return (List<Jobs>)(Object)Workshop_list;

      case "Scholar":
        return (List<Jobs>)(Object)Scholar_list;

      case "Mentorship":
        return (List<Jobs>)(Object)Mentor_list;

      case "Normal":
        return (List<Jobs>)(Object)normal_list;

      default :
        return null;

    }
  }
}
