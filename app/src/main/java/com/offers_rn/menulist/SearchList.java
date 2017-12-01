package com.offers_rn.menulist;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.offers_rn.MainApplication;
import com.offers_rn.R;
import com.offers_rn.menulist.SaveAndBlackListAdapter;
import com.offers_rn.parseobject.Jobs;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchList extends AppCompatActivity {

	private ActionBar actionBar;
	private List<Jobs> SearchReturnlist = new ArrayList<Jobs>();
	private RecyclerView rv;
	private SearchListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		String search_key = bundle.getString("search_key");
		setContentView(R.layout.savelist);
		
		actionBar = getSupportActionBar();

		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);

		searchQuery(search_key, "Competition");
		searchQuery(search_key, "InternJobs");
		searchQuery(search_key, "Exchange");
		searchQuery(search_key, "MT");
		searchQuery(search_key, "GT");

		rv = (RecyclerView)findViewById(R.id.savelist_recycleview);
		rv.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		rv.setLayoutManager(llm);
	    
		adapter = new SearchListAdapter(SearchReturnlist);
		adapter.notifyDataSetChanged();
		/*adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {

			@Override
			public void onChanged() {
				super.onChanged();
				Log.d("TEST", "ON CHANGE");
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onItemRangeInserted(int positionStart, int itemCount) {
				super.onItemRangeInserted(positionStart, itemCount);
				Log.d("TEST", "ON ITEMRANGE");
				adapter.notifyDataSetChanged();
				adapter.notifyItemInserted(positionStart);
			}

		}); */
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

	protected void searchQuery(String search_key, final String class_name){


		ParseQuery<Jobs> query_try = ParseQuery.getQuery(class_name);
		String pattern = "^.*" + search_key + ".*$";
		query_try.whereMatches("Company", pattern, "i");

		ParseQuery<Jobs> query_1 = ParseQuery.getQuery(class_name);
		String pattern_1 = "^.*" + search_key + ".*$";
		query_try.whereMatches("Description", pattern_1, "i");

		ParseQuery<Jobs> query_2 = ParseQuery.getQuery(class_name);
		String pattern_2 = "^.*" + search_key + ".*$";
		query_try.whereMatches("Job_Title", pattern_2, "i");

		List<ParseQuery<Jobs>> queries = new ArrayList<ParseQuery<Jobs>>();

		queries.add(query_try);
		queries.add(query_1);
		queries.add(query_2);

		ParseQuery<Jobs> query = ParseQuery.or(queries);

		query.findInBackground(new FindCallback<Jobs>() {
			public void done(List<Jobs> search_return_list, ParseException e) {
				if (e == null) {
					if (search_return_list.size()>0) {
						for (final Jobs temp : search_return_list) {
							final Jobs newJob = new Jobs();
							//https://www.parse.com/docs/android/guide#objects-relational-data
							newJob.duplicateFrom(temp);
							newJob.setType(class_name);
							SearchReturnlist.add(newJob);
							Log.d("testquery",newJob.getApplicationLink());
						}

						adapter.notifyDataSetChanged();
					}
				} else {
					Log.d("score", "Error: " + e.getMessage());
					Toast.makeText(SearchList.this,  e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});

	}
       
	
}
