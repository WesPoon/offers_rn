package com.offers_rn.nav;


import java.util.ArrayList;
import java.util.List;

import com.offers_rn.Constants;
import com.offers_rn.MainApplication;
import com.offers_rn.R;
import com.offers_rn.Singleton;
import com.offers_rn.offers.ContactAdapter;
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
import android.widget.ProgressBar;
import android.widget.Toast;


public class CommonFragment extends Fragment{
	
	
	View rootView;
	RecyclerView recList;
	static ActionBar actionBar;
    public String type;
	public List<Jobs> list = new ArrayList<Jobs>();
	public ProgressBar mLoadingSub;
	
	public static CommonFragment newInstance(ActionBar MainActionBar , String type) {
		actionBar = MainActionBar;
		CommonFragment fragment = new CommonFragment();
		Bundle args = new Bundle();
		args.putString("type", type);
		
		fragment.setArguments(args);
		return fragment;
	}
	
	public CommonFragment() {
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		
	
		rootView = inflater.inflate(R.layout.offer_recyclerview, container, false);
	//	mLoadingSub = (ProgressBar) rootView.findViewById(R.id.loadingSpinner);
	//	mLoadingSub.setVisibility(View.VISIBLE);


			createList();

//	}	mLoadingSub.setVisibility(View.GONE);
		return rootView;
		
		
	}
	
	@SuppressWarnings("deprecation")
	private void createList() {
		
		Bundle arg = getArguments();
		this.type = arg.getString("type");
		
		recList=(RecyclerView) rootView.findViewById(R.id.cardList);
		recList.setHasFixedSize(true);
		LinearLayoutManager llm=new LinearLayoutManager(getActivity());
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		recList.setLayoutManager(llm);      
    //    ContactAdapter ca = new ContactAdapter(this.type,((MainApplication) this.getActivity().getApplication()).getList(this.type));
//		if (Singleton.getInstance().getList(this.type).size() == 0){
		ContactAdapter ca = new ContactAdapter(this.type,Singleton.getInstance().getList(this.type));
		recList.setAdapter(ca);
//		}

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
