package com.popo.tbcake.taskDispather;

import com.popo.tbcake.activity.BaseActivity;
import com.popo.tbcake.business.task.BTask;

import java.util.concurrent.Future;

/**
 * 任务处理接口
 * @author popoi
 *
 */
public interface TaskDispatcher {
	/**
	 * 提交任务
	 * @param <T>
	 * @param task
	 * @param activity 任务所在的activity 如果不是在activity中执行可以传null
	 * @return
	 * @throws Exception
	 */
	public <T> Future<T> submit(BTask<T> task, BaseActivity activity) throws Exception;
	
	/**
	 * 异步执行回调接口
	 * @author popo
	 *
	 */
	public static interface TaskDispatcherCallBack<T>{
		public void  onPreExecute();
		public void onSuccess(T t);
        public void onFail(String message);
		
	}

}

