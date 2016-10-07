/*package com.wesley.offers_v2.offers;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.wesley.offers_v2.Constants;
import com.wesley.offers_v2.Jobs;
import com.wesley.offers_v2.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InternDetailLayout extends RelativeLayout {
	Jobs jobs;
	protected TextView vCompany;
	protected TextView vJobTitle;
	protected TextView vEmail;
	protected TextView vTitle;
	protected TextView description;
	protected TextView deadline;
	protected ImageView CompanyLogo;

	public InternDetailLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public InternDetailLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public InternDetailLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public void init(){
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View rootView = inflater.inflate(R.layout.offer_detial, this, false);
		this.vCompany = (TextView) rootView.findViewById(R.id.offer_card_context);
		this.vJobTitle= (TextView) rootView.findViewById(R.id.offer_card_title);
		//this.vEmail = findViewById(R.id.offer_card_email);
		this.CompanyLogo = (ImageView) rootView.findViewById(R.id.offer_card_icon);
		this.description = (TextView) rootView.findViewById(R.id.offer_detial_job_description);
		this.deadline = (TextView) rootView.findViewById(R.id.offer_detial_deadline);
		
		if (!this.getContext().getString("JobObjectId").equals("")){
			String job_id = this.getArguments().getString("JobObjectID");
			Log.v("ida", job_id);
			try {
				getJob(job_id);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return rootView;
	}

	private Jobs getJob(String job_id) throws ParseException {
		
		//final List<Jobs> tempresult=new ArrayList<Jobs>();
        final Jobs newJob = new Jobs();
        ParseQuery<Jobs> query = new ParseQuery<Jobs>("Jobs");
        //query.get(job_id);
        query.getInBackground(job_id, new GetCallback<Jobs>(){

			@Override
			public void done(Jobs arg0, ParseException arg1) {
				// TODO Auto-generated method stub
				if (arg1==null){
	            	arg0.getParseObject("Company_Symbol").fetchIfNeededInBackground(new GetCallback<ParseObject>(){
						@Override
						public void done(ParseObject arg0, ParseException e) {
							// TODO Auto-generated method stub
							if (e==null){
								newJob.SetLogo(arg0.getParseFile("Company_Symbol"));
								setBitMap(newJob);
							}							
						}
	            	});
		            newJob.duplicateFrom(arg0);
					setTexts(newJob);
				}
			}
        });
        return newJob;
    }
	
	public void setTexts(Jobs jobs){
		vCompany.setText(jobs.getCompany());
		vJobTitle.setText(jobs.getJobTitle());
		description.setText(jobs.getCompany()+" "+jobs.getJobTitle());
		if (jobs.getDeadline()!=null)
			deadline.setText(jobs.getDeadline().toString());
	}
	
	public void setBitMap(Jobs ci){
		try {
			byte[] data = ci.getLogo().getData();
			Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
			CompanyLogo.setImageBitmap(bmp);
		} catch (ParseException e) {
			Log.d("ca", "fail build image");
		} catch (NullPointerException e) {
			Log.d("ca", "null point");
		}
	}
}

*/

