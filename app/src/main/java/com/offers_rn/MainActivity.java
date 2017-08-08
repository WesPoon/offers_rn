package com.offers_rn;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.offers_rn.offers.InternFragment;
import com.offers_rn.offers.GradFragment;
import com.offers_rn.parseobject.Jobs;
import com.offers_rn.profile.ChooseUniActivity;
import com.offers_rn.profile.Profile;
import com.offers_rn.profile.ScollGallery;
import com.offers_rn.profile.ViewProfile;
import com.offers_rn.testcode.TestCheckBox;
import com.offers_rn.chatroom.Chatroom;
import com.offers_rn.menulist.*;
import com.offers_rn.nav.FragmentMajor;
import com.offers_rn.nav.FragmentMinor;
import com.offers_rn.nav.NavItem;
import com.offers_rn.nav.NavListAdapter;
import com.offers_rn.uielement.RoundedImageView;
import com.squareup.picasso.Picasso;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.NavigationMode;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.SearchView;
//import android.support.design.widget.NavigationView;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity.Header;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;



public class MainActivity extends AppCompatActivity{

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	
	final String EMAIL_ENDPOINT="http://54.65.1.149/email_send/";
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	private DrawerLayout drawerLayout;
	private RelativeLayout drawerPane, profileBox;
	private ListView lvNav;
	public ActionBar actionBar;
	private RoundedImageView profilePic;

//	private NavigationView navigationView;
	private List<NavItem> listNavItems;

	private ActionBarDrawerToggle drawerlistener;

	static private int selected ;
	String userName;
	String profile_pic_url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.activity_main);
				
		drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
		drawerPane = (RelativeLayout)findViewById(R.id.drawerPane);
		profileBox = (RelativeLayout)findViewById(R.id.profileBox);
		lvNav = (ListView)findViewById(R.id.navList);
		profilePic = (RoundedImageView)findViewById(R.id.avatar);
		
		// Set up the action bar.
		actionBar = getSupportActionBar();

	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_squeak);
	    
	    getSupportActionBar().setDisplayShowHomeEnabled(true);
	    getSupportActionBar().setHomeButtonEnabled(true);
	    getSupportActionBar().setIcon(R.mipmap.ic_squeak); //also displays wide logo

//      getSupportActionBar().setDisplayShowTitleEnabled(false); //optional
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//      actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
		SharedPreferences settings = getSharedPreferences(this.getApplicationContext().getString(R.string.app_name), 0);
	    userName = settings.getString("username", "/");
		TextView userName_TextView = (TextView)findViewById(R.id.userName);
	    userName_TextView.setText(userName);

		profile_pic_url = settings.getString("profile_pic_url","/");
		Picasso.with(this).
				load(profile_pic_url).
				placeholder(R.drawable.graduate_jobs).
				into(profilePic);

		listNavItems = new ArrayList<NavItem>();

		listNavItems.add(new NavItem("Level Up","Intern / Exchange / Competition",R.drawable.levelup));
		listNavItems.add(new NavItem("Final Year","MT / GT",R.drawable.youcaremost));
		listNavItems.add(new NavItem("For Elite","Summer Trip / Workshop / Mentorship",R.drawable.forelite));
		listNavItems.add(new NavItem("Chat Room","Network Circle",R.drawable.chatroom));
		listNavItems.add(new NavItem("Watch Advert","+10 PT",R.drawable.advert));
