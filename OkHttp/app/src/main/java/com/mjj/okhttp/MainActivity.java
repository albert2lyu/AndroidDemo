package com.mjj.okhttp;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

//    private final String URL = "http://news-at.zhihu.com/api/4/news/latest";


    private final String URL = "http://nfarmapitest.vegnet.cn/Users/SearchLogin";

    //网页接口秘钥
    public final static String UserId = "A6971118873561";
    public final static String UserPassword= UserId + "UZ" + "8C757B31-A896-F477-C46D-4E27E05528D3" + "UZ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * 异步的请求
     * @param v
     */
    public void doGet(View v){
        Request request = new Request.Builder()
                .url("https://github.com/square/okhttp/blob/master/README.md")
                .build();
        Call call = MyAppliction.getOkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("asd", "onFailure");
                //非UI线程
            }

            @Override
            public void onResponse(Response response) throws IOException {
                //非UI线程
                Log.i("asd", "onResponse");
                Log.i("asd", "data:" + response.body().string());

            }
        });
    }

    public void doPost(View v){

        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("LoginID", "mjj");
        builder.add("Password", MD5Util.encode("123456"));
        builder.add("RoleType", "1");
        builder.add("CheckState", "1");
        builder.add("DeviceToken", "123123");


        MultipartBuilder multipartBuilder = new MultipartBuilder();

//        Headers headers = new Headers.Builder().add()


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());

        Request request = new Request.Builder()
                .url(URL)
                .addHeader("UserId", UserId)
                .addHeader("UserPassword", MD5Util.encode(UserPassword+time))
                .addHeader("CurrentTime", time)
                .post(builder.build())
                .build();
        Call call = MyAppliction.getOkHttpClient().newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.i("asd", "data:" + response.body().string());
            }
        });


    }
    /**
     * 非异步的请求(在Android下不可用)
     * @param v
     */
    public void doUIGet(View v) throws IOException {
        Request request = new Request.Builder()
                .url("https://github.com/square/okhttp/blob/master/README.md")
                .build();

        Response response = MyAppliction.getOkHttpClient().newCall(request).execute();

        Log.i("asd", "data:" + response.body().string());
    }






}
