package com.achai.framework.cache.sharedprefences;

import com.achai.framework.cache.CacheUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedCacheFactory {

	private static SharedCacheFactory instance = null;

	public static SharedCacheFactory getInstance() {
		if (instance == null) {
			// 要动手写一个日志系统才行!
			Log.d("fetch", "创建了一个对象" + System.currentTimeMillis());
			instance = new SharedCacheFactory();
		}
		return instance;
	}

	/**
	 * 静态工厂不让从外部实例!
	 */
	private SharedCacheFactory() {

	}

	public void setSharedCache(Context ctx, CustomSharedInfo sharedInfo) {
		if (sharedInfo != null) {
			setSharedCache(ctx, sharedInfo.sharedName, sharedInfo.key,
					sharedInfo.value);
		}
	}

	private void setSharedCache(Context ctx, String sharedName, String key,
			Object value) {
		if (sharedName != null) {

		} else {
			SharedPreferences spCache = ctx.getSharedPreferences(
					CacheUtils.SHARED_CACHE_DEFAULT, 0);
			insertCache(spCache, key, value);

		}
	}
	
	public boolean haveCache(Context ctx,String key){
	//	SharedPreferences cache = ctx.getSharedPreferences(CacheUtils.SHARED_CACHE_DEFAULT, 0);
	//	String 
		
		return false;
	}
	
	/**
	 * 暂时统一返回字符串吧
	 * @param ctx
	 * @param key
	 * @return
	 */
	public String getCache(Context ctx, String key){
		SharedPreferences cache = ctx.getSharedPreferences(CacheUtils.SHARED_CACHE_DEFAULT, 0);
		
		String cacheString = cache.getString(key, null);
		return cacheString;
	
	}

	private void insertCache(SharedPreferences spCache, String key, Object value) {
		SharedPreferences.Editor insertCache = spCache.edit();

		if (value instanceof String) {
			insertCache.putString(key, (String) value);
			insertCache.commit();
			return;
		} else if (value instanceof Integer) {
			insertCache.putInt(key, (Integer) value);
			insertCache.commit();
			return;
		} else if (value instanceof Long) {
			insertCache.putLong(key, (Long) value);
			insertCache.commit();
			return;
		} else if (value instanceof Float) {
			insertCache.putFloat(key, (Float) value);
			insertCache.commit();
			return;
		} else if (value instanceof Boolean) {
			insertCache.putBoolean(key, (Boolean) value);
			insertCache.commit();
			return;
		}
	}

	public void setSharedCache(Context ctx, String key, Object value) {
		setSharedCache(ctx, null, key, value);
	}

	public static class CustomSharedInfo {
		public String sharedName = CacheUtils.SHARED_CACHE_DEFAULT;
		public String key;
		public String value;
	}
}
