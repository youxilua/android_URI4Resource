package com.achai.main;


import com.achai.R;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ExecuteActivity extends Activity{
	//初始化命令
	private InitViewCmd initTagCmd = new InitViewCmd(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		checkView();
	}
	
	public void checkView(){
		
		ViewGroup vg = (ViewGroup) findViewById(R.id.line);
		
		
		int count = vg.getChildCount();
		for(int i=0; i < count; i ++){
			View child = vg.getChildAt(i);
			String tag = (String) child.getTag();
			if(tag != null){
				initTagCmd.setViewTagListenr(child);
				if(child instanceof TextView){
					((TextView) child).setText(tag);
				}
			}
		}
	}
	
	public View getRootView() {
		return findViewById(android.R.id.content);
	}


}
