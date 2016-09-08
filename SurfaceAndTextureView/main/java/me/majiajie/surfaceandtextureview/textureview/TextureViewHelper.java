package me.majiajie.surfaceandtextureview.textureview;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.TextureView;

import me.majiajie.surfaceandtextureview.GenerateBitmapAction;
import me.majiajie.surfaceandtextureview.HandlerThreadHelper;

public class TextureViewHelper
{
    private TextureView mTextureView;

    private HandlerThreadHelper mHandlerThreadHelper;

    private Handler mHandler;

    private int mWidth;

    private int mHeight;

    private boolean stop;

    public TextureViewHelper() {}

    public void setPreviewDisplay(MyTextureView textureView)
    {
        if(textureView != null)
        {
            mTextureView = textureView;

            mWidth = mTextureView.getWidth();
            mHeight = mTextureView.getHeight();
        }
    }

    public void startPreview()
    {
        stop = false;
        mHandlerThreadHelper = new HandlerThreadHelper("TextureViewStart");
        mHandler =  mHandlerThreadHelper.startBackgroundThread();
    }

    public void stopPreview()
    {
        stop = true;
        mHandlerThreadHelper.stopBackgroundThread();
    }

    public synchronized void post(final GenerateBitmapAction action)
    {
        if(!isStop() && mHandler != null)
        {
            mHandler.post(new Runnable() {
                @Override
                public void run()
                {
                    Bitmap bitmap = action.generateBitmap();
                    if(bitmap != null)
                    {
                        Canvas canvas = mTextureView.lockCanvas();

                        canvas.drawBitmap(bitmap,0,0,null);
                        mTextureView.unlockCanvasAndPost(canvas);
                    }
                }
            });
        }
    }

    public int getWidth()
    {
        return mWidth;
    }

    public int getHeight()
    {
        return mHeight;
    }

    public boolean isStop()
    {
        return stop;
    }

}
