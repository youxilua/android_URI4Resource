package com.achai.framework.deviceinfo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * 需要权限
 *  <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 * 
 * @author tom_achai
 *
 */
public class DevicesNetInfo {
	

	/**
	 * 需要:ACCESS_NETWORK_STATE 权限 获取接入点
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getNetWorkType(Context ctx) {
		String connType = "";
		ConnectivityManager connManager = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager == null)
			return "";

		NetworkInfo info = connManager.getActiveNetworkInfo();

		if (info == null)
			return connType;

		if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
			connType = info.getExtraInfo().toLowerCase();
		} else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
			connType = "wifi";
		} else {
			connType = "";
		}

		return connType;
	}

	/**
	 * 检查是否处于联网状态
	 * @param ctx
	 * @return
	 */
	public static boolean isOnline(Context ctx) {
		ConnectivityManager connMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());

	}
	
    /**
     * Simple network connection check.
     *
     * @param context
     */
    public static void checkConnection(Context context) {
        final ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            Toast.makeText(context, "No network connection found.", Toast.LENGTH_LONG).show();
        }
    }
}
