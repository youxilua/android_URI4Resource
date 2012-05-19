package com.achai.framework.net;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;


import android.util.Log;


/**
 *网络连接管理
 * @author tom_achai
 *
 */
public class NetConnGc {
	public static List<String> recentNetLogs = new ArrayList<String>();
	public static String lastNetResult = "";
	public static boolean netReqLogEnabled = true;
	
	/**
	 * 
	 * 网络连接信息类
	 * @author tom_achai
	 *
	 */
	public static class NetReqInfo {
		//连接地址
		String url;
		//连接对象
		Object netobj;
		//执行线程
		Thread thr;

		public void stop() {
			if (netobj instanceof HttpURLConnection) {
				Log.d("net", "kill http " + url);
				HttpURLConnection http = (HttpURLConnection) netobj;
				http.disconnect();
				thr.interrupt();
			}
		}
	}
	
	//自动释放网络对象
	private static List<NetReqInfo> autoGcNetObjs = new ArrayList<NetReqInfo>();
	
	/**
	 * 把网络连接添加的回收机制当中
	 * @param url
	 * @param obj
	 * @param thr
	 */
	public static synchronized void addautoGcNetObjs(String url, Object obj, Thread thr){
		int index = -1;
		
		// 检查添加的对象是否已经在自动回收里面
		for(int i = 0; i < autoGcNetObjs.size(); i++){
			if(autoGcNetObjs.get(i).netobj == obj){
				index = i;
				break;
			}
		}
		
		//如果没有则添加进去
		if(index == -1){
			NetReqInfo info = new NetReqInfo();
			info.url = url;
			info.netobj = obj;
			info.thr = thr;
			autoGcNetObjs.add(info);
		}
	}
	
	/**
	 * 释放自动回收对象
	 * @param obj
	 */
	public static synchronized void removeAutoGcNetObj(Object obj){
		int index = -1;
		
		for(int i = 0; i < autoGcNetObjs.size(); i++){
			if(autoGcNetObjs.get(i).netobj == obj){
				index = i;
				break;
			}
		}
		
		if(index >= 0)
			autoGcNetObjs.remove(index);
	}
	
	/**
	 * 释放全部网络连接对象
	 */
	public static synchronized void gcNetObj(){
		try{
			for(NetReqInfo netInfo : autoGcNetObjs){
				try{
					netInfo.stop();
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
			autoGcNetObjs.clear();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	/**
	 * 释放特定url
	 * @param url
	 */
	public static synchronized void stopNetUrl(String url){
		try{
			for(int i = 0; i < autoGcNetObjs.size(); i++){
				if(autoGcNetObjs.get(i).url.equals(url)){
					try{
						autoGcNetObjs.get(i).stop();
					}catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	
	
	
	
}
