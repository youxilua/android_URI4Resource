package com.achai.framework.cache;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

public class CacheUtils {
	public static final int HTTP_CACHE_SIZE = 10 * 1024 * 1024; // 10MB
	public static final String HTTP_CACHE_DIR = "http";
	public static final String SHARED_CACHE_DEFAULT = "string_cache";
	public static final String TRACE_LOGS = "graphviz_log";
	public static final int TRACE_LOGS_SIZE = 10 * 1024 * 1024; // 10MB
	
	//dot 文件
	public static final String DOT_CACHE_DIR = "dot";
	public static final int DOT_CACHE_FILE_SIZE = 10 * 1024; // 1MB
	
	//默认缓存大小
	public static final int DEFAULT_CACHE_FILE_SIZE = 10 * 512; // 512 kb

	// 8k 缓存
	public static final int IO_BUFFER_SIZE = 8 * 1024;

	private CacheUtils() {
	};

	/**
	 * Check how much usable space is available at a given path.
	 * 
	 * @param path
	 *            The path to check
	 * @return The space available in bytes
	 */
	@SuppressLint("NewApi")
	public static long getUsableSpace(File path) {
//		 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
//		 return path.getUsableSpace();
//		 }
		final StatFs stats = new StatFs(path.getPath());
		return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
	}

	/**
	 * Check if external storage is built-in or removable.
	 * 2.3 以后的手机可以区分 内部大容量存储器 和 外部
	 * 内部为不可移除这样更保证我们程序缓存的稳定性
	 * 
	 * 注意! 2.3 以前没这个特性...蛋疼..
	 * @return True if external storage is removable (like an SD card), false
	 *         otherwise.
	 */
	@SuppressLint("NewApi")
	public static boolean isExternalStorageRemovable() {
		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
		// return Environment.isExternalStorageRemovable();
		// }
		// 2.3 以后才支持的参数
		
		//返回false 强制使用外部存储器
		return false;
	}

	/**
	 * Check if OS version has built-in external cache dir method.
	 * 
	 * @return
	 */
	// public static boolean hasExternalCacheDir() {

	// return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	// }
	/**
	 * Get the external app cache directory.
	 * 
	 * @param context
	 *            The context to use
	 * @return The external cache dir
	 */
	@SuppressLint("NewApi")
	public static File getExternalCacheDir(Context context) {
		// android 2.2 以后才支持的特性
		// if (hasExternalCacheDir()) {
		// return context.getExternalCacheDir();
		// }

		// Before Froyo we need to construct the external cache dir ourselves
		// 这里以后可能会出问题?
		final String cacheDir = "/Android/data/" + context.getPackageName()
				+ "/cache/";
		return new File(Environment.getExternalStorageDirectory().getPath()
				+ cacheDir);
	}

	/**
	 * Check if OS version has a http URLConnection bug. See here for more
	 * information:
	 * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
	 * 
	 * @return
	 */
	public static boolean hasHttpConnectionBug() {
		// return Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO;
		return Build.VERSION.SDK_INT <= Build.VERSION_CODES.ECLAIR;
	}

	/**
	 * Workaround for bug pre-Froyo, see here for more info:
	 * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
	 * 2.1 以前的版本你需要手动设置!
	 */
	public static void disableConnectionReuseIfNecessary() {
		// HTTP connection reuse which was buggy pre-froyo
		if (hasHttpConnectionBug()) {
			System.setProperty("http.keepAlive", "false");
		}
	}

	/**
	 * 复制流
	 * 
	 * @param is
	 * @param os
	 */
	public static void CopyStream(InputStream in, OutputStream out) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = in.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				out.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
		
		//实现二
//		  int b;
//          while ((b = in.read()) != -1) {
//              out.write(b);
//          }

	}
	
	 /**
     * Get the size in bytes of a bitmap.
     * @param bitmap
     * @return size in bytes
     */
    @SuppressLint("NewApi")
    public static int getBitmapSize(Bitmap bitmap) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
//            return bitmap.getByteCount();
//        }
        // Pre HC-MR1
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
