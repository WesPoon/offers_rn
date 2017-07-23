package com.offers_rn.offers;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.bhargavms.dotloader.DotLoader;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;
import com.offers_rn.MainApplication;
import com.offers_rn.R;
import com.offers_rn.parseobject.GradJobs;
import com.offers_rn.parseobject.Jobs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.CardView;

public class ContactAdapter extends
		RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private String type;
    List<Jobs> Jobslist;
	
	public ContactAdapter(String type, List<Jobs> access) {
		this.Jobslist = access;
		this.type = type;
	}
	
	
	public void remove(int position){
	  	
		Jobslist.remove(position);
		notifyItemRemoved(position);
		notifyItemRangeChanged(position, getItemCount());
		
		
	}
    
	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return Jobslist.size();	
	}

	@Override
	public void onBindViewHolder(final ContactViewHolder contactViewHolder, final int i) {
		
		// TODO Auto-generated method stub
		
	    final Jobs ci = Jobslist.get(i);

	    contactViewHolder.setType(type);
	    contactViewHolder.setObjectID(ci.getObjectId());
	    contactViewHolder.setViewCount(ci.getViewCount());
	    contactViewHolder.setDeadline(ci.getDeadline());
		contactViewHolder.setApplicationLink(ci.getApplicationLink());
		contactViewHolder.vCompany.setText(ci.getCompany());
		contactViewHolder.vJobTitle.setText(ci.getJobTitle());
		contactViewHolder.vViewCount.setText(Integer.toString(ci.getViewCount())+" Views");
		
		contactViewHolder.click=MainApplication.mydb.checkExist(ci.getObjectId());
		
		if(contactViewHolder.click==true){
		contactViewHolder.save.setBackgroundResource(R.drawable.cardview_savedtick);
		}else{
		contactViewHolder.save.setBackgroundResource(R.drawable.cardview_savebutton);
		}
		
		SimpleDateFormat p = new SimpleDateFormat("dd-MM-yyyy");
		final String formatDate = p.format(ci.getDeadline());
		
		
		contactViewHolder.vDeadline.setText(formatDate);
		
		
		
		Log.v("CreatedCard", ""+ci.getObjectId()+";"+ci.getCompany()+Integer.toString(i));
			 
			 ci.getParseObject("Company_Symbol").fetchIfNeededInBackground( new GetCallback<ParseObject>() {
			     public void done(ParseObject arg0, ParseException e) {
			         if (e == null) {
			        	 
			        	 Picasso.with(contactViewHolder.vCompany.getContext())
		        		  .load(arg0.getParseFile("Company_Symbol").getUrl())
		        		  .resize(100, 100)
		        		  .centerInside()
		        		  .into(contactViewHolder.CompanyLogo);

						 contactViewHolder.dotloader.setVisibility(View.GONE);
						 contactViewHolder.dotloader.clearAnimation();

			            
			     /*   	try {
			        		
			        		
			        		BitmapFactory.Options options = new BitmapFactory.Options();
			                options.inSampleSize = 3;
			                options.outWidth = 70;
			                options.inMutable =true;
			        	
							byte[] data=arg0.getParseFile("Company_Symbol").getData();
							
							
							
							Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length, options);
							
							while(bmp.getHeight()>210 && bmp.getWidth()>210){
							bmp = Bitmap.createScaledBitmap(bmp,bmp.getWidth()/2 , bmp.getHeight()/2, true);
							}
							
							Log.v("BMPHeight"+" "+ci.getCompany(), Integer.toString(bmp.getHeight()));
							Log.v("BMPWidth"+" "+ci.getCompany(), Integer.toString(bmp.getWidth()));
							Log.v("DataLength"+" "+ci.getCompany(), Integer.toString(data.length));
							Log.v("BMPLength"+" "+ci.getCompany(), Integer.toString(bmp.getByteCount()));
							
							contactViewHolder.CompanyLogo.setImageBitmap(bmp);
							
							
						} 
			        	
			       	   catch (ParseException e1) {
							// TODO Auto-generated catch block
							Log.v("Wesley", "is handsome");
							e1.printStackTrace();
						}
			        	
			        	catch (NullPointerException e2){
			        		
			        		e2.printStackTrace();
			        	}  */
			        	
			        	 
			         } else {
			             
			         }
			     }
			 });  
			 
			 System.gc();
			
    /*     try{
        	 
        	 byte[] data=ci.getParseFile("Company_Symbol").getData();
				Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
				Log.v("DataLength", Integer.toString(data.length));
				contactViewHolder.CompanyLogo.setImageBitmap(bmp);
				Log.v("BMPLength", Integer.toString(bmp.getByteCount()));
         
         
         }
			 
		catch (ParseException e) {
		Log.d("ca", "fail build image"); }
		
		catch (NullPointerException e) {
		Log.d("ca", "null point");
		}  */
			 
			 contactViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
			        @Override
			        public boolean onLongClick(View v) {
			          Toast.makeText(v.getContext(), "Long Click Test "+Integer.toString(i), Toast.LENGTH_LONG).show();
			          v.showContextMenu();
			          //remove(i);
			          return true;
			        }
			      });
			 
			 contactViewHolder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
				
				@Override
				public void onCreateContextMenu(ContextMenu menu, final View v,
						ContextMenuInfo menuInfo) {
					// TODO Auto-generated method stub
					menu.add(0, v.getId(), 0, "Delete & Blacklist this offer?")
					.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
		                @Override
		                public boolean onMenuItemClick(MenuItem item){
		                	if(MainApplication.mydb.insertOffers("offers_blacklist",type, Jobslist.get(i).getObjectId(), formatDate, Jobslist.get(i).getCompany(), Jobslist.get(i).getJobTitle(),Jobslist.get(i).getApplicationLink()))
		            		{
		            	     Toast.makeText(contactViewHolder.itemView.getContext(), Jobslist.get(i).getJobTitle()+" is added to Black List", Toast.LENGTH_SHORT).show();
		            	     remove(i);
		            	//Toast.makeText(v.getContext(), getJobTitle()+" is saved to DB", Toast.LENGTH_SHORT).show();}
		            		}
                            
		                    return true;
		                }
		            });//groupId, itemId, order, title
					
				}
				
			});
	     
	
	}
	
	public static int calculateSize(int height, int width){
		
		
		return 1;
		
	}
	
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
    }

    return inSampleSize;
}

	@Override
	public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		// TODO Auto-generated method stub
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.offer_card, viewGroup, false);
		
		Log.v("onCreateViewHolder","called");
		
		return new ContactViewHolder(itemView);
		

	}

	public static class ContactViewHolder extends RecyclerView.ViewHolder implements OnClickListener,OnTouchListener{
		protected TextView vCompany;
		protected TextView vJobTitle;
		protected TextView vEmail;
		protected TextView vTitle;
		protected TextView vDeadline;
		protected TextView vViewCount;
		protected ImageView CompanyLogo;
		protected String objectID;
        protected String type;
		protected String App_Link;
        protected ImageButton save;
        protected int ViewCount;
		protected Jobs JobObject;
		protected DotLoader dotloader;
        boolean click;
		
		
		public ContactViewHolder(View v) {
			super(v);
			vCompany = (TextView) v.findViewById(R.id.offer_card_context);
			vJobTitle = (TextView) v.findViewById(R.id.offer_card_title);
			CompanyLogo = (ImageView) v.findViewById(R.id.offer_card_icon);
			vDeadline = (TextView) v.findViewById(R.id.offer_card_deadline);
			vViewCount = (TextView) v.findViewById(R.id.offer_card_view);
			dotloader = (DotLoader) v.findViewById(R.id.offer_card_dotloader);
			save = (ImageButton) v.findViewById(R.id.cardview_savebutton);

		    save.setOnClickListener(new OnClickListener() {
		    	

		        @Override
		        public void onClick(View v) {
		        	
		        	if(click==false){
		                
		                save.setBackgroundResource(R.drawable.cardview_savedtick);
		                
		                if(MainApplication.mydb.insertOffers("offers",type, getObjectID(), getDeadline(), getCompany(), getJobTitle(),getApplicationLink()))
		                		{
		                	Snackbar.make(v, getJobTitle()+" is added to Save List", Snackbar.LENGTH_SHORT).show();
		                	//Toast.makeText(v.getContext(), getJobTitle()+" is saved to DB", Toast.LENGTH_SHORT).show();}
		                		}
		                
		                click=true;
		        	}
		        	else{
		        	     if(click==true)
		        	     {
		        		     save.setBackgroundResource(R.drawable.cardview_savebutton); 		     
		        		     if(MainApplication.mydb.deleteOffers("offers",getObjectID()))
		                		{Snackbar.make(v, getJobTitle()+" is deleted from Save List", Snackbar.LENGTH_SHORT).show();}
		        		     
		        		     click=false;
		        	     }
		        	}
		        }
		    });
			// vEmail = (TextView) v.findViewById(R.id.txtEmail);
			// vTitle = (TextView) v.findViewById(R.id.title);
			
			v.setOnClickListener(this);
	//		v.setOnCreateContextMenuListener(this);		
	//		v.setOnLongClickListener(this);
	//		if(Integer.valueOf(android.os.Build.VERSION.SDK)<=20){
    // 		v.setOnTouchListener((OnTouchListener) this);
	//		}
		}
		
		@SuppressWarnings("finally")
		private byte[] getBytes(){
			
			Bitmap bmp=null;
			byte[] byteArray=null;
			try{
			    bmp=((BitmapDrawable)this.CompanyLogo.getDrawable()).getBitmap();
			
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byteArray = stream.toByteArray();		
			return byteArray;
			}
			finally{
				
				return byteArray;
			}
			
			
			
			
		}
		
		public void setType(String type){
			
			this.type = type;
			
		}

		
		public void setObjectID(String objectID){
			
			this.objectID = objectID ; 
		}
		
		public void setViewCount(int number){
			
			this.ViewCount = number;
		}
		
		private String getObjectID(){
			return this.objectID;
		}
		
		private String getCompany(){
			return this.vCompany.getText().toString();
		}
		
		private String getJobTitle(){
			return this.vJobTitle.getText().toString();
		}
		
		private String getDeadline(){
			
			return this.vDeadline.getText().toString();
		}
		
		public void setDeadline(Date date){
			
		}

		public void setApplicationLink(String link){
			this.App_Link = link;
		}

		public String getApplicationLink(){
			return this.App_Link;
		}

		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			this.vViewCount.setText(Integer.toString(ViewCount+1)+" Views");
			
			Thread UpViewCount = new Thread(){
				
				public void run(){
					
					try{
						
						Log.d("type",type);
						ParseQuery<ParseObject> query = ParseQuery.getQuery(type);
					    query.getInBackground(getObjectID(), new GetCallback<ParseObject>(){

							@Override
							public void done(ParseObject Jobs, ParseException e) {
								// TODO Auto-generated method stub
							if(e==null){
								
								Log.e(Jobs.getString("Company")+"ViewCount",Integer.toString(Jobs.getInt("ViewCount")));
								Jobs.put("ViewCount",Jobs.getInt("ViewCount")+1);
						//		Jobs.put("ViewCount", 100);
								Jobs.saveInBackground();
								
							}
							
							else{
								Log.d(getObjectID(),"nth found");
								
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
			
		    UpViewCount.start();
		    
			
			Intent intent = new Intent((Context) arg0.getContext(), InternDetailActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			
			intent.putExtra("JobObjectID", getObjectID());
			intent.putExtra("Company",getCompany());
			intent.putExtra("JobTitle",getJobTitle());
			intent.putExtra("Deadline", getDeadline());

			intent.putExtra("Type", type);
			
			System.gc();
			
			((Activity) arg0.getContext()).startActivity(intent);
		}
		
		@Override
		public boolean onTouch(View arg0, MotionEvent event) {
			
			
			if(event.getAction()==MotionEvent.ACTION_MOVE){
				
				Log.d("Moved", "View");
				CardView cv = (CardView)arg0;
	            cv.setCardBackgroundColor(arg0.getContext().getResources().getColor(R.color.White));
				
				return false;
			}
			
            if(event.getAction()==MotionEvent.ACTION_SCROLL){
				
				Log.d("Scrolled", "View");
				CardView cv = (CardView)arg0;
	            cv.setCardBackgroundColor(arg0.getContext().getResources().getColor(R.color.White));
				
				return false;
			} 
			
			
			
			if (event.getAction() == MotionEvent.ACTION_DOWN){
				
                Log.d("Pressed", "Button pressed");
                
                CardView cv = (CardView)arg0;
                cv.setCardBackgroundColor(arg0.getContext().getResources().getColor(R.color.Pink));
                return true;
			}
			
            else if(event.getAction() == MotionEvent.ACTION_UP){
             
             Log.d("Released", "Button released");
             CardView cv = (CardView)arg0;
             cv.setCardBackgroundColor(arg0.getContext().getResources().getColor(R.color.White));
             onClick(arg0);
             return false;
            }
			
			return false;
        
		}
		
	}
}
