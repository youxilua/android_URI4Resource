package com.achai.framework.image;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.achai.framework.cache.ImageCache;
import com.achai.framework.debug.BuildConfig;

/**
 * 用于异步获得图片的抽象类
 * 
 * @author tom_achai
 * 
 */
public abstract class ImageWorker {
	private static final String TAG = "ImageWorker";
	private static final int FADE_IN_TIME = 200;
	// 图片内存需要设置!
	private ImageCache mImageCache;
	private Bitmap mLoadingBitmap;
	// 淡出图片
	private boolean mFadeInBitmap = true;

	public void setmFadeInBitmap(boolean mFadeInBitmap) {
		this.mFadeInBitmap = mFadeInBitmap;
	}

	

	// 是否尽早推出task任务
	private boolean mExitTasksEarly = false;
	protected Context mContext;

	public ImageWorker(Context context) {
		mContext = context;
	}

	public void loadImage(Object data, ImageView imageview) {
		Bitmap bitmap = null;
		// 1,从内存里面读
		if (mImageCache != null) {
			bitmap = mImageCache.getBitmapFromMemCache(String.valueOf(data));
		}
		// 2,在内存里面找到图片直接设置
		if (bitmap != null) {
			imageview.setImageBitmap(bitmap);
			// 3,判断任务是否在执行中
		} else if (cancelPotentialWork(data, imageview)) {
			// 4,获取一个bitmap 的异步操作
			final BitmapWorkerTask task = new BitmapWorkerTask(imageview);
			// // 5,设置尚未下载的图片
			final AsyncDrawable asyncDrawable = new AsyncDrawable(
					mContext.getResources(), mLoadingBitmap, task);
			// BitmapDrawable bd = new BitmapDrawable(mLoadingBitmap);

			imageview.setImageDrawable(asyncDrawable);
			// imageview.setImageBitmap(asyncDrawable);
			// 6,开始后台执行
			task.execute(data);
		}

	}

	public void setLoadingImage(Bitmap bitmap) {
		mLoadingBitmap = bitmap;
	}

	public void setLoadingImage(int resId) {
		mLoadingBitmap = BitmapFactory.decodeResource(mContext.getResources(),
				resId);
	}

	/**
	 * Returns true if the current work has been canceled or if there was no
	 * work in progress on this image view. Returns false if the work in
	 * progress deals with the same data. The work is not stopped in that case.
	 * 判断是否已经在工作的任务,true为还没工作,或者取消了任务 false为正在运行的任务
	 */
	public static boolean cancelPotentialWork(Object data, ImageView imageView) {
		final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

		if (bitmapWorkerTask != null) {
			final Object bitmapData = bitmapWorkerTask.data;
			if (bitmapData == null || !bitmapData.equals(data)) {
				bitmapWorkerTask.cancel(true);
				if (BuildConfig.DEBUG) {
					Log.d("fetch", "cancelPotentialWork - cancelled work for "
							+ data);
				}
			} else {
				// The same work is already in progress.
				return false;
			}
		}
		return true;
	}

	/**
	 * A custom Drawable that will be attached to the imageView while the work
	 * is in progress. Contains a reference to the actual worker task, so that
	 * it can be stopped if a new binding is required, and makes sure that only
	 * the last started worker process can bind its result, independently of the
	 * finish order.
	 */
	private static class AsyncDrawable extends BitmapDrawable {
		private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

		public AsyncDrawable(Resources res, Bitmap bitmap,
				BitmapWorkerTask bitmapWorkerTask) {
			super(res, bitmap);

			bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(
					bitmapWorkerTask);
		}

		public BitmapWorkerTask getBitmapWorkerTask() {
			return bitmapWorkerTaskReference.get();
		}
	}

