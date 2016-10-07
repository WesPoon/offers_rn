package com.offers_rn.detail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.offers_rn.Constants;
import com.offers_rn.R;

/**
 * Created by rufflez on 6/21/15.
 */
public class PrepareFragment extends Fragment {
	
	
	public static PrepareFragment newInstance() {
		
		PrepareFragment fragment = new PrepareFragment();
		Bundle args = new Bundle();
		//args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
		args.putString(Constants.ARG_SECTION_TITLE, "Internship List");
		fragment.setArguments(args);
		return fragment;
	}
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
//		Toast.makeText(getActivity(),"GradFragment onCreate",Toast.LENGTH_SHORT).show();
	}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.floating_action_button, container, false);
 /*       FloatingActionButton button = (FloatingActionButton)rootView.findViewById(R.id.fab2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "This is a snackbar", Snackbar.LENGTH_SHORT).show();
            }
        }); */
        return rootView;
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
		System.gc();
    }

}