package com.offers_rn.uielement;

import com.offers_rn.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class NamedButton extends ImageButton {
	String name = "";

	@SuppressLint("NewApi")
	public NamedButton(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
		init(attrs);
	}

	public NamedButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init(attrs);
	}

	public NamedButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(attrs);
	}

	public NamedButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		// init();
	}

	public void init(AttributeSet attrs) {
		TypedArray arr = getContext().obtainStyledAttributes(attrs,
				R.styleable.NamedButton);
		String name_tmp = arr.getString((R.styleable.NamedButton_name));
		if (name_tmp != null) {
			setName(name_tmp);
		}
		arr.recycle();		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}