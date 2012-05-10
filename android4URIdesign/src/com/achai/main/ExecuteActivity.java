package com.achai.main;


import com.achai.R;
import com.achai.command.tag.InitViewTag;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ExecuteActivity extends Activity{
	//初始化命令
	private InitViewTag initTagCmd = new InitViewTag(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//checkView();
		Button b = (Button) findViewById(R.id.button3);
		View root = getRootView();
		initTagCmd.checkTagView(b);
	}
	

	
	public View getRootView() {
		return findViewById(android.R.id.content);
	}


}
