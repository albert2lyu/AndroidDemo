package me.majiajie.surfaceandtextureview.surfaceview;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.view.SurfaceHolder;

import me.majiajie.surfaceandtextureview.GenerateBitmapAction;
import me.majiajie.surfaceandtextureview.HandlerThreadHelper;

public class SurfaceViewHelper
{
    private SurfaceHolder mSurfaceHolder;

    private HandlerThreadHelper mHandlerThreadHelper;

    private Handler mHandler;

    private int mWidth;

    private int mHeight;

    private boolean stop;

    public SurfaceViewHelper() {}

    public void setPreviewDisplay(SurfaceHolder holder)
    {
        if(holder != null)
        {
            mSurfaceHolder = holder;

            Rect rect = mSurfaceHolder.getSurfaceFrame();
            mWidth = rect.width();
            mHeight = rect.height();
        }
    }

    public void startPreview()
    {
        stop = false;
        mHandlerThreadHelper = new HandlerThreadHelper("SurfaceViewStart");
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
                        Canvas canvas = mSurfaceHolder.lockCanvas();

                        canvas.drawBitmap(bitmap,0,0,null);
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
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
