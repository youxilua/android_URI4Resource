package com.achai.framework.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.achai.framework.adapter.AsyncImageAdapter;
import com.achai.framework.debug.BuildConfig;
import com.achai.framework.deviceinfo.DevicesNetInfo;
import com.achai.framework.net.fetch.DataFetch;

public class ImageFetcher extends ImageResizer {

	private static final String TAG = "ImageFetcher";

	/**
	 * @param context
	 * @param imageWidth
	 * @param imageHeight
	 */
	public ImageFetcher(Context context, int imageWidth, int imageHeight) {
		super(context, imageWidth, imageHeight);
		init(context);
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
			Bitmap bitmap = decodeSampledBitmapFromFile(f.toString(),
					mImageWidth, mImageHeight);
			// 是否对图片进行一个自定义缩放
			if (isScale) {
				bitmap = Bitmap.createScaledBitmap(bitmap, mImageWidth, mImageHeight, true);
				if (isRound)
					bitmap = setImageRound(bitmap, roundPx);
				return bitmap;
			} else {
				if (isRound) {
					bitmap = setImageRound(bitmap, roundPx);
				}
				return bitmap;
			}

		}

		return null;
	}

	@Override
	protected Bitmap processBitmap(Object data) {
		return processBitmap(String.valueOf(data));
	}

}
