package com.offers_rn;

import com.offers_rn.profile.Profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static MainFragment newInstance(int sectionNumber) {
		MainFragment fragment = new MainFragment();
		Bundle args = new Bundle();
		args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
		args.putString(Constants.ARG_SECTION_TITLE, "Main");
		fragment.setArguments(args);
		return fragment;
	}

	public static MainFragment newInstance() {
		MainFragment fragment = new MainFragment();
		Bundle args = new Bundle();
		// args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		args.putString(Constants.ARG_SECTION_TITLE, "Main");
		fragment.setArguments(args);
		return fragment;
	}

	public MainFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		tv1 = (TextView) rootView.findViewById(R.id.tv1);
		tv1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				deleteSP();
				startBuildProfile();
			}
		});
		if (!LoadSP()) {
			//startBuildProfile();
		}
		return rootView;
	}

	TextView tv1;
	Profile profile = new Profile();

	public boolean LoadSP() {
		SharedPreferences settings = this.getActivity().getSharedPreferences(getString(R.string.app_name), 0);
		// if the settings exists, tv1 shows the chosen uni, press again to
		// delete the shared preference
		if (!settings.contains("university"))
			return false;
		if (!settings.contains("username"))
			return false;
		if (!settings.contains("major"))
			return false;
		if (!settings.contains("gpa"))
			return false;
		if (!settings.contains("industry1"))
			return false;
		if (!settings.contains("industry2"))
			return false;
		if (!settings.contains("career_style"))
			return false;
		profile.setUniversity(settings.getString("university", "/"));
		profile.setUserName(settings.getString("username", "/"));
		profile.setMajor(settings.getString("major", "/"));
		profile.setGPA(settings.getInt("gpa", Profile.UNDEFINED));
		profile.setIndustries(settings.getString("industry1", "/"), settings.getString("industry2", "/"));
		profile.setCareerStyle(settings.getInt("career_style", Profile.UNDEFINED));
		tv1.setText(profile.getUniversity() + "\n"
				+ profile.getUserName() + "\n"
				+ profile.getMajor() + "\n"
				+ Profile.ConvertGPAToString(profile.getGPA(), this.getActivity()) + "\n"
				+ profile.getIndustries()[Profile.FIRST_INDUSTRY] + " | "
				+ profile.getIndustries()[Profile.SECOND_INDUSTRY] + "\n"
				+ Profile.ConvertCareerStyleToString(profile.getCareerStyle(), this.getActivity())
				+ "\nPress here to start config the profile again.");
		return true;
	}

	public void startBuildProfile() {
		// Toast.makeText(getBaseContext(), "No saved uni",
		// Toast.LENGTH_LONG).show();
		// Intent intent = new Intent(this, ChooseUniActivity.class);
		// startActivity(intent);
		//FragmentManager manager = this.getActivity().getSupportFragmentManager();
		//ChooseUniFragment chooseuni = new ChooseUniFragment();
		//manager.putFragment(null, "", chooseuni);
		FragmentTransaction fragmentTransaction = this.getActivity().getSupportFragmentManager().beginTransaction();
		//fragmentTransaction.replace(R.id.fragment_profile_choose_uni, chooseuni, "");
		fragmentTransaction.addToBackStack("");
		fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		fragmentTransaction.commit();
		//this.getView()
	}

	// for playing, boss
	public void deleteSP() {
		SharedPreferences preferences = this.getActivity().getSharedPreferences(getString(R.string.app_name), 0);
		preferences.edit().clear().commit();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}