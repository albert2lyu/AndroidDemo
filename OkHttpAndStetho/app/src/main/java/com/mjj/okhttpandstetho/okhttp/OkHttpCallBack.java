package com.mjj.okhttpandstetho.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class OkHttpCallBack<T> implements Callback {
	
	private Type mType;
	
	public OkHttpCallBack() 
	{
		mType = getSuperclassTypeParameter(getClass());
	}
	
	/**
	 * 数据解析完成之后的回调，运行的主线程
	 * @param response 指定泛型数据
	 */
    public abstract void onResponse(T response);
    
    /**
	 * 发生异常的回调，运行在主线程
	 * @param code 错误代码
	 * @param message 错误信息
	 */
	public void onError(int code,String message){};
    
	/**
     * 对请求数据的解析,运行在网络请求线程
     * 如果泛型是String类型直接返回,别的类型默认用Gson解析
     * @param body 服务器返回的数据
     * @return 指定泛型数据
     */
    public T analyData(String body){
    	T Data;

    	if (mType == String.class)
        {
    		Data = (T)body;
        } 
    	else
        {
    		Gson gson = new Gson();
    		Data = gson.fromJson(body,mType);
        }
    	return Data;
    };
    

    private static Handler mUiThreadHandler = new Handler(Looper.getMainLooper());
    
    /**
     * 请求失效，Okhttp在进行IO操作时，手动取消也会调用这个方法
     */
    @Override
    public void onFailure(Request request, IOException e) 
    {
        mUiThreadHandler.post(new Runnable()
        {
            @Override
            public void run() 
            {
                onError(HttpErrorCode.FAILURE,HttpErrorCode.getMessages(HttpErrorCode.FAILURE));
            }
        });
    }

    @Override
    public void onResponse(Response response) 
    {
        if(response.code() == HttpURLConnection.HTTP_OK)
        {
			try 
			{
				final String body = response.body().string();

				final T data = analyData(body);
				
				runOnUiThread(new Runnable() 
				{
	                @Override
	                public void run() 
	                {
	                    onResponse(data);
	                }
				});
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				onError(HttpErrorCode.READ_DATA_ERROE,HttpErrorCode.getMessages(HttpErrorCode.READ_DATA_ERROE));
			}
        }
        else
        {
            onError(response.code(),"错误");
        }

    }
    
    private final void runOnUiThread(Runnable action) {
    	mUiThreadHandler.post(action);
    }
    
    Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
			throw new RuntimeException("缺少类型参数（泛型）");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }
    
}
