package com.offers_rn.profile;

import java.util.Set;

import com.offers_rn.R;

import android.content.Context;

public class Profile {
	String university = "/";
	String major = "/";
	String username = "/";
	
	int gpa = UNDEFINED;
	
	public static int CLASSIFIED = 4;
	public static final short NO_OF_GPA_CATEGORY = 3;
	public static int ABOVE_3_5 = 2;
	public static int ABOVE_3 = 1;
	public static int BELOW_3 = 0;
	public static int UNDEFINED = -1;
	
	String industry1 = "/";
	String industry2 = "/";
	public static short FIRST_INDUSTRY = 0;
	public static short SECOND_INDUSTRY = 1;
	
	int career_style = UNDEFINED;
	
	public static short NO_OF_STYLES = 5;
	public static short CENTRAL_PPL = 4;
	public static short STABLE_PPL = 3;
	public static short CREATIVE = 2;
	public static short TECHNICAL = 1;
	public static short HEA_DO = 0;
	
	public Profile(){
		
	}
	
	public Profile(String university,String username, String major, int gpa, String industry1, String industry2, int career_style) throws Exception{
		setUniversity(university);
		setUserName(username);
		setMajor(major);
		setGPA(gpa);
		setIndustries(industry1, industry2);
		setCareerStyle(career_style);
	}
	
	public Profile setUniversity(String university){
		this.university = university;
		return this;
	}
	public String getUniversity(){
		return university;
	}
	public Profile setUserName(String username){
		this.username = username;
		return this;
	}
	public String getUserName(){
		return username;
	}
	public Profile setMajor(String major){
		this.major = major;
		return this;
	}
	public String getMajor(){
		return major;
	}
	public Profile setGPA(int gpa){
		//if ( gpa>=0 && gpa<NO_OF_STYLES)
			this.gpa = (short) gpa;
		//else
			//return null;
		return this;
	}
	public int getGPA(){
		return gpa;
	}
	public static String ConvertGPAToString(int gpa, Context context){
		if (gpa==CLASSIFIED)
			return context.getResources().getString(R.string.profile_gpa_classified);
		if (gpa==ABOVE_3_5)
			return context.getResources().getString(R.string.profile_gpa_above_3_5);
		if (gpa==ABOVE_3)
			return context.getResources().getString(R.string.profile_gpa_above_3);
		else
			return context.getResources().getString(R.string.profile_gpa_below_3);
	}
	public Profile setIndustries(String industry1, String industry2){
		this.industry1 = industry1;
		this.industry2 = industry2;
		return this;
	}
	public String[] getIndustries(){
		String[] industries = new String[2];
		industries[0] = industry1;
		industries[1] = industry2;
		return industries;
	}
	public Profile setCareerStyle(int career_style){
		if ( career_style>=0 && gpa<NO_OF_STYLES)
			this.career_style = (short) career_style;
		else
			return null;
		return this;
	}
	public int getCareerStyle(){
		return career_style;
	}
	public static String ConvertCareerStyleToString(int career_style, Context context){
		switch(career_style){
		case 4:
			return context.getResources().getString(R.string.profile_career_style_central_ppl);
		case 3:
			return context.getResources().getString(R.string.profile_career_style_stable_ppl);
		case 2:
			return context.getResources().getString(R.string.profile_career_style_creative);
		case 1:
			return context.getResources().getString(R.string.profile_career_style_technical);
		
		}
		return "/";
	}
	public static String ConvertCareerStyleToString(int career_style){
		switch(career_style){
		case 4:
			return"profile_career_style_central_ppl";
		}
		return "/";
	}
}
