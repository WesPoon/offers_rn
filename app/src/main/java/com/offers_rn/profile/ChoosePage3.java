package com.offers_rn.profile;


import com.offers_rn.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class ChoosePage3 extends Activity implements OnClickListener, OnItemSelectedListener{
	ChooseIndustrySpinner spn1, spn2;
	Spinner spn3,spn4;
	Button but1;
	String industry1, industry2;
	String major="";
	int career_style=Profile.UNDEFINED;
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
		setContentView(R.layout.profile_choose_page3);
		spn1 = (ChooseIndustrySpinner) findViewById(R.id.profile_choose_industries_spinner1);
		spn2 = (ChooseIndustrySpinner) findViewById(R.id.profile_choose_industries_spinner2);
		

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.industry_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn1.setAdapter(adapter);
		spn2.setAdapter(adapter);


		spn3 = (Spinner) findViewById(R.id.profile_choose_career_style_spinner);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.career_style_array, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn3.setAdapter(adapter2);
		spn3.setOnItemSelectedListener(this);


		spn4 = (Spinner) findViewById(R.id.profile_choose_major_spinner);

		ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.major_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spn4.setAdapter(adapter3);
		spn4.setOnItemSelectedListener(this);


		but1 = (Button) findViewById(R.id.profile_choose_page3_confirm_btn);
		but1.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	
		


		industry1 = spn1.getValue();
		industry2 = spn2.getValue();
		GetSetCareerStyle(spn3.getSelectedItem().toString());
		major = spn4.getSelectedItem().toString();
	    SharedPreferences settings = getSharedPreferences(this.getApplicationContext().getString(R.string.app_name), 0);
	    SharedPreferences.Editor PE = settings.edit();
	    PE.putString("industry1", industry1);
	    PE.putString("industry2", industry2);
	    PE.putString("major", major);
	    PE.remove("career_style");
	    PE.putInt("career_style",career_style);
	    PE.commit();
		Toast.makeText(this.getApplicationContext(), "Your industry1 is "+industry1+".\nYour industry2 is "+industry2+". \nYour Career Style is : "+career_style+".", Toast.LENGTH_LONG).show();
		
		gotoNext();
	}
	
	@Override
	public void onBackPressed() {
		
	}
	
	private void GetSetCareerStyle(String in) {
		if(in.equals(getResources().getString(R.string.profile_career_style_central_ppl)))
			career_style = Profile.CENTRAL_PPL;
		if(in.equals(getResources().getString(R.string.profile_career_style_stable_ppl)))
			career_style = Profile.STABLE_PPL;
		if(in.equals(getResources().getString(R.string.profile_career_style_creative)))
			career_style = Profile.CREATIVE;
		if(in.equals(getResources().getString(R.string.profile_career_style_technical)))
			career_style = Profile.TECHNICAL;
		if(in.equals(getResources().getString(R.string.profile_career_style_undefined)))
			career_style = 0;
			//career_style = Profile.BELOW_3;
		if(in.equals(getResources().getString(R.string.profile_career_style_management_trainee)))
			career_style = 0;
			//career_style = Profile.BELOW_3;
		if(in.equals(getResources().getString(R.string.profile_career_style_graduate_trainee)))
			career_style = 0;
			//career_style = Profile.BELOW_3;
	}
	
	public void gotoNext(){

		Intent intent = new Intent(this, ScollGallery.class);
		startActivity(intent);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	

}
///http://stackoverflow.com/questions/4495511/how-to-pass-custom-component-parameters-in-java-and-xml
class ChooseIndustrySpinner extends Spinner implements OnItemSelectedListener{
	int index;
	String value = "";
	public ChooseIndustrySpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(attrs);
	}
	
	public ChooseIndustrySpinner(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ChooseIndustrySpinner(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs);
	}
	
	public void init(AttributeSet attrs){
		TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.ChooseIndustrySpinner);
		Integer index_tmp = arr.getInt((R.styleable.ChooseIndustrySpinner_index),0);
		if (index_tmp != null) {
			setIndex(index_tmp);
		}
		arr.recycle();
		this.setOnItemSelectedListener(this);
	}
	
	public int getIndex(){
		return index; 
	}
	
	public void setIndex(int index){
		this.index = index;
	}
	
	public String getValue(){
		return value; 
	}
		
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		value = arg0.getItemAtPosition(arg2).toString();
		//Toast.makeText(this.getContext(), "im spinner"+index+". Your Industry"+index+" is "+value+".", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	

	
}