//		listNavItems.add(new NavItem("Bad Jobs","Gossip:)",R.drawable.badjob));
//		listNavItems.add(new NavItem("Start-up","Discover~",R.drawable.strangecompany));
		
	 //Set Default Fragment
		
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction()
				.replace(R.id.main_content, FragmentMinor.newInstance(actionBar)).commit();
		selected = 0;
        
       //
       
		NavListAdapter navListAdapter = new NavListAdapter(getApplicationContext(),R.layout.item_nav_list,listNavItems);
		
		lvNav.setAdapter(navListAdapter);
		lvNav.setItemChecked(0,true);  //very important to check chosen or not
		drawerLayout.closeDrawer(drawerPane);
		
		profileBox.setOnClickListener(new OnClickListener(){
			
			@Override
	        public void onClick(View v) 
	        {
	            viewProfile();
	        }
			
		});
		//set listener for Navigation item
		
		lvNav.setOnItemClickListener(new OnItemClickListener() {
           
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				
				//replace the fragment with the selection correspondingly
				FragmentManager fm = getSupportFragmentManager();
				
				Log.d("press"+Integer.toString(position),"listpostion"+Integer.toString(lvNav.getPositionForView(arg1)));
				
				if(position==selected){
					
					drawerLayout.closeDrawers();
					
				}
				
				else{
				
				switch(position){
		/*		case 0:
					viewProfile();
					break;				*/
				
				case 0:			
					fm
					.beginTransaction()
					.replace(R.id.main_content, FragmentMinor.newInstance(actionBar))
					.commit();					
					lvNav.setItemChecked(position, true);					
					drawerLayout.closeDrawers();					
					selected = 0 ;					
					break;				

				case 1:					
					fm
					.beginTransaction()
					.replace(R.id.main_content, FragmentMajor.newInstance(actionBar))
					.commit();					
					lvNav.setItemChecked(position, true);					
					drawerLayout.closeDrawers();				
					selected =1;					
				    break;
				    
				case 2:
					selected = 2;
					break;
				
				case 3:
					chatroom();
					break;
					
				case 4:
					test();
					break;
			    
				default: break;
				
			    }
			 }
			}
          });
		
		//create drawerListener for drawer layout
		
		
		
		drawerlistener = new ActionBarDrawerToggle(this, drawerLayout,R.string.drawer_opened, R.string.drawer_closed){

			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				invalidateOptionsMenu();
				Log.d("Drawer","Closed");
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				invalidateOptionsMenu();
				Log.d("Drawer","Open");
				super.onDrawerOpened(drawerView);
			}
			
			
			
		};
		
		Log.d("onCreate","here");
		
		drawerLayout.setDrawerListener(drawerlistener);
		

		
        
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
	/*	mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOffscreenPageLimit(3);
  
		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});
		
		actionBar.addTab(
	            actionBar.newTab()
	                    
	                    .setText("Intern")
	                    .setTabListener(this)
	                    
	    );

	    actionBar.addTab(
	            actionBar.newTab()
	                    .setText("GradJob")
	                    .setTabListener(this)
	    );
	    
	    actionBar.addTab(
	            actionBar.newTab()
	                    .setText("Exchange")
	                    .setTabListener(this)
	    );
	    
	    actionBar.addTab(
	            actionBar.newTab()
	                    .setText("Comp")
	                    .setTabListener(this)
	    );
	    
	    
	   

	  
	    
		
		//setTabsMaxWidth();

		// For each of the sections in the app, add a tab to the action bar.
		/*for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			//actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
			actionBar.addTab(actionBar.newTab().setIcon(R.drawable.icon_1).setTabListener(this));
		} */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		try {

			SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
			searchView.setOnQueryTextListener(queryListener);
		}catch(Exception e){

		}
		return super.onCreateOptionsMenu(menu);
	}

	final private android.support.v7.widget.SearchView.OnQueryTextListener queryListener = new android.support.v7.widget.SearchView.OnQueryTextListener() {

		@Override
		public boolean onQueryTextChange(String newText) {

			//直接丟給filter
			//MessageListMainFragment.this.adapter.getFilter().filter(newText);
			if (newText.length()>0) {
				Toast.makeText(getApplicationContext(), "Query Change " + newText + Integer.toString(newText.length()), Toast.LENGTH_SHORT).show();
			}
			return false;
		}

		@Override
		public boolean onQueryTextSubmit(String query) {
			Toast.makeText(getApplicationContext(),"Query Sub "+query,Toast.LENGTH_SHORT).show();
			Intent intent_search = new Intent(getApplicationContext(), SearchList.class);
			Bundle bundle_search = new Bundle();
			bundle_search.putString("search_key", query);
			intent_search.putExtras(bundle_search);
			startActivity(intent_search);

			return false;
		}
	};
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
	    if (drawerlistener.onOptionsItemSelected(item)) {
	            return true;
	     } 
		 
		 else{
	
		switch (item.getItemId()) {
		
		case R.id.starred_messages:
			Intent intent3 = new Intent(this, StarredList.class);
			startActivity(intent3);
			return true;
			
        case R.id.save_list:
            Intent intent = new Intent(this, Blacklist.class);
            Bundle bundle = new Bundle();
            bundle.putString("list_type", "offers");
            intent.putExtras(bundle);
    		startActivity(intent);
            
            return true;
        case R.id.black_list:
        	Intent intent2 = new Intent(this, Blacklist.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("list_type", "offers_blacklist");
            intent2.putExtras(bundle2);
    		startActivity(intent2);
            return true;
            
        case R.id.email_list:
        	
        	//ContextThemeWrapper ctw = new ContextThemeWrapper(this, AlertDialog.THEME_HOLO_DARK);
        	AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_HOLO_LIGHT);
        	
        	builder.setTitle("Please enter your email");
        	//builder.setIcon(R.drawable.ic_launcher);
        	builder.setMessage("OFFERS would send the save list and application methods to you, ALL in ONE EMAIL");
        	
            final EditText input = new EditText(this);
        	builder.setView(input);
        	
        	builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					RequestParams params = new RequestParams();
			        // set our JSON object
					List<String> joblist = new ArrayList<String>();
					List<Jobs> save_job = MainApplication.mydb.getAllOffers("offers");
					for(Jobs job : save_job){
						joblist.add(job.getObjectId());
					}
					String json = new Gson().toJson(save_job);
			        params.put("email", input.getText().toString());
			        params.put("body", json);
			        
			        params.put("time", new Date().getTime());
			        AsyncHttpClient client = new AsyncHttpClient();        
			        client.post(EMAIL_ENDPOINT, params, new JsonHttpResponseHandler(){
			        	
			        	@Override
			        	public void onSuccess(final int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
			        	//	super.onSuccess(statusCode, headers, responseString);
			        		
			        		runOnUiThread(new Runnable() {
			                    @Override
			                    public void run() {
			                        Toast.makeText(getApplicationContext(), "Success sent", Toast.LENGTH_LONG).show();
			                        Log.d("statusCode",Integer.toString(statusCode));
			                    }
			                });
			             }
			        	
			        	 @Override
			             public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, final String responseString, Throwable throwable) {
			            //	 super.onFailure(statusCode, headers, responseString, throwable);
			            	 runOnUiThread(new Runnable() {
				                    @Override
				                    public void run() {
				                    	Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();
				                    }
				                });
			             }
			        });
				}
			});
        	
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
        	
        	builder.create().show();
     //   	Intent intent3 = new Intent();
        	return true;
        	
        default:
            return super.onOptionsItemSelected(item);
		}
    }
		
	}

	
	
