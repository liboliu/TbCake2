package com.popo.tbcake.taskDispather.impl;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.popo.tbcake.activity.BaseActivity;
import com.popo.tbcake.business.task.BTask;
import com.popo.tbcake.business.task.BTask.RESPONSE_TYPE;
import com.popo.tbcake.business.task.BTask.RESULT_TYPE;
import com.popo.tbcake.business.task.BTask.TYPE;
import com.popo.tbcake.http.Http;
import com.popo.tbcake.http.HttpFactory;
import com.popo.tbcake.taskDispather.TaskDispatcher;

import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncTaskDispather implements TaskDispatcher {

    private static final int COREPOOLSIZE = 4;

    private static final int MAXIMUMPOOLSIZE = 16;

    private static final long KEEPALIVETIME = 5;

    private static final TimeUnit UNIT = TimeUnit.SECONDS;

    private BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<Runnable>();


    private static final int SUCC = 1;
    private static final int FAIL = 0;

    static Handler h = new Handler(Looper.getMainLooper()) {
        @SuppressWarnings({"unchecked", "rawtypes"})
        public void handleMessage(Message msg) {
            Object[] o = (Object[]) msg.obj;
            switch (msg.what) {

                case SUCC:

                    ((BTask) o[1]).getCallBack().onSuccess(o[0]);
                    break;
                case FAIL:
                    ((BTask) o[1]).getCallBack().onSuccess(o[0]);
                    break;
                default:
                    break;
            }
        }

        ;
    };

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    };

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            COREPOOLSIZE, MAXIMUMPOOLSIZE, KEEPALIVETIME, UNIT, workQueue,
            sThreadFactory);

    // 我们这里可以提供更丰富多彩的 提交策略
    @Override
    public <T> Future<T> submit(final BTask<T> task,
                                BaseActivity activity) throws Exception {
        // TODO Auto-generated method stub

        FutureTask<T> furFutureTask = buildFutureTask(task);
        activity.addTask(furFutureTask);
        threadPoolExecutor.execute(furFutureTask);
        return furFutureTask;
    }

    // 生产
    private <T> FutureTask<T> buildFutureTask(final BTask<T> task) {
        FutureTask<T> furFutureTask = new FutureTask<T>(
                new Runnable() {

                    @Override
                    public void run() {
                        Http http = task.httpTool;
                        if(http==null)
                            http  = HttpFactory.getOkHttp();
                        // TODO Auto-generated method stub
                        TYPE type = task.getHttpMehodeType();
                        RESULT_TYPE result_type = task.getResult_type();

                        InputStream in = null;
                        Message message = h.obtainMessage();
                        Object result = null;
                        // GET请求
                        try {
                            if (type == TYPE.GET) {

                                in = http.doGetForInputStream(task.getUrl());
                            } else {
                                in = http.doPostForInputStream(task.getUrl(),
                                        task.getBody());
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            message.what = FAIL;
                            message.obj = new Object[]{e, task};
                            h.sendMessage(message);
                            return;
                        }
                        // 返回的是流
                        try {

                            if (task.getRESPONSE_TYPE() == RESPONSE_TYPE.STREAM) {

                                if (result_type == RESULT_TYPE.STRING) {
                                    result = task.getParser().parse(in);
                                } else if (result_type == RESULT_TYPE.INPUTSTEAM) {
                                    result = in;
                                } else if (result_type == RESULT_TYPE.OBJECT) {
                                    result = task.getParser().parse(in,
                                            task.getClassType());

                                    // 下载文件
                                } else if (result_type == RESULT_TYPE.FILE) {
                                    result = task.getParser().parseTofile(in,
                                            task.getFileTo());
                                }

                                message.what = SUCC;
                                message.obj = new Object[]{result, task};
                                h.sendMessage(message);
                            }
                        } catch (Exception e) {
                            message.what = FAIL;
                            message.obj = new Object[]{e, task};
                            h.sendMessage(message);
                            return;
                        }
                    }
                }, null);
        return furFutureTask;
    }


}
