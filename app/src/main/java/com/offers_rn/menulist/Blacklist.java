package com.offers_rn.menulist;

import com.offers_rn.MainApplication;
import com.offers_rn.R;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.FloatingActionButton;

public class Blacklist extends AppCompatActivity {
	
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		String list_type = bundle.getString("list_type");
		setContentView(R.layout.savelist);
		
		actionBar = getSupportActionBar();
		actionBar.setLogo(R.drawable.ic_launcher);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		
		if(list_type.equals("offers")){
			actionBar.setTitle("Your Save List");
		}
		else if(list_type.equals("offers_blacklist")){
			actionBar.setTitle("Your Black List");
		}
		
//		actionBar.hide();
		
		RecyclerView rv = (RecyclerView)findViewById(R.id.savelist_recycleview);
		rv.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		rv.setLayoutManager(llm);
	    
		SaveAndBlackListAdapter adapter = new SaveAndBlackListAdapter(MainApplication.mydb.getAllOffers(list_type),list_type);
		rv.setAdapter(adapter);
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onTitleChanged(CharSequence title, int color) {
		// TODO Auto-generated method stub
		super.onTitleChanged(title, color);
	}
       
	
}
