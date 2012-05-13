package com.achai.main;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class TargetActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle b = getIntent().getExtras();
		Map<String, String> getMap = new HashMap<String, String>();
		String s2 = "";
		for(String s : b.keySet()){
			 getMap.put(s, b.getString(s));
		}
		TextView tv = new TextView(this);
		tv.setText(getMap.get("id"));
	
		setContentView(tv);
		
	}
}
