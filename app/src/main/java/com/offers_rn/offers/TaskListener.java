package com.offers_rn.offers;

import java.util.List;

public interface TaskListener {
	
	void onTaskStarted(); 
    void onTaskFinished(String result);
    void loadData();

}
