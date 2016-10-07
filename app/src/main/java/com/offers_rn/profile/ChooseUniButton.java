package com.offers_rn.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;
import com.offers_rn.uielement.NamedButton;
import com.offers_rn.R;

/**
 * Created by user on 4/8/2016.
 */
class ChooseUniButton extends NamedButton implements View.OnClickListener {
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Toast.makeText(this.getContext(), "Your University is "+getName()+".", Toast.LENGTH_LONG).show();
	    SharedPreferences settings = this.getContext().getSharedPreferences(this.getContext().getString(R.string.app_name), 0);
	    SharedPreferences.Editor PE = settings.edit();
	    PE.putString("university", getName());
	    PE.commit();
	    ChooseUniActivity cua = (ChooseUniActivity) this.getContext();
	    cua.gotoNext();

	}

	public ChooseUniButton(Context context, AttributeSet attrs,
						   int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
	}

	public ChooseUniButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public ChooseUniButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ChooseUniButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void init(AttributeSet attrs){
		super.init(attrs);
		setOnClickListener(this);
	}




}
