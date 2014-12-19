package com.popo.tbcake.business;

import com.popo.tbcake.Bean.CakeBean;
import com.popo.tbcake.activity.BaseActivity;
import com.popo.tbcake.dto.BaseDto;
import com.popo.tbcake.dto.CakeDto;
import com.popo.tbcake.taskDispather.TaskDispatcher;

import java.util.concurrent.Future;

/**
 * Created by popo on 14-12-11.
 */
public interface Business {

    public Future<CakeDto> getCake(BaseActivity activity,int page,int pageSzie ,TaskDispatcher.TaskDispatcherCallBack<CakeDto> listener) throws Exception;
    public Future<BaseDto> selectCake(BaseActivity activity,CakeBean cake,TaskDispatcher.TaskDispatcherCallBack callBack) throws Exception;
}
