package com.offers_rn.offers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.bhargavms.dotloader.DotLoader;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.offers_rn.MainApplication;
import com.offers_rn.R;
import com.offers_rn.detail.Gossip;
import com.offers_rn.detail.GossipFragment;
import com.offers_rn.detail.JDFragment;
import com.offers_rn.detail.PrepareFragment;
import com.offers_rn.parseobject.Exchange;
import com.offers_rn.parseobject.GradJobs;
import com.offers_rn.parseobject.Jobs;
import com.squareup.picasso.Picasso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
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

public class InternDetailActivity extends AppCompatActivity {

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
	protected DotLoader dotloader;
	
	private String job_id;
	private String Company;
	private String JobTitle;
	private String Deadline;
	private byte[] data;
	private Bitmap bmp;
	private String type;
	
	final ArrayList<String> JD = new ArrayList<String>();  

    final List<String> PreparationMaterial = new ArrayList<String>();
	
    final ArrayList<Gossip> gossiplist = new ArrayList<Gossip>();
    
    public static boolean JDQueryDone;
    public static boolean GossipQueryDone;
	
    String userName;
	
	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Spinner spn1,spn2;
    private ViewPager viewPager;
	
 //   private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		final ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
		setContentView(R.layout.offer_detail);
		
		SharedPreferences settings = getSharedPreferences(this.getApplicationContext().getString(R.string.app_name), 0);
	    userName = settings.getString("username", "/");
		
		JDQueryDone = false;
		GossipQueryDone = false;
		
		this.vCompany = (TextView) findViewById(R.id.offer_card_context);
		this.vJobTitle= (TextView) findViewById(R.id.offer_card_title);
		this.CompanyLogo = (ImageView) findViewById(R.id.offer_card_icon);
		this.vDeadline = (TextView) findViewById(R.id.offer_card_deadline);
		this.Sendbutton = (FloatingActionButton)findViewById(R.id.btn_send);
		this.dotloader = (DotLoader) findViewById(R.id.offer_card_dotloader);
		
