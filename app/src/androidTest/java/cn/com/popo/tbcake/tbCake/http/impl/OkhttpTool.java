package cn.com.popo.tbcake.tbCake.http.impl;

import java.io.InputStream;

import cn.com.popo.tbcake.tbCake.http.Http;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class OkhttpTool implements Http {

	private static OkHttpClient okHttpClient;

	public OkhttpTool() {
		// TODO Auto-generated constructor stub

		if (okHttpClient == null) {
			synchronized (OkHttpClient.class) {
				if (okHttpClient == null)
					okHttpClient = new OkHttpClient();
			}
		}
	}

	@Override
	public String doGetForString(String url) throws Exception {
		// TODO Auto-generated method stub
		Request request = new Request.Builder().url(url).build();
		Response response = okHttpClient.newCall(request).execute();
		return response.body().string();
		
	}

	@Override
	public InputStream doGetForInputStream(String url) throws Exception {
		// TODO Auto-generated method stub
		Request request = new Request.Builder().url(url).build();
		Response response = okHttpClient.newCall(request).execute();
		return response.body().byteStream();
	}

	@Override
	public String doPostForString(String url,String body) throws Exception {
		// TODO Auto-generated method stub
		RequestBody requestbody = RequestBody.create(MediaType.parse(body),body);
		Request request = new Request.Builder().url(url).post(requestbody).build();
		Response response = okHttpClient.newCall(request).execute();
		return response.body().string();
	}

	@Override
	public InputStream doPostForInputStream(String url,String body) throws Exception {
		// TODO Auto-generated method stub,
		RequestBody requestbody = RequestBody.create(MediaType.parse(body),body);
		Request request = new Request.Builder().url(url).post(requestbody).build();
		Response response = okHttpClient.newCall(request).execute();
		return response.body().byteStream();
	}

}
