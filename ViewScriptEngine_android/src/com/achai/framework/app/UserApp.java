package com.achai.framework.app;

import java.io.File;

import com.achai.framework.cache.CacheUtils;
import com.achai.framework.cache.DiskLruCache;
import com.achai.framework.cache.sharedprefences.SharedCacheFactory;
import com.achai.framework.logs.GraphLog;
import com.achai.framework.utils.FileUtils;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager.OnActivityDestroyListener;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

public class UserApp extends Application {

	public static UserApp curApp() {
		return curApp(null);
	}

	public static UserApp activeApp = null;

	private static UserApp curApp(Context ctx) {
		if (ctx != null) {
			if (activeApp == null) {
				activeApp = (UserApp) ctx.getApplicationContext();
			}
		}
		if (activeApp == null) {
			throw new RuntimeException("当前 application 未初始化");
		}
		return activeApp;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		this.init();
	}

	public static SharedCacheFactory sharedCache;
	

	private void init() {
		activeApp = this;
		sharedCache = SharedCacheFactory.getInstance();
		// 进来的时候把写进sharedpreferences 的值获取,转换成一个 graphviz 的log
		String taskId = sharedCache.getCache(this, "taskid");
		if (taskId != null) {
			File taskFile = FileUtils.getCacheFile(this, CacheUtils.TRACE_LOGS,
					taskId);
			if (taskFile != null) {
				Log.d("fetch", "找到需要生成DOT 的文件");
				String content = FileUtils.getStringFromFile(taskFile);
				String result = GraphLog.generedDotfile(content,taskId);
				SparseArray<String> fileInfo = new SparseArray<String>();
				// 文件夹
				fileInfo.put(0, CacheUtils.DOT_CACHE_DIR);
				// 文件名
				fileInfo.put(1, taskId);
				// 结果
				fileInfo.put(2, result);
				FileUtils.writeToCacheFile(this, fileInfo);
				isTrace = true;
				sharedCache.setSharedCache(this, "taskid", null);
			}

		}

	}

	public static boolean isTrace = true;

	public String getCurPackageName() {
		return getPackageName();
	}

	/**
	 * 显示全局变量的
	 * 
	 * @param message
	 */
	public void showMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 
	 * @param ctx
	 * @param msg
	 */
	public static void showMessage(Context ctx, String msg) {
		Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
	}

}