		if (this.getIntent().getExtras()!=null){
			
			this.job_id = this.getIntent().getExtras().getString("JobObjectID");
			this.Company = this.getIntent().getExtras().getString("Company");
			this.JobTitle = this.getIntent().getExtras().getString("JobTitle");
		//	this.data = this.getIntent().getExtras().getByteArray("CompanyLogo");
			this.Deadline=this.getIntent().getExtras().getString("Deadline");
			this.type = this.getIntent().getExtras().getString("Type");
		//	this.bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

			ParseQuery.getQuery(this.type).getInBackground(this.job_id, new GetCallback<ParseObject>() {
				@Override
				public void done(ParseObject tempjob, ParseException e) {

					((Jobs)tempjob).getParseObject("Company_Symbol").fetchIfNeededInBackground( new GetCallback<ParseObject>() {
						public void done(ParseObject arg0, ParseException e) {
							if (e == null) {

								Picasso.with(getApplicationContext())
										.load(arg0.getParseFile("Company_Symbol").getUrl())
										.resize(100, 100)
										.centerInside()
										.into(CompanyLogo);

								dotloader.setVisibility(View.GONE);
								dotloader.clearAnimation();
								dotloader = null;

							} else {

							}
						}
					});

				}
			});

			//	CompanyLogo.setImageBitmap(bmp);
			
			
			vCompany.setText(this.Company);
			vJobTitle.setText(this.JobTitle);
			vDeadline.setText(this.Deadline);
			
			try {
				loadData();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
					
//			new AsynTaskLoadParse().execute();
			
			initSpinner();
			initSpinner2();
			viewPager = (ViewPager)findViewById(R.id.detail_tab_viewpager);
	/*		if (viewPager != null){
	            setupViewPager(viewPager);
	        } */


	        
	    
			
			Sendbutton.setOnClickListener(new View.OnClickListener() {
				
			    @Override
                public void onClick(View v) {
			    	EditText edit =  (EditText) findViewById(R.id.chat_text);
			    	String chat_text=edit.getText().toString();
			    	Gossip gossip = new Gossip();
			    	gossip.put("TextContent", chat_text);
			    	gossip.put("offersid", InternDetailActivity.this.job_id);
			    	gossip.put("GossipType", spn1.getSelectedItem().toString());
			    	gossip.put("PostType",spn2.getSelectedItem().toString());
			    	gossip.put("Helpful",0);
			    	gossip.put("Sender",userName);
			    	gossip.saveInBackground(new SaveCallback() {
			    	    @Override
			    	    public void done(ParseException e) {
			    	        Toast.makeText(InternDetailActivity.this, "Item added to database", Toast.LENGTH_LONG).show();
			    	        
			    	        try {
								loadData();
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
			    	    }
			    	});
			    	
			    	edit.setText("");
		
			    }

                });
			
			
			Log.v("TESTING OUTSIDE", Integer.toString(JD.size()));
			
/*			
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
	        });  */
	        		
		}
	}

	
	private void setupViewPager(final ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(GossipFragment.newInstance(gossiplist), "Gossip");
        adapter.addFrag(JDFragment.newInstance(JD,this.Company,this.JobTitle, this.Deadline), "JD & Req");
        adapter.addFrag(PrepareFragment.newInstance(), "Poster");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.detail_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
	
	private void initSpinner(){		
		spn1 = (Spinner) findViewById(R.id.GossipSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gossip_tag_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn1.setAdapter(adapter);
	}
	
	private void initSpinner2(){		
		spn2 = (Spinner) findViewById(R.id.GossipSpinner2);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.gossip_criteria_array, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn2.setAdapter(adapter2);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
	//	this.bmp.recycle();

/*		myWebView.removeAllViews();
		myWebView.destroy();
		myWebView = null;  */
	    
	    super.onDestroy();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		CompanyLogo.destroyDrawingCache();
		CompanyLogo.setImageDrawable(null);
		//Picasso.with(CompanyLogo.getContext()).invalidate(imagePath);
		this.data=null;
		System.gc();
		finish();
		return;
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

	}
	
	static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<Fragment>();
        private final List<String> mFragmentTitleList = new ArrayList<String>();

        public ViewPagerAdapter(FragmentManager manager){
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }
    }

	public void loadData() throws InterruptedException {
		// TODO Auto-generated method stub
		final ProgressDialog pd = new ProgressDialog(this);
	    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pd.setMessage("Loading Data");
	    pd.setIndeterminate(false);
	    pd.setCancelable(false);
	    pd.show();
	
	    ParseQuery<Jobs> query = ParseQuery.getQuery(type);
	    Log.v(type,"hello");
				
	    
	    query.getInBackground(InternDetailActivity.this.job_id, new GetCallback<Jobs>(){

	    	   @Override
	    		public void done(Jobs Jobs, ParseException e) {
	    							// TODO Auto-generated method stub
	    							
	    						
	    						if(e==null){					
	    						   JD.add(Jobs.getDescription());
	    						   Log.v("TESTING INSIDE 1", Integer.toString(JD.size()));	
	    						}
	    						
	    					    else{
	    							
	    							Log.v("Error happen",e.getMessage());
	    						}
	    						
	    					JDQueryDone = true;
	    						
	    					if(JDQueryDone == true && GossipQueryDone == true){
	    						    Log.v("JDQuery","done");
	    							pd.dismiss();
	    							if(viewPager!=null)
	    								setupViewPager(viewPager);
	    					}
	    					
	    				}
	    		});
	    
	    ParseQuery<Gossip> query2 = ParseQuery.getQuery("Gossip");
	    query2.whereEqualTo("offersid", this.job_id);
	    query2.addDescendingOrder("createdAt");
		query2.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		query2.findInBackground(new FindCallback<Gossip>(){

			@Override
			public void done(List<Gossip> temp_list, ParseException arg1) {
				
				gossiplist.clear();
				for (Gossip gossip : temp_list){
					gossiplist.add(gossip);
				}
				
				GossipQueryDone = true ; 
				
				if(JDQueryDone == true && GossipQueryDone == true){
					Log.v("GossipQuery","done");
					pd.dismiss();
					if(viewPager!=null)
						setupViewPager(viewPager);
				}
				
			}
		});
	    		
	    		
		
	}
	
		
	private class AsynTaskLoadParse extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;
       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           pDialog = new ProgressDialog(InternDetailActivity.this);
           pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
           pDialog.setMessage("Getting Data ...");
           pDialog.setIndeterminate(true);
           pDialog.setCancelable(true);
           pDialog.show();

       }

       @Override
       protected String doInBackground(String... args) {
    	   
    	   try{
				
				ParseQuery<Jobs> query = ParseQuery.getQuery(type);
				
			    query.getInBackground(InternDetailActivity.this.job_id, new GetCallback<Jobs>(){

					@Override
					public void done(Jobs Jobs, ParseException e) {
						// TODO Auto-generated method stub
					if(e==null){					
					   JD.add(Jobs.getDescription());
			           Toast.makeText(getApplicationContext(), Integer.toString(JD.size()), Toast.LENGTH_LONG).show();
			        
					}
					
					else{
						
						
					}
						
						
					}
			    	
			    });
				
			}
			finally{

			}
           return null;
       }
        @Override
        protected void onPostExecute(String json) {
        	Toast.makeText(getApplicationContext(), "Job "+Integer.toString(JD.size()), Toast.LENGTH_LONG).show();
            pDialog.dismiss();
            
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
//	LinearLayout linearLayout = (LinearLayout)findViewById(R.id.webview);
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

	
}




