package cn.com.popo.tbcake.tbCake.http.parser;

import java.io.File;
import java.io.InputStream;

public interface Parser<T>  {
	public T parse(String s, Class<T> t) throws Exception;
	public T parse(InputStream in, Class<T> t) throws Exception;
	public String parse(String s) throws Exception;
	public String parse(InputStream in) throws Exception;
	public Boolean parseTofile(InputStream in, File into) throws Exception;
}
