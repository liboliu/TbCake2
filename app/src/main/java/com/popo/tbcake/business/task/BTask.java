package com.popo.tbcake.business.task;

import android.content.ContentValues;

import com.popo.tbcake.http.Http;
import com.popo.tbcake.http.HttpFactory;
import com.popo.tbcake.http.parser.Parser;
import com.popo.tbcake.http.parser.impl.JsonParserImpl;
import com.popo.tbcake.http.parser.impl.ParseFactory;
import com.popo.tbcake.taskDispather.TaskDispatcher;

import java.io.File;
import java.util.Set;

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
	private boolean interuptedable;
	public TaskDispatcher.TaskDispatcherCallBack<T> callBack;
    public Parser<T> parser;
    public Class<T> t;
    public Http httpTool;
	
	public String getBody(){
		return body;
	}

	public Class<T> getClassType() {
		return t;
	}

	public Parser<T> getParser() {
		return parser;
	}

	public boolean isInteruptedAble() {
		return interuptedable;
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
        StringBuilder sb = new StringBuilder();
        Set<String> keys = params.keySet();
        for(String key:keys){
            sb.append(key);
            sb.append("=");
            sb.append(params.getAsString(key));
            sb.append("&");
        }

		return url+"?"+sb.substring(0,sb.length()-1);
	}

	public ContentValues getParams() {
		return params;
	}

	public TaskDispatcher.TaskDispatcherCallBack<T> getCallBack() {
		return callBack;
	}

	public static class Builder<T> {
		private TYPE httpMehodeType = TYPE.GET;
		private RESPONSE_TYPE responseType = RESPONSE_TYPE.JSON;
		private RESULT_TYPE result_type = RESULT_TYPE.OBJECT;
		private String url;
		private TaskDispatcher.TaskDispatcherCallBack<T> callBack;
		private ContentValues params;
		private Parser<T> parser;
		private Class<T> t=null;
        private Http http;

		public Builder(Class<T> t) {
			// TODO Auto-generated constructor stub
			this.t=t;
		}

		public BTask<T> create() {


			BTask<T> bt = new BTask<T>();
			bt.httpMehodeType = httpMehodeType;
			bt.responseType = responseType;
            if(parser == null)
            {
                switch (responseType){
                    case STREAM:
                        parser = ParseFactory.getJsonParser();
                        break;
                    case XML:
                        parser = ParseFactory.getXmlParser();
                        break;
                    case JSON:
                        parser = ParseFactory.getJsonParser();
                        break;
                }
            }
			bt.result_type = result_type;
			bt.url = url;
			bt.callBack = callBack;
			bt.params = params;
			bt.parser = parser;
            if(http==null)
                http = HttpFactory.getOkHttp();
            bt.httpTool = http;
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

        public Builder() {
            super();
        }

        public Builder<T> setResponseType(RESPONSE_TYPE type) {
			this.responseType = type;
			return this;
		}

        public Builder<T> setResultType(RESULT_TYPE type){
            this.result_type=type;
            return this;
        }

        public Builder<T> setParseTool(Parser<T> parser){
            this.parser=parser;
            return this;
        }

        public Builder<T> setContValues(ContentValues params){
            this.params= params;
            return this;
        }

        public Http getHttp() {
            return http;
        }

        public Builder<T> setHttp(Http http) {
            this.http = http;
            return this;
        }

        public Builder<T> setParser(Parser<T> parser){
            this.parser=parser;
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
		return null;
	}
}
