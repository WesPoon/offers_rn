package com.offers_rn.offers;



import java.util.ArrayList;

import com.offers_rn.Constants;
import com.offers_rn.MainApplication;
import com.offers_rn.R;
import com.offers_rn.parseobject.Jobs;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class InternFragment extends Fragment{
	
	
	View rootView;
	RecyclerView recList;
	static ActionBar actionBar;
	
	public static InternFragment newInstance(ActionBar MainActionBar) {
		actionBar = MainActionBar;
		InternFragment fragment = new InternFragment();
		Bundle args = new Bundle();
		//args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
		args.putString(Constants.ARG_SECTION_TITLE, "Internship List");
		fragment.setArguments(args);
		return fragment;
	}

	public InternFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		setRetainInstance(true);
//		Toast.makeText(getActivity(),"InternFragment onCreateView",Toast.LENGTH_SHORT).show();
		
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		Log.v("INTERNFRAGMENT","OnCreateView_New");
		

		
		rootView = inflater.inflate(R.layout.offer_recyclerview, container, false);
		
		
		
		createList();
		
		
		
		return rootView;
		
		
		
	}
	
	private void createList() {
		
		recList=(RecyclerView) rootView.findViewById(R.id.cardList);
    	recList.setHasFixedSize(true);
		LinearLayoutManager llm=new LinearLayoutManager(getActivity());
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		recList.setLayoutManager(llm);
		
		ContactAdapter ca = new ContactAdapter("InternJobs",(ArrayList<Jobs>)(Object)((MainApplication)this.getActivity().getApplication()).getInternList());
		recList.setAdapter(ca);
        recList.setOnScrollListener(new OnScrollListener(){
        	
        	@Override
        	public void onScrollStateChanged(RecyclerView recyclerView, int newState){
        		if(newState==RecyclerView.SCROLL_STATE_IDLE){
        			int i=0;
        			while(i<=15000){i++;}
        			actionBar.show();
        		}
        		else{
        			actionBar.hide();
        			
        		}
        		
        	}
        	  
            
        });
		
	/*	 ParseQuery<Jobs> query = ParseQuery.getQuery("InternJobs");
		 
		 query.setCachePolicy(ParseQuery.CachePolicy.IGNORE_CACHE);
   //     ParseQuery<Jobs> query = new ParseQuery<Jobs>("Jobs");
        
  //      query.whereEqualTo("Company", "HSBC");   //first parameter key,second the name
        query.include("Company_Symbol");
        query.findInBackground(new FindCallback<Jobs>() {
            @Override
            public void done(List<Jobs> list, ParseException e) {
            	
             	final List<Jobs> tempresult = new ArrayList<Jobs>();
            	
            	final RecyclerView recList=(RecyclerView) rootView.findViewById(R.id.cardList);
            	recList.setHasFixedSize(true);
        		LinearLayoutManager llm=new LinearLayoutManager(getActivity());
        		llm.setOrientation(LinearLayoutManager.VERTICAL);
        		recList.setLayoutManager(llm);
        	  	ContactAdapter trial = new ContactAdapter("");
       		    recList.setAdapter(trial);
   
            	
                if (e != null){
                    Toast.makeText(rootView.getContext(), "Error " + e, Toast.LENGTH_SHORT ).show();
                }
                for (final Jobs tempjob : list){
                	final Jobs newJob = new Jobs();
                	//https://www.parse.com/docs/android/guide#objects-relational-data
               
					newJob.duplicateFrom(tempjob);
                    tempresult.add(newJob);
                    
                    
                } 
                
        		
            }
            
        });  */
       
    }

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("INTERNFRAGMENT","RESUME");
		Toast.makeText(getActivity(),"InternFragment onResume",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v("INTERNFRAGMENT","DESTROY");
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.v("INTERNFRAGMENT","DESTROYVIEW");
		System.gc();
	}

	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.v("INTERNFRAGMENT","config");
		onResume();
	}
	

	
	
}
