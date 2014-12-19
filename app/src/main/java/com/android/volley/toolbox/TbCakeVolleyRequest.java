package com.android.volley.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.popo.tbcake.business.task.BTask;
import com.popo.tbcake.http.parser.Parser;

import java.io.ByteArrayInputStream;

/**
 * Created by popo on 14-12-19.
 */
public class TbCakeVolleyRequest<T> extends Request<T> {

    BTask<T> bTask;

    public TbCakeVolleyRequest(BTask<T> bTask) {
        super(getMethod(bTask), bTask.getUrl(), new TbCakeResponseErrorListener(bTask));
        this.bTask = bTask;


    }

    private static int getMethod(BTask bTask) {
        BTask.TYPE method_type = bTask.getHttpMehodeType();
        int method = Request.Method.GET;
        if (method_type == BTask.TYPE.GET) {
            method = Request.Method.GET;
        } else if (method_type == BTask.TYPE.POST) {
            method = Request.Method.POST;
        }
        return method;
    }


    static class TbCakeResponseErrorListener<T> implements Response.ErrorListener {

        BTask<T> bTask;

        public TbCakeResponseErrorListener(BTask<T> bTask) {
            this.bTask = bTask;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            bTask.callBack.onFail(error.getMessage());
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {

        Parser<T> parser = bTask.getParser();
        T t = null;
        try {
            BTask.RESULT_TYPE resultType = bTask.getResult_type();
            if (resultType == BTask.RESULT_TYPE.OBJECT) {
                t = parser.parse(new String(response.data), bTask.t);
            } else if (resultType == BTask.RESULT_TYPE.INPUTSTEAM) {
                t = (T) new ByteArrayInputStream(response.data);
            } else if (resultType == BTask.RESULT_TYPE.STRING) {
                t = (T) new String(response.data);
            } else if (resultType == BTask.RESULT_TYPE.FILE) {
                t = (T) parser.parseTofile(new ByteArrayInputStream(response.data), bTask.getFileTo());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return Response.success(t, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(T response) {
        bTask.callBack.onSuccess(response);
    }
}
