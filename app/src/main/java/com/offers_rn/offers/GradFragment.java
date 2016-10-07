package com.offers_rn.offers;


import java.util.ArrayList;

import com.offers_rn.Constants;
import com.offers_rn.MainActivity;
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


public class GradFragment extends Fragment{
	
	
	View rootView;
	RecyclerView recList;
	public static ActionBar actionBar;
	
	public static GradFragment newInstance(ActionBar MainActionBar) {
		actionBar = MainActionBar;
		GradFragment fragment = new GradFragment();
		Bundle args = new Bundle();
		//args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
		args.putString(Constants.ARG_SECTION_TITLE, "Internship List");
		fragment.setArguments(args);
		return fragment;
	}

	public GradFragment() {
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		setRetainInstance(true);
//		Toast.makeText(getActivity(),"GradFragment onCreate",Toast.LENGTH_SHORT).show();
		
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.offer_recyclerview, container, false);
		createList();
//		Toast.makeText(getActivity(),"GradFragment onCreateView",Toast.LENGTH_SHORT).show();
		
		return rootView;
		
		
	}
	
	@SuppressWarnings("deprecation")
	private void createList() {
		
		
	  	
		recList=(RecyclerView) rootView.findViewById(R.id.cardList);
		
		recList.setHasFixedSize(true);
		LinearLayoutManager llm=new LinearLayoutManager(getActivity());
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		recList.setLayoutManager(llm);
                
        ContactAdapter ca = new ContactAdapter("GradJobs",(ArrayList<Jobs>)(Object)((MainApplication)this.getActivity().getApplication()).getGradList());
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
        		
        
        
       
    }

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		Toast.makeText(getActivity(),"GradFragment onResume",Toast.LENGTH_SHORT).show();
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
	}
	
	
	
	
}
