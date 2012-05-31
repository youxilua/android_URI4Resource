package com.achai.main;

import android.os.Bundle;
import android.widget.TextView;

import com.achai.framework.logs.GraphLog;
import com.achai.framework.logs.GraphivzLogActivity;

public class FirstActivity extends GraphivzLogActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		
		int id = this.getTaskId();
		tv.setText("id:"+id);
		
		setContentView(tv);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		GraphLog.onPause(this);
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		GraphLog.onResume(this);
	}
}
