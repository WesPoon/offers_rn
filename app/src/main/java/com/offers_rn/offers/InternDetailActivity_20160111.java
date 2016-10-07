package com.offers_rn.offers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.offers_rn.R;
import com.offers_rn.parseobject.Jobs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class InternDetailActivity_20160111 extends AppCompatActivity{

	Jobs jobs;
	protected TextView vCompany;
	protected TextView vJobTitle;
	protected TextView vEmail;
	protected TextView vTitle;
	protected TextView description;
	protected TextView vDeadline;
	protected ImageView CompanyLogo;
	protected FloatingActionButton Sendbutton;
	protected EditText SendText; 
	
	private String job_id;
	private String Company;
	private String JobTitle;
	private String Deadline;
	private byte[] data;
	private Bitmap bmp;
	private String type;
	
	
	
	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Spinner spn1;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		final ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
		setContentView(R.layout.offer_detail);
		
		this.vCompany = (TextView) findViewById(R.id.offer_card_context);
		this.vJobTitle= (TextView) findViewById(R.id.offer_card_title);
		this.CompanyLogo = (ImageView) findViewById(R.id.offer_card_icon);
		this.vDeadline = (TextView) findViewById(R.id.offer_card_deadline);
		this.Sendbutton = (FloatingActionButton)findViewById(R.id.btn_send);
		
		if (this.getIntent().getExtras()!=null){
			
			this.job_id = this.getIntent().getExtras().getString("JobObjectID");
			this.Company = this.getIntent().getExtras().getString("Company");
			this.JobTitle = this.getIntent().getExtras().getString("JobTitle");
			this.data = this.getIntent().getExtras().getByteArray("CompanyLogo");
			this.Deadline=this.getIntent().getExtras().getString("Deadline");
			this.type = this.getIntent().getExtras().getString("Type");
			this.bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
			
			CompanyLogo.setImageBitmap(bmp);
			
			
			vCompany.setText(this.Company);
			vJobTitle.setText(this.JobTitle);
			vDeadline.setText(this.Deadline);
			
			prepareListData();
			initSpinner();
			
			Sendbutton.setOnClickListener(new View.OnClickListener() {
				
			    @Override
                public void onClick(View v) {
			    	final EditText edit =  (EditText) findViewById(R.id.chat_text);
			    	final String chat_text=edit.getText().toString();
			    	Thread UpdateChat = new Thread(){
						
						public void run(){
							
							try{
								
								ParseQuery<ParseObject> query = ParseQuery.getQuery(type);
							    query.getInBackground(job_id, new GetCallback<ParseObject>(){

									@Override
									public void done(ParseObject Jobs, ParseException e) {
										// TODO Auto-generated method stub
									if(e==null){
										
										Jobs.addAllUnique("Comment", Arrays.asList("Wes Poon:\n"+chat_text));
										Jobs.saveInBackground();
										
									}
									
									else{
										Log.d(job_id,"nth found");
										
									}
										
										
									}
							    	
							    });
								
							}
							finally{
								
								finish();
								startActivity(getIntent());
								overridePendingTransition(0,0);
								System.gc();
							}
						}
						
					};
					
					UpdateChat.start();
		
			    }

                });
			
			
			expListView = (ExpandableListView) findViewById(R.id.lvExp);	
			listAdapter = new ExpandableListAdapter(getApplicationContext(), this.listDataHeader, this.listDataChild);
	        // setting list adapter
	        expListView.setAdapter(listAdapter);
	        
	     // Listview Group click listener	        
	        
	        expListView.setOnGroupClickListener(new OnGroupClickListener() {
	 
	            @Override
	            public boolean onGroupClick(ExpandableListView parent, View v,
	                    int groupPosition, long id) {
	                // Toast.makeText(getApplicationContext(),
	                // "Group Clicked " + listDataHeader.get(groupPosition),
	                // Toast.LENGTH_SHORT).show();
	                return false;
	            }

				
	        });
	 
	        // Listview Group expanded listener
	        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
	 
	            @Override
	            public void onGroupExpand(int groupPosition) {
	                Toast.makeText(getApplicationContext(),
	                        listDataHeader.get(groupPosition) + " Expanded",
	                        Toast.LENGTH_SHORT).show();
	            }
	        });
	 
	        // Listview Group collasped listener
	        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
	 
	            @Override
	            public void onGroupCollapse(int groupPosition) {
	                Toast.makeText(getApplicationContext(),
	                        listDataHeader.get(groupPosition) + " Collapsed",
	                        Toast.LENGTH_SHORT).show();
	 
	            }
	        });
	 
	        // Listview on child click listener
	        expListView.setOnChildClickListener(new OnChildClickListener() {
	 
	            @Override
	            public boolean onChildClick(ExpandableListView parent, View v,
	                    int groupPosition, int childPosition, long id) {
	                // TODO Auto-generated method stub
	                Toast.makeText(
	                        getApplicationContext(),
	                        listDataHeader.get(groupPosition)
	                                + " : "
	                                + listDataChild.get(
	                                        listDataHeader.get(groupPosition)).get(
	                                        childPosition), Toast.LENGTH_SHORT)
	                        .show();
	                return false;
	            }
	        });
	        		
		}
	}
