package com.offers_rn.menulist;

import com.offers_rn.MainApplication;
import com.offers_rn.R;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class StarredList extends AppCompatActivity {

	private ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.savelist);
		actionBar = getSupportActionBar();
		actionBar.setLogo(R.drawable.rsz_star_message);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setTitle("Starred Messages");
		RecyclerView rv = (RecyclerView)findViewById(R.id.savelist_recycleview);
		rv.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		rv.setLayoutManager(llm);
	    
		StarredListAdapter starred = new StarredListAdapter(MainApplication.mydb.getAllStarMessages());
		rv.setAdapter(starred);
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

	
}
