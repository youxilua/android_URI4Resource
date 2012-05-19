package com.achai.framework.net.fetch;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.achai.framework.cache.CacheUtils;
import com.achai.framework.cache.DiskLruCache;
import com.achai.framework.debug.BuildConfig;

/**
 * @author tom_achai
 * 
 */
public class DataFetch {
	public static final String TAG = "fetch";
	public static final int IO_BUFFER_SIZE = 8 * 1024;

	/**
	 * 获取JSON 对象
	 * 
	 * @param urlString
	 * @return
	 */
	public static JSONObject getJsonData(String urlString) {
		JSONObject result = null;
		String temp = null;
		temp = getStringData(urlString);
		if (temp != null) {
			try {
				result = new JSONObject(temp);
				return result;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获得字符串对象
	 * 
	 * @param urlString
	 * @return
	 */
	public static String getStringData(String urlString) {
		BufferedReader buffer = null;
		StringBuffer tempString = new StringBuffer();
		String readline;
		HttpURLConnection httpConn = null;
		try {

			final URL u = new URL(urlString);
			httpConn = (HttpURLConnection) u.openConnection();

			buffer = new BufferedReader(new InputStreamReader(
					httpConn.getInputStream()));
			while ((readline = buffer.readLine()) != null) {
				tempString.append(readline);
			}
			return tempString.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			httpConn.disconnect();
		}

		return null;
	}

	/**
	 * 下载图片并且加入进 lru 缓存里面
	 * 
	 * @param context
	 * @param urlString
	 * @return
	 */
	public static File dowanLoadBitmap(Context context, String urlString) {
		//创建缓存图片保存目录,默认为内置
		final File cacheDir = DiskLruCache.getDiskCacheDir(context,
				CacheUtils.HTTP_CACHE_DIR);
		
		
		final DiskLruCache cache = DiskLruCache.openCache(context, cacheDir,
				CacheUtils.HTTP_CACHE_SIZE);
		
		final File cacheFile = new File(cache.createFilePath(urlString));
		
//		if (BuildConfig.DEBUG) {
//			Log.d(TAG,cacheFile.toString()+ " - "
//					+ urlString);
//		}

		if (cache.containsKey(urlString)) {
			if (BuildConfig.DEBUG) {
				Log.d(TAG,cacheFile.toString()+ "downloadBitmap - found in http cache - "
						+ urlString);
			}
			return cacheFile;
		}
		
		if(BuildConfig.DEBUG){
			 Log.d(TAG, "downloadBitmap - downloading - " + urlString);
		}
		//针对 2.1 版本 以前的版本
		CacheUtils.disableConnectionReuseIfNecessary();
		
		 HttpURLConnection urlConnection = null;
	     BufferedOutputStream out = null;
	     
	     try {
			final URL url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			final InputStream in = new BufferedInputStream(urlConnection.getInputStream(), CacheUtils.IO_BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(cacheFile), CacheUtils.IO_BUFFER_SIZE);
			CacheUtils.CopyStream(in, out);
			return cacheFile;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(urlConnection != null){
				urlConnection.disconnect();
			}
			if(out != null){
				 try {
	                    out.close();
	                } catch (final IOException e) {
	                    Log.e(TAG, "Error in downloadBitmap - " + e);
	                }
			}
		}

		return null;
	}

}
