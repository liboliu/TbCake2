package com.popo.tbcake.taskDispather;

import android.content.Context;

import com.popo.tbcake.taskDispather.impl.VolleyTaskDispather;

/**
 * Created by popo on 14-12-18.
 */
public class TaskDispaterFactory {
    public static TaskDispatcher getTaskDispater(Context context){
        return new VolleyTaskDispather(context);
    }
}
