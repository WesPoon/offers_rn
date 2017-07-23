package com.offers_rn.menulist;

import java.text.SimpleDateFormat;
import java.util.List;

import com.offers_rn.MainApplication;
import com.offers_rn.R;
import com.offers_rn.offers.InternDetailActivity;
import com.offers_rn.parseobject.Jobs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.TextView;
import android.widget.Toast;



public class SaveAndBlackListAdapter extends RecyclerView.Adapter<SaveAndBlackListAdapter.SaveViewHolder> {

	
	List<Jobs> Savelist;
	String list_type;
	
	public SaveAndBlackListAdapter(List<Jobs> access, String list_type){
		this.Savelist = access ; 
		this.list_type = list_type;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return Savelist.size();
	}
	
    public void remove(int position){
	  	
	    Savelist.remove(position);
		notifyItemRemoved(position);
		notifyItemRangeChanged(position, getItemCount());
		
		
	}

	@Override
	public SaveViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
		// TODO Auto-generated method stub
		
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.save_card, viewGroup, false);
		
		return new SaveViewHolder(itemView);
	}
	
	

	@Override
	public void onBindViewHolder(final SaveViewHolder saveView, final int position) {
		// TODO Auto-generated method stub
		final Jobs card= Savelist.get(position);
		saveView.vCompany.setText(card.getCompany());
		saveView.vJobTitle.setText(card.getJobTitle());
		if(card.getDeadline()!=null){
			SimpleDateFormat p = new SimpleDateFormat("dd-MM-yyyy");
			String formatDate = p.format(card.getDeadline());
			saveView.vDeadline.setText("- "+formatDate);
		}
		
		saveView.itemView.setOnLongClickListener(new View.OnLongClickListener() {
			        @Override
			        public boolean onLongClick(View v) {
			          Toast.makeText(v.getContext(), "Long Click Test "+Integer.toString(position), Toast.LENGTH_LONG).show();
			          v.showContextMenu();
			          //remove(i);
			          return true;
			        }
			      });

		saveView.itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent((Context) v.getContext(), InternDetailActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

						intent.putExtra("JobObjectID", card.getObjectId());
						intent.putExtra("Company",card.getCompany());
						intent.putExtra("JobTitle",card.getJobTitle());
						intent.putExtra("Deadline", card.getDeadline());
						intent.putExtra("Type",card.getType());
						((Activity) v.getContext()).startActivity(intent);
					}
				 });
		
		saveView.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu menu, final View v,
					ContextMenuInfo menuInfo) {
				// TODO Auto-generated method stub
				menu.add(0, v.getId(), 0, "offload from the list ?")
				.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
	                @Override
	                public boolean onMenuItemClick(MenuItem item){
	                	if(MainApplication.mydb.deleteOffers(list_type, Savelist.get(position).getObjectId()))
						{
	            	      remove(position);
	            	//Toast.makeText(v.getContext(), getJobTitle()+" is saved to DB", Toast.LENGTH_SHORT).show();}
	            		}
                        
	                    return true;
	                }
	            });//groupId, itemId, order, title
				
			}
			
		});
		
		
	}
	
	public static class SaveViewHolder extends RecyclerView.ViewHolder {      

		
		protected TextView vCompany;
		protected TextView vJobTitle;
		protected TextView vEmail;
		protected TextView vTitle;
		protected TextView vDeadline;
		protected TextView vViewCount;
		
 
        SaveViewHolder(View v) {
            super(v);
            
            vCompany = (TextView) v.findViewById(R.id.offer_card_context);
			vJobTitle = (TextView) v.findViewById(R.id.offer_card_title);
			vDeadline = (TextView) v.findViewById(R.id.offer_card_deadline);

        }
	}
		
}		
			
			


		
	
	
