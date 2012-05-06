package com.achai.main;

import java.net.URI;

import android.view.View;
import android.view.View.OnClickListener;

import com.achai.app.UserApp;
import com.achai.command.CmdListener.TagOnClickEvents;

public class InitViewCmd {
	private final ExecuteActivity theAct;
	protected TagOnClickEvents tagClickEvent;
	
	public InitViewCmd(ExecuteActivity act){
		this.theAct = act;
		tagClickEvent = new TagOnClickEvents();
		tagClickEvent.clickEvent = new OnClickListener() {
			@Override
			public void onClick(View v) {
				doViewClicked(v);
			}
		};
	
	}
	
	public void doViewClicked(View v){
		String url = v.getTag().toString().trim();
		if(v != null && url != null){
			//对view 中tag 进行解析
			doExecUrl(v, url);
		}
	}
	
	protected void doExecUrl(View view, String url) {
		try{
			if(url.indexOf('\n') > 0){
				String[] urls = url.split("\n");
				for(String u : urls){
					execUrl(view, u);
				}
			}else{
				execUrl(view, url);
			}
		}catch(RuntimeException ex){
			UserApp.curApp().showMessage("url 解析错误");
		}
	}

	private void execUrl(View view, String u) {
		URI execUri = URI.create(u);
		if(execUri == null)
			return;
		String prefix = execUri.getScheme();
		//执行相关的的命令
		if(prefix.equals("cmd")){
			execCmd(execUri);
		}else if(prefix.equals("act")){
			execAct(execUri);
		}
			
	}
	
	/**
	 * 执行命令操作
	 * @param u
	 */
	private void execCmd(URI u){
		String type = u.getHost();
		
		//监控 watch
		if(type.equals("watch")){
			//用于观察view 中的变量改变情况,晚些在实现
			return;
		}
		
		//结束当前view
		if(type.equals("finish")){
			theAct.finish();
			return;
		}
		
		//弹出提示
		if(type.equals("hint")){
			String msg = u.getFragment();
			if(msg != null){
				UserApp.showMessage(theAct, msg);
			}
			return;
		}
		//重新读取
		if(type.equals("reload")){
			return;
		}
		//设置指定id view 的值
		if(type.equals("setview")){
			return;
		}
		//设置显示某个view
		if(type.equals("showview")){
			return;
		}
	}
	
	/**
	 * 执行动作操作
	 * @param u
	 */
	private void execAct(URI u){
		
	}
	
	/**
	 * 设置  view  的监听
	 * @param v
	 */
	public void setViewTagListenr(View v){
		v.setOnClickListener(tagClickEvent.clickEvent);
	}
}
