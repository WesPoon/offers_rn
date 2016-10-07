package com.offers_rn.profile;

import com.offers_rn.R;
import com.offers_rn.uielement.NamedButton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;


public class ChooseUniActivity extends AppCompatActivity {

	ChooseUniButton cuhk;
	ChooseUniButton hku;
	ChooseUniButton polyu;
	ChooseUniButton lu;
	ChooseUniButton ust;
	ChooseUniButton openu;
	ChooseUniButton cityu;
	ChooseUniButton bu;


	@SuppressLint("NewApi") @Override
	public void onCreate(Bundle savedInstanceState,
			PersistableBundle persistentState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState, persistentState);
		init();//call init() to initiate
	}
	//mhk: added this on create
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		final ActionBar actionBar = getSupportActionBar();
		
		actionBar.setTitle("What is your School?");
		init();//call init() to initiate
	}
	
	@Override
	public void onBackPressed(){
	
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	public void init(){
		setContentView(R.layout.profile_choose_uni_activity);
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
	
	public void gotoNext(){
		Intent intent = new Intent(this, ChoosePage1.class);
		//Intent intent = new Intent(this, ChooseMajorActivity.class);
		startActivity(intent);
	}
}
