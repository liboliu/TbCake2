package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by popo on 14-12-11.
 */
public class OkhttpStack implements HttpStack {

    private OkHttpClient okHttpClient;

    public OkhttpStack(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }


    @Override
    public HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders) throws IOException, AuthFailureError {

        Map<String, String> headersMap = request.getHeaders();
        int method = request.getMethod();
        int timeoutMs = request.getTimeoutMs();
        okHttpClient.setConnectTimeout(timeoutMs, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(timeoutMs, TimeUnit.MILLISECONDS);


        com.squareup.okhttp.Request.Builder builder = new com.squareup.okhttp.Request.Builder();
        builder.url(request.getUrl());
        for (String key : headersMap.keySet()) {
            builder.addHeader(key, headersMap.get(key));
        }
        byte[] body = request.getBody();
        RequestBody okhttpBody = RequestBody.create(MediaType.parse(request.getBodyContentType()), body);
        switch (request.getMethod()) {
            case Request.Method.DEPRECATED_GET_OR_POST:

                builder.post(okhttpBody);
                break;
            case Request.Method.GET:
                builder.get();
                break;
            case Request.Method.POST:
                builder.post(okhttpBody);
                break;
            case Request.Method.PUT:

                builder.put(okhttpBody);
                break;
            case Request.Method.DELETE:
                builder.delete();
                break;

        }

        com.squareup.okhttp.Request okhttpRequest = builder.build();

        Response oKhttpResponse = okHttpClient.newCall(okhttpRequest).execute();
        //失败
        if (!oKhttpResponse.isSuccessful()) {
            throw new IOException("Uexpected code " + oKhttpResponse.message());
        }

        ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
        StatusLine responseStatus = new BasicStatusLine(protocolVersion,
                oKhttpResponse.code(), oKhttpResponse.message());
        BasicHttpResponse response = new BasicHttpResponse(responseStatus);
        response.setEntity(entityFromConnection(oKhttpResponse));

        return null;
    }

    /**
     * Initializes an {@link org.apache.http.HttpEntity} from the given {@link java.net.HttpURLConnection}.
     *
     * @param response
     * @return an HttpEntity populated with data from <code>connection</code>.
     */
    private static HttpEntity entityFromConnection(Response response) throws  IOException{
        BasicHttpEntity entity = new BasicHttpEntity();
        InputStream inputStream;
        inputStream = response.body().byteStream();
        entity.setContent(inputStream);
        response.header("");
        entity.setContentEncoding("");
        entity.setContentLength(response.body().contentLength());
        entity.setContentType(response.body().contentType().toString());
        return entity;
    }

}
