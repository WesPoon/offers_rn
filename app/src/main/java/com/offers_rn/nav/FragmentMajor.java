package com.offers_rn.nav;

import java.util.List;
import java.util.Vector;

import com.offers_rn.Constants;


import com.offers_rn.MainApplication;
import com.offers_rn.R;
import com.offers_rn.Singleton;
import com.offers_rn.offers.ExchangeFragment;
import com.offers_rn.offers.GradFragment;
import com.offers_rn.offers.InternFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentMajor extends Fragment implements OnTabChangeListener,OnPageChangeListener{
	
	
	private TabHost tabHost;
	private ViewPager viewPager;
	private MyFragmentPagerAdapter myViewPagerAdapter;
	private String[] tabname = {"MT", "GT"};
	int i = 0;
	View v;
	public static ActionBar actionBar;
	
	public ProgressBar mLoadingSub;
	
	
	public static FragmentMajor newInstance(ActionBar MainActionBar) {
		actionBar = MainActionBar;
		FragmentMajor fragment = new FragmentMajor();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}
	
	public FragmentMajor(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		Log.v("MajorFragment","OnCreate");
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		v = inflater.inflate(R.layout.tabs_viewpager, container, false);
		
		// We put TabHostView Pager here:
		i++;
		this.initializeTabHost(savedInstanceState);
		this.initializeViewPager();
		return v;
	}
	
	// fake content for tabhost
		class FakeContent implements TabContentFactory {
			private final Context mContext;

			public FakeContent(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumHeight(0);
				v.setMinimumWidth(0);
				return v;
			}
		}
	

	private void initializeTabHost(Bundle args) {
		


		tabHost = (TabHost) v.findViewById(android.R.id.tabhost);
		

		tabHost.setup();

		for (int i = 1; i <= 2; i++) {

			TabHost.TabSpec tabSpec;
			tabSpec = tabHost.newTabSpec("Tab" + i);
			tabSpec.setIndicator(tabname[i-1]);
			tabSpec.setContent(new FakeContent(getActivity()));
			tabHost.addTab(tabSpec);
			TextView x = (TextView) tabHost.getTabWidget().getChildAt(i-1).findViewById(android.R.id.title);
		    x.setTextSize(11);
		    
		}
		
		
	    
		tabHost.setOnTabChangedListener(this);
	}
	
	@SuppressWarnings("deprecation")
	private void initializeViewPager() {
		List<Fragment> fragments = new Vector<Fragment>();
		
		fragments.add(CommonFragment.newInstance(actionBar,Constants.MT));
		fragments.add(CommonFragment.newInstance(actionBar,Constants.GT));


		this.myViewPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragments);		
		this.viewPager = (ViewPager) v.findViewById(R.id.tab_viewPager);
		this.viewPager.setAdapter(this.myViewPagerAdapter);
		this.viewPager.setOffscreenPageLimit(2);
		this.viewPager.setOnPageChangeListener(this);

	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.d("MajorFragment","Destroy");
		super.onDestroy();

	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		
		Log.d("MajorFragment","DestroyView");
		super.onDestroyView();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Toast.makeText(getActivity(),"Fragment Major Resume",Toast.LENGTH_LONG).show();
		if(Singleton.getInstance().getInternListCount()==0){
			mLoadingSub = (ProgressBar) v.findViewById(R.id.loadingSpinner);
			mLoadingSub.setVisibility(View.VISIBLE);
			
		}
		
//		Toast.makeText(getActivity(), "Grad list number NOW: "+Integer.toString(MainApplication.GradJobslist.size()), Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		
		this.tabHost.setCurrentTab(position);
		
	}

	@Override
	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub
		int pos = this.tabHost.getCurrentTab();
		this.viewPager.setCurrentItem(pos);

		HorizontalScrollView hScrollView = (HorizontalScrollView) v
				.findViewById(R.id.hScrollView);
		View tabView = tabHost.getCurrentTabView();
		int scrollPos = tabView.getLeft()
				- (hScrollView.getWidth() - tabView.getWidth()) / 2;
		hScrollView.smoothScrollTo(scrollPos, 0);
	}
	

}
