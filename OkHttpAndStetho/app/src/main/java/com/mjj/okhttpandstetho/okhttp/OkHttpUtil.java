package com.mjj.okhttpandstetho.okhttp;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.mjj.okhttpandstetho.app.MyApplication;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class OkHttpUtil {

    private final boolean DEBUG = MyApplication.DEBUG;

    private final int TIME_OUT = 25;

    private final MediaType TYPE_DEFUALT = MediaType.parse("application/x-www-form-urlencoded");

    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

    private static OkHttpUtil mInstance = null;

    private static OkHttpClient mOkHttpClient;

    private OkHttpUtil(){
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(TIME_OUT, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(TIME_OUT, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(TIME_OUT, TimeUnit.SECONDS);
        if(DEBUG){
            mOkHttpClient.networkInterceptors().add(new StethoInterceptor());
        }
    }

    /**
     * 单例模式，高并发较差，但手机端基本不会出现高并发
     */
    private static OkHttpUtil getInstance(){
        if(mInstance == null){
            synchronized (OkHttpUtil.class)
            {
                if(mInstance == null){
                    mInstance = new OkHttpUtil();
                }
            }
        }
        return mInstance;
    }
    
    /**
     * 异步执行GET请求,tag默认为url地址
     */
    public static void doGet(String url,OkHttpCallBack callback)
    {
        getInstance().enqueueGet(url, url, callback);
    }
    
    /**
     * 异步执行GET请求,tag默认为url地址
     */
    public static void doGet(String url,Map<String,String> params,OkHttpCallBack callback)
    {
    	getInstance().enqueueGet(url, url+"?"+encodeParameters(params, DEFAULT_PARAMS_ENCODING), callback);
    }
    
    /**
     * 异步执行GET请求
     */
    public static void doGet(String tag,String url,OkHttpCallBack callback)
    {
        getInstance().enqueueGet(tag, url, callback);
    }
    
    /**
     * 异步执行GET请求
     */
    public static void doGet(String tag,String url,Map<String,String> params,OkHttpCallBack callback)
    {
        getInstance().enqueueGet(tag, url+"?"+encodeParameters(params, DEFAULT_PARAMS_ENCODING), callback);
    }
    
    /**
     * 异步执行POST请求,tag默认为url地址
     */
    public static void doPost(String url,Map<String,String> params, OkHttpCallBack callback)
    {
        getInstance().enqueuePost(url, url, encodeParameters(params, DEFAULT_PARAMS_ENCODING), callback);
    }
    
    /**
     * 异步执行POST请求,tag默认为url地址
     */
    public static void doPost(String url,String params, OkHttpCallBack callback)
    {
        getInstance().enqueuePost(url, url, params, callback);
    }

    /**
     * 异步执行POST请求
     */
    public static void doPost(String tag,String url,Map<String,String> params, OkHttpCallBack callback)
    {
        getInstance().enqueuePost(tag, url, encodeParameters(params, DEFAULT_PARAMS_ENCODING), callback);
    }

    /**
     * 异步执行POST请求
     */
    public static void doPost(String tag,String url,String params, OkHttpCallBack callback)
    {
        getInstance().enqueuePost(tag, url, params, callback);
    }

    /**
     * 取消请求任务
     * @param tag
     */
    public static void cancel(String tag)
    {
        if (mOkHttpClient != null){
            mOkHttpClient.cancel(tag);
        }
    }

    /**
     * 异步GET
     */
    private  void enqueueGet(String tag,String url,OkHttpCallBack callback)
    {
        Request request = buildGet(tag, url);
        asynchronous(request, callback);
    }

    /**
     * 异步Post
     */
    private  void enqueuePost(String tag,String url,String paramJson,OkHttpCallBack callback)
    {
        RequestBody body = RequestBody.create(TYPE_DEFUALT,paramJson);
        Request request = buildPost(tag, url, body);
        asynchronous(request,callback);
    }

    /**
     * 建立GET请求
     */
    private Request buildGet(String tag, String url)
    {
        return new Request.Builder()
                .headers(getHeaders())
                .tag(tag)
                .url(url)
                .build();
    }

    /**
     * 建立POST请求
     */
    private Request buildPost(String tag, String url, RequestBody body)
    {
        return new Request.Builder()
                .headers(getHeaders())
                .tag(tag)
                .post(body)
                .url(url)
                .build();
    }

    /**
     * 异步执行
     */
    private void asynchronous(Request request,OkHttpCallBack callback)
    {
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 将Map<String, String>编译成字符串
     */
    private static String encodeParameters(Map<String, String> params, String paramsEncoding) 
    {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    /**
     *  全局的头文件
     */
    public Headers getHeaders()
    {
        Headers headers = new Headers.Builder()
                .add("test","test")
                .build();

        return headers;
    }


}
