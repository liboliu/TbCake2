package com.popo.tbcake.activity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.concurrent.Future;

public class BaseActivity extends Activity {
	@SuppressWarnings("rawtypes")
	private ArrayList<Future> taskList = new ArrayList<Future>();
	
	@SuppressWarnings("rawtypes")
	public void addTask(Future task){
		taskList.add(task);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		for(Future task: taskList){
			task.cancel(false);
		}
		super.onDestroy();
	}
}
