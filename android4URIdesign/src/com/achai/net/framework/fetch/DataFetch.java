package com.achai.net.framework.fetch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author tom_achai
 * 
 */
public class DataFetch {
	 public static final int IO_BUFFER_SIZE = 8 * 1024;
	
	/**
	 * 获取JSON 对象
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
	
	

}
