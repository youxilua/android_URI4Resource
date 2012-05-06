package com.achai.app;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class UserApp extends Application {
	
	public static UserApp curApp(){
		return curApp(null);
	}
	protected static UserApp activeApp = null;
	
	
	private static UserApp curApp(Context ctx){
		if(ctx != null){
			if(activeApp == null){
				activeApp = (UserApp) ctx.getApplicationContext();
			}
		}
		if(activeApp == null){
			throw new RuntimeException("当前 application 未初始化");
		}
		return activeApp;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		this.init();
	}
	
	private void init(){
		activeApp = this;
	}
	
	public void showMessage(String message){
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	public static void showMessage(Context ctx, String msg){
		Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
	}
}