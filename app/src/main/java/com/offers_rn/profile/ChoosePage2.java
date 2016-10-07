package com.offers_rn.profile;

import com.offers_rn.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

import android.widget.Spinner;
import android.widget.Toast;

public class ChoosePage2 extends Activity implements OnClickListener, OnItemSelectedListener{
	Spinner spn1, spn2;
	Button but1;
	String major="";
	int gpa = Profile.UNDEFINED;
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
		setContentView(R.layout.profile_choose_page2);
		spn1 = (Spinner) findViewById(R.id.profile_choose_major_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.major_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn1.setAdapter(adapter);
		spn1.setOnItemSelectedListener(this);
/*		spn2 = (Spinner) findViewById(R.id.profile_choose_gpa_spinner);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.gpa_array, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn2.setAdapter(adapter2);
		spn2.setOnItemSelectedListener(this); */
		but1 = (Button) findViewById(R.id.profile_choose_page2_confirm_btn);
		but1.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		//major = et1.getText().toString();
		major = spn1.getSelectedItem().toString();
//		GetSetGPA(spn2.getSelectedItem().toString());
//		Toast.makeText(this.getApplicationContext(), "Your major is "+major+", GPA is "+gpa, Toast.LENGTH_LONG).show();
		SharedPreferences settings = getSharedPreferences(this.getApplicationContext().getString(R.string.app_name), 0);
	    SharedPreferences.Editor PE = settings.edit();
	    PE.putString("major", major);
//	    PE.putInt("gpa", gpa);
	    PE.commit();
		gotoNext();
	}
	
	private void GetSetGPA(String in) {
		if(in.equals(getResources().getString(R.string.profile_gpa_classified)))
			gpa = Profile.CLASSIFIED;
		if(in.equals(getResources().getString(R.string.profile_gpa_above_3_5)))
			gpa = Profile.ABOVE_3_5;
		if(in.equals(getResources().getString(R.string.profile_gpa_above_3)))
			gpa = Profile.ABOVE_3;
		if(in.equals(getResources().getString(R.string.profile_gpa_below_3)))
			gpa = Profile.BELOW_3;
	}
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		//gpa = Profile.UNDEFINED;
	}
	
	
	
	@Override
	public void onBackPressed() {
		
	}

	public void gotoNext(){
			Intent intent = new Intent(this, ChoosePage3.class);
			startActivity(intent);
	}
}
