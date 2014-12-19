package com.popo.tbcake.business.impl;

import android.content.ContentValues;
import android.content.Context;

import com.popo.tbcake.Bean.CakeBean;
import com.popo.tbcake.activity.BaseActivity;
import com.popo.tbcake.business.Business;
import com.popo.tbcake.business.task.BTask;
import com.popo.tbcake.dto.BaseDto;
import com.popo.tbcake.dto.CakeDto;
import com.popo.tbcake.taskDispather.TaskDispatcher;

import java.util.concurrent.Future;

/**
 * Created by popo on 14-12-18.
 */
public class BusinessImpl implements Business {

    public  ContentValues getBaseContextValues(Context context){
        ContentValues c = new ContentValues();

        return  c;
    }

    TaskDispatcher taskDispatcher;

    @Override
    public Future<CakeDto> getCake(BaseActivity activity,int page, int pageSzie, TaskDispatcher.TaskDispatcherCallBack<CakeDto> listener) throws Exception {
        ContentValues c = getBaseContextValues(activity);
        c.put("page",page);
        c.put("pagesize",pageSzie  );
        BTask<CakeDto> bTask = BTask.newBuilder(CakeDto.class)
                .setCallback(listener)
                .setUrl("")
                .setResponseType(BTask.RESPONSE_TYPE.JSON)
                .setResultType(BTask.RESULT_TYPE.OBJECT)
                .setContValues(c)
                .create();

        return taskDispatcher.submit(bTask,activity);
    }

    @Override
    public Future<BaseDto> selectCake(BaseActivity activity, CakeBean cake, TaskDispatcher.TaskDispatcherCallBack callBack) throws Exception {
        return null;
    }
}
