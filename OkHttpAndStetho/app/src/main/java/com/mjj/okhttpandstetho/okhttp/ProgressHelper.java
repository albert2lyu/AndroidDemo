package com.mjj.okhttpandstetho.okhttp;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * ���Ȼص�������
 */
public class ProgressHelper {
    /**
     * ��װOkHttpClient�����������ļ��Ļص�
     * @param client ����װ��OkHttpClient
     * @param progressListener ���Ȼص��ӿ�
     * @return ��װ���OkHttpClient��ʹ��clone��������
     */
    public static OkHttpClient addProgressResponseListener(OkHttpClient client,final ProgressResponseBody.DownloadProgressListener progressListener){
        //��¡
        OkHttpClient clone = client.clone();
        //����������
        clone.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //����
                Response originalResponse = chain.proceed(chain.request());
                //��װ��Ӧ�岢����
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                        .build();
            }
        });
        return clone;
    }

    /**
     * ��װ�����������ϴ��ļ��Ļص�
     * @param requestBody ������RequestBody
     * @param progressRequestListener ���Ȼص��ӿ�
     * @return ��װ��Ľ��Ȼص�������
     */
    public static ProgressRequestBody addProgressRequestListener(RequestBody requestBody,ProgressRequestBody.UpLoadProgressListener progressRequestListener){
        return new ProgressRequestBody(requestBody,progressRequestListener);
    }
}
