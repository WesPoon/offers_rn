package com.offers_rn.offers;


import java.util.ArrayList;
import java.util.List;

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


public class ExchangeFragment extends Fragment{
	
	
	View rootView;
	RecyclerView recList;
	static ActionBar actionBar;
	public List<Jobs> exchange = new ArrayList<Jobs>();
	
	public static ExchangeFragment newInstance(ActionBar MainActionBar) {
		actionBar = MainActionBar;
		ExchangeFragment fragment = new ExchangeFragment();
		Bundle args = new Bundle();
		
		return fragment;
	}

	@SuppressWarnings("unchecked")
	public ExchangeFragment() {
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		for (Jobs job :(ArrayList<Jobs>)(Object)((MainApplication)this.getActivity().getApplication()).getExchange())
		    this.exchange.add(job);
		
		
//		setRetainInstance(true);
//		Toast.makeText(getActivity(),"GradFragment onCreate",Toast.LENGTH_SHORT).show();
		
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.offer_recyclerview, container, false);	
		createList();
		
		return rootView;
		
		
	}
	
	@SuppressWarnings("deprecation")
	private void createList() {
		
		
	  	
		recList=(RecyclerView) rootView.findViewById(R.id.cardList);
		
		recList.setHasFixedSize(true);
		LinearLayoutManager llm=new LinearLayoutManager(getActivity());
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		recList.setLayoutManager(llm);
                
        ContactAdapter ca = new ContactAdapter("Exchange",exchange);
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
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();

	}
	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	
	
	
}
