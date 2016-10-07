package com.offers_rn.detail;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.offers_rn.Constants;
import com.offers_rn.MainApplication;
import com.offers_rn.R;
import com.offers_rn.offers.ContactAdapter;
import com.offers_rn.parseobject.Jobs;
import com.shamanland.facebook.likebutton.FacebookLikeButton;

/**
 * Created by rufflez on 6/21/15.
 */
public class GossipFragment extends Fragment {
	
	RecyclerView recList;
	View rootView;
	ArrayList<Gossip> gossip_list = new ArrayList<Gossip>();
	
	public static GossipFragment newInstance(ArrayList<Gossip> gossip_list) {
		GossipFragment fragment = new GossipFragment();
		Bundle args = new Bundle();
		//args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
		args.putParcelableArrayList("Gossip List",  gossip_list);
		fragment.setArguments(args);
		Log.v("Gossip Fragment","Create GossipFragment2 - "+Integer.toString(gossip_list.size()));
		return fragment;
	}
	
	public GossipFragment(){
			
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setRetainInstance(true);
//		Toast.makeText(getActivity(),"GradFragment onCreate",Toast.LENGTH_SHORT).show();
	}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    	Bundle extras = getArguments();
  
    	this.gossip_list = extras.getParcelableArrayList("Gossip List");
        rootView = inflater.inflate(R.layout.offer_recyclerview, container, false);
        createGossipList();

        return rootView;
    }
    
    public void createGossipList(){
        
    	recList=(RecyclerView) rootView.findViewById(R.id.cardList);	
		recList.setHasFixedSize(true);
		LinearLayoutManager llm=new LinearLayoutManager(getActivity());
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		recList.setLayoutManager(llm);
		
        GossipAdapter ca = new GossipAdapter(gossip_list);
        recList.setAdapter(ca);
    	
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


}