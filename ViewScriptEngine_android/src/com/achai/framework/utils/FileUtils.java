package com.achai.framework.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.achai.framework.cache.CacheUtils;
import com.achai.framework.cache.DiskLruCache;

import android.content.Context;
import android.util.SparseArray;

public class FileUtils {
	public static String getStringFromFile(File dotfile) {
		BufferedReader buffer = null;
		StringBuffer sb = new StringBuffer();
		String line = null;
		try {
			buffer = new BufferedReader(new InputStreamReader(
					new FileInputStream(dotfile)));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void outFile(File logout, String target) {
		OutputStream taskLogOut = null;
		try {
			taskLogOut = new FileOutputStream(logout, true);
			byte[] bytes = target.getBytes();
			for (int i = 0; i < bytes.length; i++) {
				taskLogOut.write(bytes[i]);
			}
			taskLogOut.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (taskLogOut != null) {
				try {
					taskLogOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * 0 -> dir 1 -> filename 2 -> result
	 * 
	 * @param ctx
	 * @param fileInfo
	 * @param size
	 */
	public static void writeToCacheFile(Context ctx,
			SparseArray<String> fileInfo, int size) {
		final File cacheDir = DiskLruCache
				.getDiskCacheDir(ctx, fileInfo.get(0));
		final DiskLruCache cache = getDiskLruCache(ctx, cacheDir, size);
		final File cacheFile = new File(cache.createFilePath(fileInfo.get(1)));
		outFile(cacheFile, fileInfo.get(2));

	}
	
	public static void writeToCacheFile(Context ctx, SparseArray<String> fileInfo){
		writeToCacheFile(ctx, fileInfo, -1);
	}

	private static DiskLruCache getDiskLruCache(Context ctx, File cacheDir,
			int size) {
		final DiskLruCache cache;
		if (size != -1) {
			cache = DiskLruCache.openCache(ctx, cacheDir, size);
		} else {
			cache = DiskLruCache.openCache(ctx, cacheDir,
					CacheUtils.DEFAULT_CACHE_FILE_SIZE);
		}
		return cache;
	}

	public static File getCacheFile(Context ctx, String dir, String filename,
			int size) {
		final File cacheDir = DiskLruCache.getDiskCacheDir(ctx, dir);
		final DiskLruCache cache = getDiskLruCache(ctx, cacheDir, size);
		final File cacheFile = new File(cache.createFilePath(filename));
		if (cache.containsKey(filename)) {
			return cacheFile;
		}
		return null;
	}

	public static File getCacheFile(Context ctx, String dir, String filename) {
		return getCacheFile(ctx, dir, filename, -1);
	}

}
