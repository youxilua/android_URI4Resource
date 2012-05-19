package com.achai.framework.cache;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.achai.framework.debug.BuildConfig;

/**
 * 内存缓存
 * 
 * @author tom_achai
 * 
 */
public class ImageCache {

	private static final String TAG = "ImageCache";

	// Default memory cache size
	private static final int DEFAULT_MEM_CACHE_SIZE = 1024 * 1024 * 5; // 5MB

	// Default disk cache size
	private static final int DEFAULT_DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB

	// Compression settings when writing images to disk cache
	private static final CompressFormat DEFAULT_COMPRESS_FORMAT = CompressFormat.JPEG;
	private static final int DEFAULT_COMPRESS_QUALITY = 70;

	// Constants to easily toggle various caches
	private static final boolean DEFAULT_MEM_CACHE_ENABLED = true;
	private static final boolean DEFAULT_DISK_CACHE_ENABLED = true;
	private static final boolean DEFAULT_CLEAR_DISK_CACHE_ON_START = false;

	private DiskLruCache mDiskCache;
	private LruCache<String, Bitmap> mMemoryCache;

	/**
	 * Creating a new ImageCache object using the specified parameters.
	 * 
	 * @param context
	 *            The context to use
	 * @param cacheParams
	 *            The cache parameters to use to initialize the cache
	 */
	public ImageCache(Context context, ImageCacheParams cacheParams) {
		init(context, cacheParams);
	}

	public ImageCache(Context context, String uniqueName) {
		init(context, new ImageCacheParams(uniqueName));
	}
	
	/**
	 * 暂时想不到怎么改...
	 * @return
	 */
	public static ImageCache findOrCreateCache(){
		return null;
	}
	
	
	

	/**
	 * 初始化图片缓存模块
	 * @param context
	 * @param cacheParams
	 */
	private void init(Context context, ImageCacheParams cacheParams) {
		//获得缓存目录
		final File diskCacheDir = DiskLruCache.getDiskCacheDir(context, cacheParams.uniqueName);
		
		// Set up disk cache
        if (cacheParams.diskCacheEnabled) {
            mDiskCache = DiskLruCache.openCache(context, diskCacheDir, cacheParams.diskCacheSize);
            mDiskCache.setCompressParams(cacheParams.compressFormat, cacheParams.compressQuality);
            if (cacheParams.clearDiskCacheOnStart) {
                mDiskCache.clearCache();
            }
        }
        
        // Set up memory cache
        if (cacheParams.memoryCacheEnabled) {
            mMemoryCache = new LruCache<String, Bitmap>(cacheParams.memCacheSize) {
                /**
                 * Measure item size in bytes rather than units which is more practical for a bitmap
                 * cache
                 */
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return CacheUtils.getBitmapSize(bitmap);
                }
            };
        }
	}
	
    /**
     * 添加一张图片到内存当中
     * @param data
     * @param bitmap
     */
    public void addBitmapToCache(String data, Bitmap bitmap) {
        if (data == null || bitmap == null) {
            return;
        }

        // Add to memory cache
        if (mMemoryCache != null && mMemoryCache.get(data) == null) {
            mMemoryCache.put(data, bitmap);
        }

        // Add to disk cache
        if (mDiskCache != null && !mDiskCache.containsKey(data)) {
            mDiskCache.put(data, bitmap);
        }
    }
	
    
    /**
     * Get from memory cache.
     *
     * @param data Unique identifier for which item to get
     * @return The bitmap if found in cache, null otherwise
     */
    public Bitmap getBitmapFromMemCache(String data) {
        if (mMemoryCache != null) {
            final Bitmap memBitmap = mMemoryCache.get(data);
            if (memBitmap != null) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "Memory cache hit");
                }
                return memBitmap;
            }
        }
        return null;
    }
    
    /**
     * Get from disk cache.
     *
     * @param data Unique identifier for which item to get
     * @return The bitmap if found in cache, null otherwise
     */
    public Bitmap getBitmapFromDiskCache(String data) {
        if (mDiskCache != null) {
            return mDiskCache.get(data);
        }
        return null;
    }
    
    public void clearCaches() {
        mDiskCache.clearCache();
        mMemoryCache.evictAll();
    }
	/**
	 * A holder class that contains cache parameters.
	 */
	public static class ImageCacheParams {
		public String uniqueName;
		public int memCacheSize = DEFAULT_MEM_CACHE_SIZE;
		public int diskCacheSize = DEFAULT_DISK_CACHE_SIZE;
		public CompressFormat compressFormat = DEFAULT_COMPRESS_FORMAT;
		public int compressQuality = DEFAULT_COMPRESS_QUALITY;
		public boolean memoryCacheEnabled = DEFAULT_MEM_CACHE_ENABLED;
		public boolean diskCacheEnabled = DEFAULT_DISK_CACHE_ENABLED;
		public boolean clearDiskCacheOnStart = DEFAULT_CLEAR_DISK_CACHE_ON_START;

		public ImageCacheParams(String uniqueName) {
			this.uniqueName = uniqueName;
		}
	}

}
