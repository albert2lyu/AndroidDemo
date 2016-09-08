package me.majiajie.surfaceandtextureview.surfaceview;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView  implements SurfaceHolder.Callback
{
    private SurfaceHolder mHolder;

    private SurfaceViewHelper mSurfaceViewHelper;

    public MySurfaceView(Context context,SurfaceViewHelper surfaceViewHelper)
    {
        super(context);
        mSurfaceViewHelper = surfaceViewHelper;

        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        //开始绘制
        mSurfaceViewHelper.setPreviewDisplay(holder);
        mSurfaceViewHelper.startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        if (mHolder.getSurface() == null){
            // 预览表面不存在
            return;
        }

        // 修改视图大小前停止绘制
        try
        {
            mSurfaceViewHelper.stopPreview();
        }
        catch (Exception e) {}

        //调整大小
        try
        {
            mSurfaceViewHelper.setPreviewDisplay(mHolder);
            mSurfaceViewHelper.startPreview();
        } catch (Exception e){}
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        if (mHolder.getSurface() == null)
        {
            // 预览表面不存在
            return;
        }

        // 停止绘制
        try
        {
            mSurfaceViewHelper.stopPreview();
        }
        catch (Exception e) {}
    }
}
