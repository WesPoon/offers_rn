package com.offers_rn.nav;

import java.util.ArrayList;
import java.util.List;

import com.offers_rn.R;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavListAdapter extends ArrayAdapter<NavItem> {
	
	Context context;
	int resLayout;
	List<NavItem> listNavItems;
	static private int selected;

	public NavListAdapter(Context context, int resource, List<NavItem> listNavItems) {
		super(context, resource, listNavItems);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.resLayout = resource;
		this.listNavItems = listNavItems;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		// TODO Auto-generated method stub
		
		View v=  View.inflate(context,resLayout,null);
		TextView tvTitle = (TextView) v.findViewById(R.id.title);
		TextView tvSubtitle = (TextView) v.findViewById(R.id.subtitle);
		ImageView navIcon = (ImageView) v.findViewById(R.id.nav_icon);
		
		final NavItem navItem = listNavItems.get(position);
		
		tvTitle.setText(navItem.getTitle());
		tvSubtitle.setText(navItem.getSubtitle());
		navIcon.setImageResource(navItem.getResIcon());
		
/*		v.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(navItem.getTitle(),"is called");
			}
			
			
			
			
		}); */
		
		return v;
	}

	@Override
	public int getPosition(NavItem item) {
		// TODO Auto-generated method stub
		return super.getPosition(item);
	}
	
	
	

}  
