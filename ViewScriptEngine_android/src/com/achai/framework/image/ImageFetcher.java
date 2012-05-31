package com.achai.framework.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.achai.framework.debug.BuildConfig;
import com.achai.framework.deviceinfo.DevicesNetInfo;
import com.achai.framework.net.fetch.DataFetch;

public class ImageFetcher extends ImageResizer {

	private static final String TAG = "ImageFetcher";
	private boolean mNeedScale = false;

	/**
	 * @param context
	 * @param imageWidth
	 * @param imageHeight
	 */
	public ImageFetcher(Context context, int imageWidth, int imageHeight) {
		super(context, imageWidth, imageHeight);
		init(context);
	}

	public void setmNeedScale(boolean mNeedScale) {
		this.mNeedScale = mNeedScale;
	}

	/**
	 * @param context
	 * @param imageSize
	 */
	public ImageFetcher(Context context, int imageSize) {
		super(context, imageSize);
		init(context);
	}
	

	/**
	 * 检查是否有网络
	 * 
	 * @param context
	 */
	private void init(Context context) {
		DevicesNetInfo.checkConnection(context);
	}
	
    private Bitmap processBitmap(String data) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "processBitmap - " + data);
        }

        // Download a bitmap, write it to a file
        final File f = DataFetch.dowanLoadBitmap(mContext, data);

        if (f != null) {
            // 是否对图片进行一个自定义缩放
        	if(mNeedScale){
        		Bitmap b = decodeSampledBitmapFromFile(f.toString(), mImageWidth, mImageHeight);
        		return Bitmap.createScaledBitmap(b, mImageWidth, mImageWidth, true);
        	}else{
        		return decodeSampledBitmapFromFile(f.toString(), mImageWidth, mImageHeight);
        	}
            
        }

        return null;
    }

	@Override
	protected Bitmap processBitmap(Object data) {
		return processBitmap(String.valueOf(data));
	}

}
