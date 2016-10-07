package com.offers_rn.parseobject;

import java.util.Date;

import android.util.Log;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.offers_rn.MainApplication;

//@ParseClassName("InternJobs")
@ParseClassName("InternJobs")
//@ParseClassNameRepeated("GradJobs")

public class Jobs extends ParseObject implements Comparable{

	private String company;
	private String JT;
    private String type;

	// @MHK: getString(String KEY) key in the parse backend, please remember to
	// @ParseClassName("Jobs")
	
	public Jobs duplicateFrom(Jobs demo){
		this.SetCompany(demo.getCompany());
		this.SetJobTitle(demo.getJobTitle());
		this.SetLogoPointer(demo.getLogoPointer());
		this.setDeadline(demo.getDeadline());
		this.setObjectId(demo.getObjectId());
		this.setDeadline(demo.getDeadline());
		this.setViewCount(demo.getViewCount());
		return this;
	}
	
	public String getCompany() {

		this.company = getString("Company");
		return this.company;

	}

	public void SetCompany(String company) {
		this.company = company;
		put("Company", company);

	}

	public String getJobTitle() {
		this.JT = getString("Job_Title");
		return this.JT;
	}
	

	public void SetJobTitle(String JobTitle) {
		this.JT = JobTitle;
		if(JobTitle!=null){
		put("Job_Title", JobTitle);
		}
		else put("Job_Title", "TBC");

	}

	public void setDeadline(Date deadline) {
		if (deadline!=null)
			this.put("Deadline", deadline);
		
	     
		
	}
	
	public Date getDeadline() {
		return getDate("Deadline");
	}

	public ParseObject getLogoPointer() {
		// Log.v("jobs", ""+getParseObject("Company_Symbol").toString());
		// ParseObject pointer = null;
		// try {
		// pointer = getParseObject("Company_Symbol").fetchIfNeeded();
		// } catch (ParseException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// this.fetchIfNeeded();
		// if (pointer!=null)
	  return getParseObject("Company_Symbol") ; 
		
//		return getString("Company_Symbol");
		// Log.v("jobs", "null");
		// return null;

	}

	public void SetLogoPointer(ParseObject CS) {
		if (CS != null) {
			put("Company_Symbol", CS);
			Log.d("jobs", "after put");
		}

	}
	
	
	public int getViewCount(){
		
		return getInt("ViewCount");
		
	}
	
	public void setViewCount(int viewcount){
		
		put("ViewCount",viewcount);
		
	}

	@Override
	public String toString() {

		return "Company Name: " + this.company + "\n" + "Job Title:" + this.JT;

	}
	
	public String getDescription(){
		
		if(getString("Description")!=null){
		
		return getString("Description");	}

		else return "Sorry for missing information. OFFERS Team would provide it ASAP" ;
		
	}
	
	public String getReq(){
		
		if (getString("Req")!=null){
			
			return getString("Req");
		}
		
		else return "Sorry for missing information. OFFERS Team would provide it ASAP"; 
		
	}
	
	
	public void setType(String type){
		
		this.type=type;
		
	}
	
	public boolean isSaved(){
		
		
		return false;
		
	}
	
	

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
