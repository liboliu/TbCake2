package cn.com.popo.tbcake.tbCake.business.task;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class TbCakeFutureTask<V> extends FutureTask<V> {
	
	public boolean interuptedable = false;

	public TbCakeFutureTask(Callable<V> callable) {
		super(callable);
		// TODO Auto-generated constructor stub
	}

	public TbCakeFutureTask(Runnable runnable, V result) {
		super(runnable, result);
		// TODO Auto-generated constructor stub
	}
	
}
