package com.popo.tbcake.business.task;

import com.android.volley.Request;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by popo on 14-12-18.
 */
public class TbCakeVolleyRequstFutureTask<V> extends FutureTask<V> {
    Request<V> request;

    public TbCakeVolleyRequstFutureTask(Callable<V> callable) {
        super(callable);
    }

    public TbCakeVolleyRequstFutureTask(Request<V> request){
        super(null);
        this.request=request;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        request.cancel();
        return super.cancel(mayInterruptIfRunning);
    }


}
