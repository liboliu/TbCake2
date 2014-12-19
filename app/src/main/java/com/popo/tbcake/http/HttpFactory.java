package com.popo.tbcake.http;

import com.popo.tbcake.http.impl.OkhttpTool;

/**
 * Http工厂类
 * @author popo
 *
 */
public class HttpFactory {
	public static Http getOkHttp() {
		return new OkhttpTool();
	}
}
