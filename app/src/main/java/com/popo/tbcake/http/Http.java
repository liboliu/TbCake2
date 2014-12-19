package com.popo.tbcake.http;

import java.io.InputStream;

/**
 * 执行http任务的接口
 * 
 * @author popo
 * @param <T>
 *
 */
public interface Http {
	
	public String doGetForString(String url) throws Exception;

	public InputStream doGetForInputStream(String url) throws Exception;

	public String doPostForString(String ur, String bodyl) throws Exception;

	public InputStream doPostForInputStream(String ur, String bodyl) throws Exception;


	
}