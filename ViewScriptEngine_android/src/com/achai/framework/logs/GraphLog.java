package com.achai.framework.logs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import com.achai.framework.app.UserApp;
import com.achai.framework.cache.CacheUtils;
import com.achai.framework.cache.DiskLruCache;
import com.achai.framework.utils.FileUtils;

public class GraphLog {

	private static boolean start = false;
	private static boolean end = true;

	// session 生成器
	protected static SessionManager generSession = new SessionManager();

	private static JSONArray taskInfoArray = new JSONArray();

	private static String sourceName = null;
	private static String distName = null;
	
	
	/**
	 * 
	 * 开始点
	 * @param source
	 */
	public static void onPause(Activity source) {
		//暂时没用上
//		String session = generSession.generateSessionId();
		if (end) {
			//初始化任务队列
		//	UserApp.sharedCache.getCache(source, "taskid");
			if(UserApp.isTrace){
				Log.d("taskfetch", "start");
				String taskId =String.valueOf(source.getTaskId());
				UserApp.sharedCache.setSharedCache(source,"taskid", taskId);
				UserApp.isTrace = false;
			}
			ComponentName sourceInfo = source.getComponentName();
			sourceName = sourceInfo.getClassName();
			int sourceTaskId = source.getTaskId();
			Log.d("fetch", "sourcename->" + sourceName + ":" + sourceTaskId);
			start = true;
			end = false;
		}
	}

	/**
	 * 结束点
	 * @param dist
	 */
	public static void onResume(Activity dist) {
		if (start) {
			// 获得 activity 的任务id值
			int distTaskId = dist.getTaskId();
			ComponentName distInfo = dist.getComponentName();
			distName = distInfo.getClassName();
		
			Map<String, String> taskInfo = new HashMap<String, String>();
			taskInfo.put("source", sourceName);
			taskInfo.put("distname", distName);
			writeTofile(dist, String.valueOf(distTaskId), taskInfo);
			Log.d("fetch", sourceName + "->" + distName);
			// 把信息存到json Array 里面
			Log.d("taskfetch", taskInfoArray.toString());
			start = false;
			end = true;
		}
	}

	/**
	 * 准备放到Fileutls 里面解决
	 * @param ctx
	 * @param taskId
	 * @param taskInfo
	 */
	@Deprecated
	protected static void writeTofile(Context ctx, String taskId,
			Map<String, String> taskInfo) {
		final File logDir = DiskLruCache.getDiskCacheDir(ctx, CacheUtils.TRACE_LOGS);
		final DiskLruCache logCache = DiskLruCache.openCache(ctx, logDir,
				CacheUtils.TRACE_LOGS_SIZE);
		final File logCacheFile = new File(logCache.createFilePath(taskId));
		String target = '"'+taskInfo.get("source")+'"' + "->"
				+ '"'+taskInfo.get("distname")+'"' +"\n";
		FileUtils.outFile(logCacheFile, target);
	}
	
	/**
	 * 简单组装一个字符串,以后优化!
	 * @param content
	 * @return
	 */
	public static String generedDotfile(String content, String taskid){
		String head = "digraph G{\n"+"label="+'"'+"taskId:"+taskid+'"'+"\n";
		String foot = "}";
		String result = head + content + foot;
		return result;
	}
}
