package com.achai.utils;

import java.net.URI;

import com.achai.main.UserApp;

import android.content.res.Resources;

public class AnalyseViewURI {
	private static Resources ress = null;
	private static String pkgName = "";
	private String resType = "";
	
	private static void initRes(){
		if(ress == null){
			if(UserApp.curApp() != null){
				ress = UserApp.curApp().getResources();
				pkgName = UserApp.curApp().getPackageName();
			}
		}
	}
	
	
	
	/**
	 * 资源ID 的几种形式
	 * res = package:type/entry
	 * 1,res://com.achai:drawable/test_icon
	 * 2,res://drawable/test_icon
	 *(1) 不带具体包名
	 *entry = layout/main
	 *(2) 指定包名和类型实体
	 *com.achai:drawable/icon
	 * @param url
	 * @return
	 */
	public static int getResIdFromURL(String url){
		URI uri = URI.create(url);
		String scheme = uri.getScheme();
		String entry =	uri.getPath();
		entry = entry.replaceFirst("/", "");
		int port = uri.getPort();
		if(ress == null)
			initRes();
		if(ress == null)
			return -1;
		//判断是否资源URL
		if(!scheme.equals("res"))
			return -1;
		//1,判断是否是带包名的uri,并执行转换,获得资源ID
		if(port != -1){
			String pkg = uri.getHost();
			String type = getType(port);
			return ress.getIdentifier(entry, type, pkg);
		}else{
			String type = uri.getHost();
			return ress.getIdentifier(entry, type, pkgName);
		}
	}
	
	private static String getType(int port){
		switch (port) {
		case 1:
			return "layout";
		default:
			break;
		}
		return null;
	}
}
