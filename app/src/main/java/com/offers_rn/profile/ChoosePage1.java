package com.offers_rn.profile;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.offers_rn.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ChoosePage1 extends Activity implements OnClickListener{
	EditText et1;
	Button but1;
	String username="";
	ParseUser user;
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
		setContentView(R.layout.profile_choose_page1);
		et1 = (EditText) findViewById(R.id.profile_user_name);
		but1 = (Button) findViewById(R.id.profile_choose_page1_confirm_btn);
		but1.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		username = et1.getText().toString();
		if(username != null){
			
			if(ParseUser.getCurrentUser()==null){
				user = new ParseUser();
				user.setUsername(username);
				user.setPassword("565656");
				user.signUpInBackground(new SignUpCallback() {
					public void done(ParseException e) {
						if (e == null) {
				      // Hooray! Let them use the app now.
				    	Log.d("success","");
				    	Toast.makeText(ChoosePage1.this.getApplicationContext(), "Your username is "+username, Toast.LENGTH_LONG).show();
						SharedPreferences settings = getSharedPreferences(ChoosePage1.this.getApplicationContext().getString(R.string.app_name), 0);
					    SharedPreferences.Editor PE = settings.edit();
					    PE.putString("username", username);
					    PE.commit();
				    	gotoNext();
				    } else {
				    	Log.d(e.getMessage(),"fail");
				    	Toast.makeText(ChoosePage1.this.getApplicationContext(), "Username used by others:(", Toast.LENGTH_LONG).show();
				      // Sign up didn't succeed. Look at the ParseException
				      // to figure out what went wrong
				    }
				  }
				});
			}
			else{
				user = ParseUser.getCurrentUser();
				user.setUsername(username);
				user.saveInBackground(new SaveCallback(){

					@Override
					public void done(ParseException arg0) {
						// TODO Auto-generated method stub
						if(arg0==null){
							Toast.makeText(ChoosePage1.this.getApplicationContext(), "Your username is "+username, Toast.LENGTH_LONG).show();
							SharedPreferences settings = getSharedPreferences(ChoosePage1.this.getApplicationContext().getString(R.string.app_name), 0);
						    SharedPreferences.Editor PE = settings.edit();
						    PE.putString("username", username);
						    PE.commit();
					    	gotoNext();
						}
						else{
							Toast.makeText(ChoosePage1.this.getApplicationContext(), "Username used by others:(", Toast.LENGTH_LONG).show();
						}
					}
					
					
				});
			}
				
			
			}else{
				Toast.makeText(this.getApplicationContext(), "Please input username", Toast.LENGTH_LONG).show();
			}
		
	}
	
	public void gotoNext(){
			Intent intent = new Intent(this, ChoosePage3.class);
			startActivity(intent);
	}
	
	@Override
	public void onBackPressed() {
		
	}

}