	/**
	 * @param imageView
	 *            Any imageView
	 * @return Retrieve the currently active work task (if any) associated with
	 *         this imageView. null if there is no such task.
	 */
	private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
		if (imageView != null) {
			final Drawable drawable = imageView.getDrawable();
			if (drawable instanceof AsyncDrawable) {
				final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
				return asyncDrawable.getBitmapWorkerTask();
			}
		}
		return null;
	}

	protected abstract Bitmap processBitmap(Object data);

	/**
	 * The actual AsyncTask that will asynchronously process the image.
	 */
	private class BitmapWorkerTask extends AsyncTask<Object, Void, Bitmap> {
		private Object data;
		private final WeakReference<ImageView> imageViewReference;

		public BitmapWorkerTask(ImageView imageView) {
			imageViewReference = new WeakReference<ImageView>(imageView);
		}

		/**
		 * Background processing.
		 */
		@Override
		protected Bitmap doInBackground(Object... params) {
			data = params[0];
			final String dataString = String.valueOf(data);
			Bitmap bitmap = null;

			// 从缓存里面里面读取
			if (mImageCache != null && !isCancelled()
					&& getAttachedImageView() != null && !mExitTasksEarly) {
				bitmap = mImageCache.getBitmapFromDiskCache(dataString);
			}
			// 用其他方式读取,例如网络下载
			if (bitmap == null && !isCancelled()
					&& getAttachedImageView() != null && !mExitTasksEarly) {
				bitmap = processBitmap(params[0]);
			}

			if (bitmap != null && mImageCache != null) {
				// 添加到内存 和 图片缓存里面
				mImageCache.addBitmapToCache(dataString, bitmap);
			}

			return bitmap;
		}

		/**
		 * Once the image is processed, associates it to the imageView
		 */
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			// if cancel was called on this task or the "exit early" flag is set
			// then we're done
			if (isCancelled() || mExitTasksEarly) {
				bitmap = null;
			}

			final ImageView imageView = getAttachedImageView();
			if (bitmap != null && imageView != null) {
				setImageBitmap(imageView, bitmap);
			}
		}

		/**
		 * Returns the ImageView associated with this task as long as the
		 * ImageView's task still points to this task as well. Returns null
		 * otherwise.
		 */
		private ImageView getAttachedImageView() {
			final ImageView imageView = imageViewReference.get();
			final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

			if (this == bitmapWorkerTask) {
				return imageView;
			}

			return null;
		}
	}

	/**
	 * Called when the processing is complete and the final bitmap should be set
	 * on the ImageView. 当图片下载完以后进行显示
	 * 
	 * @param imageView
	 * @param bitmap
	 */
	private void setImageBitmap(ImageView imageView, Bitmap bitmap) {

		// 是否淡出图片
		if (mFadeInBitmap) {
			// Transition drawable with a transparent drwabale and the final
			// bitmap
			final TransitionDrawable td = new TransitionDrawable(
					new Drawable[] {
							new ColorDrawable(android.R.color.transparent),
							new BitmapDrawable(mContext.getResources(), bitmap) });
			// Set background to loading bitmap
			imageView.setBackgroundDrawable(new BitmapDrawable(mContext
					.getResources(), mLoadingBitmap));

			imageView.setImageDrawable(td);
			td.startTransition(FADE_IN_TIME);
		} else {
			imageView.setImageBitmap(bitmap);
		}
	}

	/**
	 * 设置图片的缓存
	 * 
	 * @param cacheCallback
	 */
	public void setImageCache(ImageCache cacheCallback) {
		mImageCache = cacheCallback;
	}

	/**
	 * 获得图片的缓存
	 * 
	 * @return
	 */
	public ImageCache getImageCache() {
		return mImageCache;
	}

	/**
	 * 设置图片是否淡出
	 * 
	 * @param fadeIn
	 */
	public void setImageFadeIn(boolean fadeIn) {
		mFadeInBitmap = fadeIn;
	}

	/**
	 * 不进行异步操作.如果内存里面没有图片就退出. 要用这个方法的最佳实践就是,先设置图片缓存,这样就可以共用缓存不要进行线程的操作
	 * 就是说,是只读缓存,用于共用一个缓存的时候用
	 * 
	 * @param exitTasksEarly
	 */
	public void setExitTasksEarly(boolean exitTasksEarly) {
		mExitTasksEarly = exitTasksEarly;
	}

	/**
	 * 取消某个imageview 的任务
	 * 
	 * @param imageView
	 */
	public static void cancelWork(ImageView imageView) {
		final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
		if (bitmapWorkerTask != null) {
			bitmapWorkerTask.cancel(true);
			if (BuildConfig.DEBUG) {
				final Object bitmapData = bitmapWorkerTask.data;
				Log.d(TAG, "cancelWork - cancelled work for " + bitmapData);
			}
		}
	}
}
