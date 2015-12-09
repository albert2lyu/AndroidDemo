package com.mjj.okhttpandstetho.okhttp;

public class HttpErrorCode {
	
	/**请求失效,code:-1*/
	public static final int FAILURE = -1;
	
	/**读取或解析数据异常,code:0*/
	public static final int READ_DATA_ERROE = 0;
	
	/**没有数据,code:1*/
	public static final int NO_DATA = 1;
	
	/**服务器异常,code:2*/
	public static final int SERVER_ERROR  = 2;
	
	/**
	 * 获取错误信息
	 * @param code	错误代码
	 * @return	错误信息
	 */
	public static String getMessages(int code)
	{
		switch(code)
		{
		case FAILURE:
			return "网络连接失败，请检查后刷新重试";
		case READ_DATA_ERROE:
			return "读取或解析数据异常";
		case NO_DATA:
			return "没有任何数据";
		case SERVER_ERROR:
			return "服务器维护中,请稍后再尝试";
		default:
			return "发生未知错误";
		}
	}
	
	
}
