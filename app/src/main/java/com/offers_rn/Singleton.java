package com.offers_rn;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.util.Log;

import com.offers_rn.parseobject.SummerTrip;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.offers_rn.parseobject.Competition;
import com.offers_rn.parseobject.Exchange;
import com.offers_rn.parseobject.Govern;
import com.offers_rn.parseobject.GradJobs;
import com.offers_rn.parseobject.Jobs;
import com.offers_rn.parseobject.Mentorship;
import com.offers_rn.parseobject.RubbishJobs;
import com.offers_rn.parseobject.Scholar;
import com.offers_rn.parseobject.Workshop;
import com.offers_rn.parseobject.MT;
import com.offers_rn.parseobject.GT;


public class Singleton {
	
	private static Singleton single = new Singleton();
    private Singleton(){}
    public static Singleton getInstance(){     
        return single;
    }
	
    private boolean CompetitionQueryDone = false;
    private boolean InternQueryDone = false;
    private boolean ExchangeQueryDone = false;
    private boolean GradJobQueryDone = false;
	public boolean MTQueryDone = false;
	public boolean GTQueryDone = false;
	private List<Jobs> InternJobslist = new ArrayList<Jobs>();
    private List<GradJobs> GradJobslist = new ArrayList<GradJobs>();
    private List<Exchange> Exchangelist = new ArrayList<Exchange>();
    private List<Competition> Comp_list = new ArrayList<Competition>();
    private List<Mentorship> Mentor_list = new ArrayList<Mentorship>();
    private List<Scholar> Scholar_list = new ArrayList<Scholar>();
    private List<Workshop> Workshop_list = new ArrayList<Workshop>();
    private List<Govern> Govern_list = new ArrayList<Govern>();
    private List<RubbishJobs> normal_list = new ArrayList<RubbishJobs>();
	private List<MT> MT_list = new ArrayList<MT>();
	private List<GT> GT_list = new ArrayList<GT>();
	private List<SummerTrip> summer_list = new ArrayList<SummerTrip>();

    
    public int getInternListCount(){
		
		return this.InternJobslist.size();
	}
	
	public int getExchangeListCount(){
		return this.Exchangelist.size();
	}
	
	public int getGradListCount(){
		return this.GradJobslist.size();
	}
	
	public int getCompListCount(){
		
		return this.Comp_list.size();
	}

	public int getMTListCount(){

		return this.MT_list.size();
	}

	public int getGTListCount(){

		return this.GT_list.size();
	}
	
	public void addIntern(Jobs intern){
		InternJobslist.add(intern);
	}
	
	public void addGrad(Jobs grad){
		GradJobslist.add((GradJobs) grad);
	}
	
	public void addExchange(Jobs exchange){
		Exchangelist.add((Exchange)exchange);
	}

	public void addMT(Jobs mt){
		MT_list.add((MT)mt);
	}

	public void addGT(Jobs gt){
		GT_list.add((GT)gt);
	}
	
	public void addCompetition(Jobs comp){
		
		Comp_list.add((Competition)comp);
	}
	
	public List<Jobs> getInternList(){
		
		return InternJobslist;
	}
	
    public List<GradJobs> getGradList(){
		
		return GradJobslist;
	}
    
    public List<Exchange> getExchange(){
		
		return Exchangelist;
	}
    
    public List<Jobs> getList(String type){
		
    	switch(type){
    	
    		case Constants.gradjob:
		     	return (List<Jobs>)(Object)GradJobslist;
		     
    		case Constants.competition:
    		 	return (List<Jobs>)(Object)Comp_list;
    		
    		case Constants.workshop:
    		 	return (List<Jobs>)(Object)Workshop_list;
    		
    		case "Scholar":
    		 	return (List<Jobs>)(Object)Scholar_list;
    		
    		case Constants.mentorship:
    		 	return (List<Jobs>)(Object)Mentor_list;
    		
    		case "Normal":
    		 	return (List<Jobs>)(Object)normal_list;
    		 
    		case Constants.internship:
    		 	return InternJobslist;
    		 
    		case Constants.exchange:
    		 	return (List<Jobs>)(Object)Exchangelist;

			case Constants.MT:
				return (List<Jobs>)(Object)MT_list;

			case Constants.GT:
				return (List<Jobs>)(Object)GT_list;

			case Constants.summertrip:
				return (List<Jobs>)(Object)summer_list;
    		 
    	default :
    		return null;
		
    	}
	}
    
