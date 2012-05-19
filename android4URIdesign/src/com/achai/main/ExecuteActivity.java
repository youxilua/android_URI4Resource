package com.achai.main;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.achai.framework.R;
import com.achai.framework.command.tag.InitViewTag;
import com.achai.framework.command.tag.TagListener;
import com.achai.framework.interfaces.ExecTagListener;
import com.achai.net.framework.fetch.DataFetch;

public class ExecuteActivity extends Activity implements ExecTagListener{
	//初始化命令
	private InitViewTag initTagCmd = new InitViewTag(this);
	Intent i = new Intent();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
//		checkView();
//		绑定特定的view 的 tag
		Button b = (Button) findViewById(R.id.button4);
		//i.setClassName("com.achai", "com.achai.main.TargetActivity");
		
		View root = getRootView();
		initTagCmd.checkTagView(root);
		
		Map<String, String> viewMap = new HashMap<String, String>();
		viewMap.put("name", "Hello,world!");
		
		String jsonString = "http://api.douban.com/book/subject/1220562?alt=json";
		
		JSONObject doubanJson = DataFetch.getJsonData(jsonString);
		
		TextView tvOK = (TextView) findViewById(R.id.ok);
		
		
		tvOK.setText(doubanJson.toString());
		
		
		//Toast.makeText(this, NetConnUtils.getNetWorkType(this), Toast.LENGTH_LONG).show();
		
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
		new TagListener(this).doViewClicked(v);
	}


}
