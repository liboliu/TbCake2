package cn.com.popo.tbcake.tbCake.http.parser.impl;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import cn.com.popo.tbcake.tbCake.http.parser.Parser;

public class JsonParserImpl<T> implements Parser<T> {
	Gson g = new Gson();
	@Override
	public T parse(String s,Class<T> t) throws Exception {
		// TODO Auto-generated method stub
		
		return g.fromJson(s, t);
	}

	@Override
	public T parse(InputStream in,Class<T> t) throws Exception {
		// TODO Auto-generated method stub
		return g.fromJson(new InputStreamReader(in), t);
	}

	@Override
	public String parse(String s) throws Exception {
		// TODO Auto-generated method stub
		return s;
	}

	@Override
	public String parse(InputStream in) throws Exception {
		// TODO Auto-generated method stub
		return "";
	}

}