    public void CompetitionQuery(){
		
		 
		
		ParseQuery<Competition> query = ParseQuery.getQuery("Competition");
		 
	    query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.include("Company_Symbol");
        query.findInBackground(new FindCallback<Competition>() {
        @Override
        public void done(List<Competition> list, ParseException e) {
        	
 
            if (e != null){
    //            Toast.makeText(rootView.getContext(), "Error " + e, Toast.LENGTH_SHORT ).show();
            }
            else{
          	  
          	  
          	  Collections.sort(list,new Comparator<Competition>() {
        		    @Override
        		    public int compare(Competition r1, Competition r2) {
        		    	
        		        if(r1.getDeadline().before(r2.getDeadline())){
        		    	return -1;
        		        }
        		    	
        		    		else{return 0;}
        		    		
        		    	
        		    }
        		    
        		});
          	  
            for (final Competition tempjob : list){
            	final Competition newJob = new Competition();
            	//https://www.parse.com/docs/android/guide#objects-relational-data
           
					newJob.duplicateFrom(tempjob);
					Singleton.getInstance().addCompetition(tempjob);
          
            }

    		if(Singleton.getInstance().getCompListCount()>0)
    			CompetitionQueryDone = true;
    		else
    			CompetitionQuery();
        }
      
       
        }
        
    });
      
      
      
      
	}
    
    public void InternJobQuery(){
		
		 
		
		 ParseQuery<Jobs> query = ParseQuery.getQuery("InternJobs");
		 
		 query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);

