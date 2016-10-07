package com.offers_rn.detail;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import com.offers_rn.Constants;
import com.offers_rn.MainApplication;
import com.offers_rn.R;

/**
 * Created by rufflez on 6/21/15.
 */
public class JDFragment extends Fragment {
	
	List<String> JD = new ArrayList<String>();
	List<String> req = new ArrayList<String>();
    String Company, Deadline, Job_Title;
	
	
	public static JDFragment newInstance(ArrayList<String> JD, String company, String job_title, String deadline) {
		Log.v("JD number",Integer.toString(JD.size()));
		JDFragment fragment = new JDFragment();
		Bundle args = new Bundle();
		args.putStringArrayList("list", JD);
		args.putString("Company", company);
		args.putString("Job Title",job_title);
	    args.putString("Deadline",deadline);
		fragment.setArguments(args);
		return fragment;
	}

	public JDFragment(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setRetainInstance(true);
	}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        
    	Bundle arg0 = getArguments();
    	this.JD = arg0.getStringArrayList("list");
    	this.Company = arg0.getString("Company");
    	this.Deadline = arg0.getString("Deadline");
    	this.Job_Title = arg0.getString("Job Title");
    	View rootView = inflater.inflate(R.layout.detail_job_description, container, false);
        TextView jd_textview = (TextView)rootView.findViewById(R.id.job_description);
        if(JD.size()>0){
           jd_textview.setText(JD.get(0));
        }
        
        FloatingActionButton button = (FloatingActionButton)rootView.findViewById(R.id.fab2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            	Intent whatsapp = new Intent(Intent.ACTION_SEND);
            	whatsapp.setType("text/plain");
            	whatsapp.setPackage("com.whatsapp");
            	
            	String message;
            	message = "------ Info provided by OFFERS ------\n";
            	message = message + "👉🏻👉🏻 " + Company+"\n";
            	message = message + "👉🏻👉🏻 " + Job_Title+"\n";
            	message = message + "👉🏻👉🏻 " + "Deadline : " + Deadline + "\n";
            	message = message+JD.get(0);
            	whatsapp.putExtra(Intent.EXTRA_TEXT, message);
            	try {
            	    startActivity(whatsapp);
            	} catch (android.content.ActivityNotFoundException ex) {
            	    Toast.makeText(getActivity(), "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
            	}
            	
            }
        });
 /*   
        
/*       do{
    	   try{
    		   jd_textview.setText(MainApplication.getTempJD());
    	   }
    	   finally{
    		   
    	   }
       }while(MainApplication.getTempJD()==null); */
        
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
    
    @Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

}