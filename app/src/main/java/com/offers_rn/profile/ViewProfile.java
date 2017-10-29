package com.offers_rn.profile;

import com.squareup.picasso.Picasso;
import com.offers_rn.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Spinner;
import android.widget.Toast;

public class ViewProfile extends Activity implements OnClickListener{
	ImageView uIcon, userIcon;
	TextView uni, username, major, gpa, industry1, industry2, careerStyle;
	Button btn1;
	String profilePicUrl;
	@SuppressLint("NewApi") @Override
	public void onCreate(Bundle savedInstanceState,
			PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
		init();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}
	
	public void init(){




		setContentView(R.layout.view_profile);


		Typeface tinderFont = Typeface.createFromAsset(getAssets(),"fonts/proxima-nova-soft-light-webfont.ttf");

		btn1 = (Button) findViewById(R.id.viewProfile_reset_btn);
		btn1.setOnClickListener(this);
		uIcon = (ImageView) findViewById(R.id.viewProfile_uIcon);
		userIcon = (ImageView) findViewById(R.id.viewProfile_userIcon);
		uni = (TextView) findViewById(R.id.viewProfile_university);
		username = (TextView) findViewById(R.id.viewProfile_userName);
		major = (TextView) findViewById(R.id.viewProfile_major);
		gpa = (TextView) findViewById(R.id.viewProfile_gpa);
		industry1 = (TextView) findViewById(R.id.viewProfile_industry1);
		industry2 = (TextView) findViewById(R.id.viewProfile_industry2);
		careerStyle = (TextView) findViewById(R.id.viewProfile_careerStyle);
		SharedPreferences settings = getSharedPreferences(this.getApplicationContext().getString(R.string.app_name), 0);

		this.profilePicUrl = settings.getString("profile_pic_url","/");

		uni.setText(settings.getString("university","/"));
		uni.setTypeface(tinderFont);
		setUIcon(settings.getString("university","/"));
		username.setText(settings.getString("username","/"));
		username.setTypeface(tinderFont);
		setUserIcon(settings.getString("username","/"));
		major.setText(settings.getString("major","/"));
		major.setTypeface(tinderFont);
		gpa.setText(convertGPA(settings.getInt("gpa",-1)));
		industry1.setText(settings.getString("industry1","/"));
		industry1.setTypeface(tinderFont);
		industry2.setText(settings.getString("industry2","/"));
		industry2.setTypeface(tinderFont);
		careerStyle.setText(convertCareerStyle(settings.getInt("career_style",-1)));
		careerStyle.setTypeface(tinderFont);
		
		//hiding gpa
		gpa.setVisibility(View.GONE);
	}
	
	public void setUIcon(String input){
		switch(input){
		case "CUHK":
			uIcon.setImageResource(R.drawable.cuhk);
			break;
		case "HKU":
			uIcon.setImageResource(R.drawable.hku);
			break;			
		case "PolyU":
			uIcon.setImageResource(R.drawable.polyu);
			break;			
		case "LU":
			uIcon.setImageResource(R.drawable.lu);
			break;			
		case "UST":
			uIcon.setImageResource(R.drawable.ust);
			break;			
		case "OpenU":
			uIcon.setImageResource(R.drawable.openu);
			break;			
		case "CityU":
			uIcon.setImageResource(R.drawable.cityu);
			break;			
		case "BU":
			uIcon.setImageResource(R.drawable.bu);
			break;			
		}
	}
	
	public void setUserIcon(String input){
		Context messageContext = null;
		Picasso.with(messageContext).
        load(profilePicUrl).
        placeholder(R.mipmap.ic_squeak).
        into(userIcon);
	}
	
	public String convertGPA (int input){
		switch(input){
		case 0:
			return "GPA<3";
		case 1:
			return "3<GPA<3.5";
		case 2:
			return "3.5<GPA";
		case 4:
			return "Classified";
		default:
			return "Error";					
		}		
	}
	
	public String convertCareerStyle (int input){
		switch(input){
		case 0:
			return "Hea Do";
		case 1:
			return "Technical";
		case 2:
			return "Creative";
		case 3:
			return "Stable people";
		case 4:
			return "Central people";
		default:
			return "Error";
		}
	}
	
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		gotoNext();
	}
	
	public void gotoNext(){
			Intent intent = new Intent(this, ChooseUniActivity.class);
			startActivity(intent);
	}

}
