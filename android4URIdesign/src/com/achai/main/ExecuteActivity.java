package com.achai.main;


import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.achai.R;
import com.achai.command.tag.InitViewTag;
import com.achai.command.tag.TagListener;
import com.achai.interfaces.ExecTagListener;

public class ExecuteActivity extends Activity implements ExecTagListener{
	//初始化命令
	private InitViewTag initTagCmd = new InitViewTag(this);
	Intent i = new Intent();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//checkView();
		//绑定特定的view 的 tag
//		Button b = (Button) findViewById(R.id.button4);
//		//i.setClassName("com.achai", "com.achai.main.TargetActivity");
//		
//		View root = getRootView();
//		initTagCmd.checkTagView(root);
//		
//		Map<String, String> viewMap = new HashMap<String, String>();
//		viewMap.put("name", "Hello,world!");
		
//		
//		b.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				startActivity(i);
//			}
//		});
	}
	

	
	public View getRootView() {
		return findViewById(android.R.id.content);
	}



	@Override
	public void execTags(View v) {
		new TagListener(this).doExecUrl(v, v.getTag().toString());
	}


}
