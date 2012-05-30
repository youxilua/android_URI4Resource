package com.achai.framework.logs;

import android.support.v4.app.FragmentActivity;

public class GraphivzLogActivity extends FragmentActivity {
	
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