        query.include("Company_Symbol");
        Calendar c = Calendar.getInstance(); 
        query.whereGreaterThanOrEqualTo("Deadline",c.getTime());
        query.findInBackground(new FindCallback<Jobs>() {
          @Override
          public void done(List<Jobs> list, ParseException e) {
       	   
       	   
          	
   
              if (e != null){
      //            Toast.makeText(rootView.getContext(), "Error " + e, Toast.LENGTH_SHORT ).show();
              }
              
              else{
           	   
           	   Collections.sort(list,new Comparator<Jobs>() {
           		    @Override
           		    public int compare(Jobs r1, Jobs r2) {
           		    	
           		        if(r1.getDeadline().before(r2.getDeadline())){
           		    	return -1;
           		        }
           		    	
           		    		else{return 0;}
           		    		
           		    	
           		    
           		    }
           		});
              
              for (final Jobs tempjob : list){
              	final Jobs newJob = new Jobs();
              	//https://www.parse.com/docs/android/guide#objects-relational-data
             
					newJob.duplicateFrom(tempjob);
					Singleton.getInstance().addIntern(tempjob);
                  
                  
              }

      		if(Singleton.getInstance().getInternListCount()>=0)
      			InternQueryDone = true;
      		else
      			InternJobQuery();
          }
              
          }

      });
	}
    
    public void ExchangeQuery(){
		
		 
		
		 ParseQuery<Exchange> query = ParseQuery.getQuery("Exchange");
		 
	     query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
         query.include("Company_Symbol");
         query.findInBackground(new FindCallback<Exchange>() {
        @Override
        public void done(List<Exchange> list, ParseException e) {
        	
 
            if (e != null){
    //            Toast.makeText(rootView.getContext(), "Error " + e, Toast.LENGTH_SHORT ).show();
            }
            else{
          	  
          	  
          	  Collections.sort(list,new Comparator<Exchange>() {
        		    @Override
        		    public int compare(Exchange r1, Exchange r2) {
        		    	
        		        if(r1.getDeadline().before(r2.getDeadline())){
        		    	return -1;
        		        }
        		    	
        		    		else{return 0;}
        		    		
        		    	
        		    }
        		    
        		});
          	  
            for (final Exchange tempjob : list){
            	final Exchange newJob = new Exchange();
            	//https://www.parse.com/docs/android/guide#objects-relational-data
           
					newJob.duplicateFrom(tempjob);
					Singleton.getInstance().addExchange(tempjob);
                
            }

    		Log.d("RETURN", "Exchanges, size of array"+Integer.toString(Singleton.getInstance().getExchangeListCount()));
    		if(Singleton.getInstance().getExchangeListCount()>=0)
    		ExchangeQueryDone = true;
    		else
    			ExchangeQuery();
        }
      
       
        }
        
    });
      
	}
    
    public void GradJobQuery(){



		ParseQuery<GradJobs> query = ParseQuery.getQuery("GradJobs");

		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		query.include("Company_Symbol");
		Calendar c = Calendar.getInstance();
        query.whereGreaterThanOrEqualTo("Deadline",c.getTime());
		query.findInBackground(new FindCallback<GradJobs>() {
			@Override
			public void done(List<GradJobs> list, ParseException e) {


				if (e != null){
					//            Toast.makeText(rootView.getContext(), "Error " + e, Toast.LENGTH_SHORT ).show();
				}
				else{


					Collections.sort(list,new Comparator<GradJobs>() {
						@Override
						public int compare(GradJobs r1, GradJobs r2) {

							if(r1.getDeadline().before(r2.getDeadline())){
								return -1;
							}

							else{return 0;}


						}

					});

					for (final GradJobs tempjob : list){
						final GradJobs newJob = new GradJobs();
						//https://www.parse.com/docs/android/guide#objects-relational-data

						newJob.duplicateFrom(tempjob);
						Singleton.getInstance().addGrad(tempjob);


					}

					Log.d("RETURN", "GradJobs, size of array"+Integer.toString(Singleton.getInstance().getGradListCount()));
					if(Singleton.getInstance().getGradListCount()>=0)
						GradJobQueryDone = true;
					else
						GradJobQuery();
				}


			}

		});
	}

	public void MTQuery(){



		ParseQuery<MT> query = ParseQuery.getQuery("MT");

		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		query.include("Company_Symbol");
		Calendar c = Calendar.getInstance();
        query.whereGreaterThanOrEqualTo("Deadline",c.getTime());
		query.findInBackground(new FindCallback<MT>() {
			@Override
			public void done(List<MT> list, ParseException e) {


				if (e != null){
					//            Toast.makeText(rootView.getContext(), "Error " + e, Toast.LENGTH_SHORT ).show();
				}
				else{


					Collections.sort(list,new Comparator<MT>() {
						@Override
						public int compare(MT r1, MT r2) {

							if(r1.getDeadline().before(r2.getDeadline())){
								return -1;
							}

							else{return 0;}


						}

					});

					for (final MT tempjob : list){
						final MT newJob = new MT();
						//https://www.parse.com/docs/android/guide#objects-relational-data

						newJob.duplicateFrom(tempjob);
						Singleton.getInstance().addMT(tempjob);


					}

					Log.d("RETURN", "MT, size of array"+Integer.toString(Singleton.getInstance().getGradListCount()));
					if(Singleton.getInstance().getMTListCount()>=0)
						MTQueryDone = true;
					else
						MTQuery();
				}


			}

		});
	}

	public void GTQuery(){



		ParseQuery<GT> query = ParseQuery.getQuery("GT");

		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		query.include("Company_Symbol");
		Calendar c = Calendar.getInstance();
       query.whereGreaterThanOrEqualTo("Deadline",c.getTime());
		query.findInBackground(new FindCallback<GT>() {
			@Override
			public void done(List<GT> list, ParseException e) {


				if (e != null){
					//            Toast.makeText(rootView.getContext(), "Error " + e, Toast.LENGTH_SHORT ).show();
				}
				else{


					Collections.sort(list,new Comparator<GT>() {
						@Override
						public int compare(GT r1, GT r2) {

							if(r1.getDeadline().before(r2.getDeadline())){
								return -1;
							}

							else{return 0;}


						}

					});

					for (final GT tempjob : list){
						final GT newJob = new GT();
						//https://www.parse.com/docs/android/guide#objects-relational-data

						newJob.duplicateFrom(tempjob);
						Singleton.getInstance().addGT(tempjob);


					}

					Log.d("RETURN", "GT, size of array"+Integer.toString(Singleton.getInstance().getGradListCount()));
					if(Singleton.getInstance().getGTListCount()>=0)
						GTQueryDone = true;
					else
						GTQuery();
				}


			}

		});
	}
	
}
