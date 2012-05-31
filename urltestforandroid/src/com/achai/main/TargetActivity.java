package com.achai.main;

import android.os.Bundle;
import android.widget.TextView;

import com.achai.framework.command.tag.InitViewTag;
import com.achai.framework.logs.GraphLog;
import com.achai.framework.logs.GraphivzLogActivity;

public class TargetActivity extends GraphivzLogActivity {
	//初始化命令
		private InitViewTag initTagCmd = new InitViewTag(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		Bundle b = getIntent().getExtras();
//		Map<String, String> getMap = new HashMap<String, String>();
//		String s2 = "";
//		for(String s : b.keySet()){
//			 getMap.put(s, b.getString(s));
//		}
		TextView tv = new TextView(this);
	//	tv.setText(getMap.get("id"));
		tv.setText("hello"+getTaskId());
		
		tv.setTag("act://com.achai@com.achai.main.FirstActivity");
		
		initTagCmd.checkTagView(tv);
		setContentView(tv);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		GraphLog.onResume(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		GraphLog.onPause(this);
	}
}
