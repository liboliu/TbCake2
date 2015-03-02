package cn.com.popo.tbcake.tbCake.activity;

import android.app.Activity;

import java.util.ArrayList;

import cn.com.popo.tbcake.tbCake.business.task.TbCakeFutureTask;

public class BaseActivity extends Activity {
	@SuppressWarnings("rawtypes")
	private ArrayList<TbCakeFutureTask> taskList = new ArrayList<TbCakeFutureTask>();
	
	@SuppressWarnings("rawtypes")
	public void addTask(TbCakeFutureTask task){
		taskList.add(task);
	}

    //git test
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		for(TbCakeFutureTask<?> task: taskList){
			task.cancel(task.interuptedable);
		}
		super.onDestroy();
	}
}
