package me.majiajie.surfaceandtextureview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import me.majiajie.surfaceandtextureview.textureview.MyTextureView;
import me.majiajie.surfaceandtextureview.textureview.TextureViewHelper;

public class MainActivity extends AppCompatActivity
{

    final int MSG = 0x007;

    FrameLayout mFrameLayout;

    MyHandler mMyHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFrameLayout = (FrameLayout) findViewById(R.id.framelayout);

//        testSurfaceView();

        testTextureView();

    }

//    SurfaceViewHelper helper;
//
//    private void testSurfaceView()
//    {
//        helper = new SurfaceViewHelper();
//        MySurfaceView surfaceView = new MySurfaceView(this,helper);
//        mFrameLayout.addView(surfaceView);
//    }

    TextureViewHelper helper;

    private void testTextureView()
    {
        helper = new TextureViewHelper();
        MyTextureView textureView = new MyTextureView(this,helper);
        mFrameLayout.addView(textureView);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(hasFocus)
        {
            mMyHandler = new MyHandler();

            mMyHandler.sendEmptyMessage(MSG);

        }
        else if(mMyHandler != null)
        {
            mMyHandler.removeMessages(MSG);
        }
    }

    class MyHandler extends Handler
    {
        int n = 1;

        @Override
        public void handleMessage(final Message msg)
        {
            if(msg.what == MSG)
            {
                helper.post(new GenerateBitmapAction() {
                    @Override
                    public Bitmap generateBitmap()
                    {
                        Bitmap bitmap;
                        if(msg.what % 2 == 0)
                        {
                            bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
                        }
                        else
                        {
                            bitmap = DrawableUtils.toBitmap(MainActivity.this,R.drawable.ic_http_72dp);
                        }
                        return bitmap;
                    }
                });
                n++;
                this.sendEmptyMessageDelayed(MSG,1_000);
            }



        }
    }
}
