package me.majiajie.surfaceandtextureview.textureview;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.TextureView;

public class MyTextureView extends TextureView implements TextureView.SurfaceTextureListener
{
    private TextureViewHelper mTextureViewHelper;

    public MyTextureView(Context context,TextureViewHelper textureViewHelper)
    {
        super(context);
        this.setSurfaceTextureListener(this);
        mTextureViewHelper = textureViewHelper;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height)
    {
        //开始绘制
        mTextureViewHelper.setPreviewDisplay(this);
        mTextureViewHelper.startPreview();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height)
    {
        // 修改视图大小前停止绘制
        try
        {
            mTextureViewHelper.stopPreview();
        }
        catch (Exception e) {}

        //调整大小
        try
        {
            mTextureViewHelper.setPreviewDisplay(this);
            mTextureViewHelper.startPreview();
        } catch (Exception e){}
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface)
    {
        // 停止绘制
        try
        {
            mTextureViewHelper.stopPreview();
        }
        catch (Exception e) {}

        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface)
    {
        Log.i("asd","update");
    }
}
