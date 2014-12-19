package cn.com.popo.tbcake.tbCake.business.task;

import java.io.File;

import android.content.ContentValues;

import cn.com.popo.tbcake.tbCake.http.parser.Parser;
import cn.com.popo.tbcake.tbCake.http.parser.impl.JsonParserImpl;
import cn.com.popo.tbcake.tbCake.taskDispather.TaskDispatcher;

public class BTask<T> {
	/**
	 * Http 提交类型
	 * 
	 * @author popo
	 */
	public enum TYPE {
		GET, POST,

	};

	/**
	 * 任务需要返回的类型
	 * 
	 * @author popo
	 *
	 */
	public enum RESULT_TYPE {
		INPUTSTEAM, STRING, OBJECT, FILE
	}

	/**
	 * 物业返回的类型
	 * 
	 * @author popo
	 *
	 */
	public enum RESPONSE_TYPE {
		XML, JSON, STREAM
	}

	private TYPE httpMehodeType = TYPE.GET;
	private RESPONSE_TYPE responseType = RESPONSE_TYPE.JSON;
	private RESULT_TYPE result_type = RESULT_TYPE.OBJECT;
	private String url=null;
	private String body=null;
	private ContentValues params;
	private boolean interupted;
	private TaskDispatcher.TaskDispatcherCallBack<T> callBack;
	private Parser<T> parser;
	private Class<T> t;
	
	public String getBody(){
		return body;
	}
	
	public Class<T> getClassType() {
		return t;
	}

	public Parser<T> getParser() {
		return parser;
	}

	public boolean isInterupted() {
		return interupted;
	}

	public TYPE getHttpMethodType() {
		return httpMehodeType;
	}

	public TYPE getHttpMehodeType() {
		return httpMehodeType;
	}

	public RESULT_TYPE getResult_type() {
		return result_type;
	}

	public RESPONSE_TYPE getRESPONSE_TYPE() {
		return responseType;
	}

	public String getUrl() {
		return url;
	}

	public ContentValues getParams() {
		return params;
	}

	public TaskDispatcher.TaskDispatcherCallBack<T> getCallBack() {
		return callBack;
	}

	static class Builder<T> {
		private TYPE httpMehodeType = TYPE.GET;
		private RESPONSE_TYPE responseType = RESPONSE_TYPE.JSON;
		private RESULT_TYPE result_type = RESULT_TYPE.OBJECT;
		private String url;
		private TaskDispatcher.TaskDispatcherCallBack<T> callBack;
		private ContentValues params;
		private Parser<T> parser;
		private Class<T> t=null;

		public Builder(Class<T> t) {
			// TODO Auto-generated constructor stub
			this.t=t;
		}

		public BTask<T> create() {

			if (!checkAndset()) {
				return null;
			}
			BTask<T> bt = new BTask<T>();
			bt.httpMehodeType = httpMehodeType;
			bt.responseType = responseType;
			bt.result_type = result_type;
			bt.url = url;
			bt.callBack = callBack;
			bt.params = params;
			bt.parser = parser;

			return bt;
		}

		private boolean checkAndset() {
			// TODO Auto-generated method stub
			// 设置解析器
			if (parser == null) {
				if (result_type == RESULT_TYPE.OBJECT) {
					if (responseType == RESPONSE_TYPE.JSON) {
						parser = new JsonParserImpl<T>();
					}
				}
			}
			return true;
		}

		public Builder<T> setCallback(
				TaskDispatcher.TaskDispatcherCallBack<T> callBack) {
			this.callBack = callBack;
			return this;
		}

		public Builder<T> setUrl(String url) {
			this.url = url;
			return this;
		}

		public Builder<T> setResponseType(RESPONSE_TYPE type) {
			this.responseType = type;
			return this;
		}

	}

	/**
	 * 
	 * @param t
	 *            任务需要 返回的类型
	 * @return
	 */
	public static <T> Builder<T> newBuilder(Class<T> t) {
		return new Builder<T>(t);
	}

	public File getFileTo() {
		// TODO Auto-generated method stub
		return "";
	}
}
