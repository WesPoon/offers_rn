package com.offers_rn.offers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v4.view.WindowCompat;
import android.view.Window;

public class AsynTaskLoadData extends AsyncTask<String,Integer,String> {
	
	private final TaskListener listener;
	private Context context;
	ProgressDialog progressDialog;
	 
    public  AsynTaskLoadData(TaskListener listener,Context context) {
        this.listener = listener;
        this.context = context;
        
    }

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		listener.loadData();
		return null;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		listener.onTaskStarted();
/*		progressDialog = new ProgressDialog(this.context);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Loading Data");
		progressDialog.show(); */
		
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		listener.onTaskFinished(result);
	//	progressDialog.dismiss();
	}
	
	

}

