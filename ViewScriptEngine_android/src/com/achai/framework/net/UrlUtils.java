package com.achai.framework.net;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.http.protocol.HTTP;

public class UrlUtils {
	public static String urlEncode(String url){
		return urlEncodeEnc(url, HTTP.UTF_8);
	}
	
	/**
	 * url 编码方法
	 * @param url
	 * @param enc
	 * @return
	 */
	public static String urlEncodeEnc(String url, String enc){
		if(url == null)
			return "";
		try {
			url = URLEncoder.encode(enc, enc);
			return url;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * URL 解码方法
	 * @param url
	 * @return
	 */
	public static String urlDecode(String url){
		return urlDecode(url, HTTP.UTF_8);
	}
	
	public static String urlDecode(String url, String enc){
		if(url == null)
			return "";
		try {
			url = URLDecoder.decode(url, enc);
			return url;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获取一个需要认证key 的URL
	 * @param url
	 * @return
	 */
	public static String getAuthKey(String url){
		return null;
	}
}
