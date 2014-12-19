package com.popo.tbcake.taskDispather.impl;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.OkhttpStack;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.TbCakeVolleyRequest;
import com.android.volley.toolbox.Volley;
import com.popo.tbcake.activity.BaseActivity;
import com.popo.tbcake.business.task.BTask;
import com.popo.tbcake.taskDispather.TaskDispatcher;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.Future;

/**
 * Created by popo on 14-12-18.
 */
public class VolleyTaskDispather implements TaskDispatcher {
    static RequestQueue requestQueue;
    static OkHttpClient okHttpClient = new OkHttpClient();
    public VolleyTaskDispather(Context context) {

        requestQueue = Volley.newRequestQueue(context,new OkhttpStack(okHttpClient));
    }

    @Override
    public <T> Future<T> submit(BTask<T> task, BaseActivity activity) throws Exception {

        Request<T> request = buildVolleyRequest(task   );

        requestQueue.add(request);

        RequestFuture<T> requestFuture  = RequestFuture.newFuture();
        requestFuture.setRequest(request);
        return requestFuture;
    }

    private <T> Request<T> buildVolleyRequest(final BTask<T> task) {

        BTask.RESULT_TYPE result_type = task.getResult_type();
        BTask.RESPONSE_TYPE response_type = task.getRESPONSE_TYPE();

        BTask.TYPE method_type = task.getHttpMehodeType();
        int method =Request.Method.GET;
        if(method_type == BTask.TYPE.GET){
            method =Request.Method.GET;
        }else if(method_type == BTask.TYPE.POST){
            method =Request.Method.POST;
        }

       final  TaskDispatcherCallBack callback = task.getCallBack();
        Request request = null;

        request = new TbCakeVolleyRequest(task);
        return request;
    }

}