/*	private Jobs getJob(String job_id) throws ParseException {
		
		//final List<Jobs> tempresult=new ArrayList<Jobs>();
        final Jobs newJob = new Jobs();
        ParseQuery<Jobs> query = new ParseQuery<Jobs>("Jobs");
        //query.get(job_id);
      //  query.include("Company_Symbol");
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
    }  */
	
/*	public void setTexts(Jobs jobs){
		vCompany.setText(jobs.getCompany());
		vJobTitle.setText(jobs.getJobTitle());
		description.setText(jobs.getCompany()+" "+jobs.getJobTitle());
		if (jobs.getDeadline()!=null)
		deadline.setText(jobs.getDeadline().toString());
	}  */
	
/*	public void setBitMap(Jobs ci){
		try {
			byte[] data = ci.getLogo().getData();
			Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
			data=null; //very important to clear data
			CompanyLogo.setImageBitmap(bmp);
		} catch (ParseException e) {
			Log.d("ca", "fail build image");
		} catch (NullPointerException e) {
			Log.d("ca", "null point");
		}
	} */
	
/*	@SuppressWarnings("deprecation")
	private void buildWeb(){
		
		myWebView = new WebView(getApplicationContext());
//		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.webview);
		linearLayout.addView(myWebView);
		
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		myWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		webSettings.setBuiltInZoomControls(false);
		webSettings.setRenderPriority(RenderPriority.HIGH);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		
		myWebView.setWebViewClient(new WebViewClient() {
	        @Override
	        public void onReceivedError(WebView view, int errorCode,
	                String description, String failingUrl) {
	            // Handle the error
	        }
	        
	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            view.loadUrl(url);
	            return false;
	        }
	    });
		myWebView.loadUrl("http://www.dbs.edu.hk");
		
		
	}  */
	
	private void initSpinner(){		
		spn1 = (Spinner) findViewById(R.id.GossipSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gossip_tag_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn1.setAdapter(adapter);	
		
	}
	
	
	private void prepareListData() {
		
		
        final List<String> description = new ArrayList<String>();  
        final List<String> req = new ArrayList<String>();
        final List<String> gossip = new ArrayList<String>();
        final List<String> PreparationMaterial = new ArrayList<String>();
		
		
		this.listDataHeader = new ArrayList<String>();
        this.listDataChild = new HashMap<String, List<String>>();
 
        // Adding child data
        this.listDataHeader.add("Description");
        this.listDataHeader.add("Requirement");
        this.listDataHeader.add("Gossip");
        this.listDataHeader.add("Preparaton Material");
		
		Thread RetrieveJD = new Thread(){
			
			public void run(){
				
				try{
					
					ParseQuery<Jobs> query = ParseQuery.getQuery(type);
					
				    query.getInBackground(InternDetailActivity_20160111.this.job_id, new GetCallback<Jobs>(){

						@Override
						public void done(Jobs Jobs, ParseException e) {
							// TODO Auto-generated method stub
						if(e==null){					
						description.add(Jobs.getDescription());
				        req.add(Jobs.getReq());
				        
						}
						
						else{
							
							
						}
							
							
						}
				    	
				    });
					
				}
				finally{
					interrupt();
					System.gc();
				}
			}
			
		};
		
       Thread RetrieveGossip = new Thread(){
			
			public void run(){
				
				try{
					
					ParseQuery<Jobs> query = ParseQuery.getQuery(type);				
				    query.getInBackground(InternDetailActivity_20160111.this.job_id, new GetCallback<Jobs>(){

						@Override
						public void done(Jobs Jobs, ParseException e) {
							// TODO Auto-generated method stub
						if(e==null && Jobs.getList("Comment")!=null){
						
							Log.v("Check Array",Jobs.getList("Comment").get(0).toString());
							if(Jobs.getList("Comment").size()>0){
						      for(int i=0;i<=Jobs.getList("Comment").size()-1;i++){	
						      gossip.add(Jobs.getList("Comment").get(i).toString());
						      }
							}
						}
						
						else{
							
							
						}
							
							
						}
				    	
				    });
					
				}
				finally{
					interrupt();
					System.gc();
				}
			}
			
		};
		

	    RetrieveJD.start();
	    RetrieveGossip.start();
		
        // Adding child data
        this.listDataChild.put(listDataHeader.get(0), description); // Header, Child data
        this.listDataChild.put(listDataHeader.get(1), req);
        this.listDataChild.put(listDataHeader.get(2), gossip);
        this.listDataChild.put(listDataHeader.get(3), PreparationMaterial);
    }
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
		this.bmp.recycle();
		this.data=null;	
		
/*		myWebView.removeAllViews();
		myWebView.destroy();
		myWebView = null;  */
	    
	    super.onDestroy();
	    
	    //android.os.Process.killProcess(android.os.Process.myPid());
	    
	    System.gc();
	    
//	    Toast.makeText(getApplicationContext(),"Detail is Destroyed, Great!!",Toast.LENGTH_SHORT).show();
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		prepareListData();
	}
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
	}

	
}
