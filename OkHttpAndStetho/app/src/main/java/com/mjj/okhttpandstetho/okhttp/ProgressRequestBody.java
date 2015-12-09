package com.mjj.okhttpandstetho.okhttp;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * ����ʵ���ϴ����ȵ�����
 */
public class ProgressRequestBody extends RequestBody {

    //ʵ�ʵĴ���װ������
    private final RequestBody requestBody;
    //���Ȼص��ӿ�
    private final UpLoadProgressListener progressListener;
    //��װ��ɵ�BufferedSink
    private BufferedSink bufferedSink;

    /**
     * ���캯������ֵ
     * @param requestBody ����װ��������
     * @param progressListener �ص��ӿ�
     */
    public ProgressRequestBody(RequestBody requestBody, UpLoadProgressListener progressListener) {
        this.requestBody = requestBody;
        this.progressListener = progressListener;
    }

    /**
     * ��д����ʵ�ʵ���Ӧ���contentType
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    /**
     * ��д����ʵ�ʵ���Ӧ���contentLength
     * @return contentLength
     * @throws IOException �쳣
     */
    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    /**
     * ��д����д��
     * @param sink BufferedSink
     * @throws IOException �쳣
     */
    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            //��װ
            bufferedSink = Okio.buffer(sink(sink));
        }
        //д��
        requestBody.writeTo(bufferedSink);
        //�������flush���������һ�������ݿ��ܲ��ᱻд��
        bufferedSink.flush();

    }

    /**
     * д�룬�ص����Ƚӿ�
     * @param sink Sink
     * @return Sink
     */
    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //��ǰд���ֽ���
            long bytesWritten = 0L;
            //���ֽڳ��ȣ������ε���contentLength()����
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //���contentLength��ֵ���������ٵ���
                    contentLength = contentLength();
                }
                //���ӵ�ǰд����ֽ���
                bytesWritten += byteCount;
                //�ص�
                progressListener.onUploadProgress(bytesWritten, contentLength, bytesWritten == contentLength);
            }
        };
    }

    public interface UpLoadProgressListener {
        void onUploadProgress(long bytesRead, long contentLength, boolean done);
    }
}
