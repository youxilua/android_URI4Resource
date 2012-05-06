package com.achai.main;


import com.achai.R;
import com.achai.command.InitViewCmd;


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
		//checkView();
		View root = getRootView();
		initTagCmd.checkTagView(root);
	}
	

	
	public View getRootView() {
		return findViewById(android.R.id.content);
	}


}