/*	private void setTabsMaxWidth() {
		   DisplayMetrics displaymetrics = new DisplayMetrics();
		   getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		   int screenWidth = displaymetrics.widthPixels;
		   final ActionBar actionBar = getSupportActionBar();
		   final View tabView = actionBar.getTabAt(0).getCustomView();
		   final View tabContainerView = (View) tabView.getParent();
		   final int tabPadding = tabContainerView.getPaddingLeft() + tabContainerView.getPaddingRight();
		   final int tabs = actionBar.getTabCount();
		   for(int i=0 ; i < tabs ; i++) {
		      View tab = actionBar.getTabAt(i).getCustomView();
		      TextView text1 = (TextView) tab.findViewById(R.id.text1);
		      text1.setMaxWidth(screenWidth/tabs-tabPadding-1);
		  }
		} */

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		ArrayList<Fragment> fragment_list;
		ArrayList<Fragment> minor_fragment_list;
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			fragment_list = new ArrayList<Fragment>();
		//	fragment_list.add(MainFragment.newInstance());
			//minor_fragment_list = new ArrayList<Fragment>();
			fragment_list.add(InternFragment.newInstance(actionBar));
			fragment_list.add(GradFragment.newInstance(actionBar));
			fragment_list.add(InternFragment.newInstance(actionBar));
			fragment_list.add(GradFragment.newInstance(actionBar));
			
			Log.v("FRAGMENT","ADDED");
		
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			//return PlaceholderFragment.newInstance(position + 1);
			
			return fragment_list.get(position);
			/*if (position==PAGE_MAIN)
				return MainFragment.newInstance(position+1);
			else
				return MainFragment.newInstance(position+1);*/
		}

		@Override
		public int getCount() {
			return fragment_list.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			/*Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null; */
			return fragment_list.get(position).getArguments().getString(Constants.ARG_SECTION_TITLE, "section_title");
		}
	}

	@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
		super.onPostResume();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		
		Log.d("onConfig","done");
		
		super.onConfigurationChanged(newConfig);
		drawerlistener.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		super.onPostCreate(savedInstanceState);
		drawerlistener.syncState();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	public void chatroom(){
		Intent intent = new Intent(this, Chatroom.class);
		startActivity(intent);
	}
	public void viewProfile(){
		Intent intent = new Intent(this, ViewProfile.class);
		startActivity(intent);
	}
	
	public void test(){
		Intent intent = new Intent(this, ScollGallery.class);
		startActivity(intent);
	}
	
}
