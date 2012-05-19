package com.achai.framework.command.tag;

import java.net.URI;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.achai.framework.command.tag.TagListener.TagOnClickEvents;

public class InitViewTag {
	private final Activity theAct;
	private final TagListener tagListener;
	private final TagOnClickEvents tagClickEvent;
	public InitViewTag(Activity act){
		this.theAct = act;
		tagListener = new TagListener(act);
		tagClickEvent = new TagOnClickEvents();
		//设置监听
		tagClickEvent.clickEvent = new OnClickListener() {
			@Override
			public void onClick(View v) {
				tagListener.doViewClicked(v);
			}
		};
	}
	/**
	 * 遍历view 中的元素,检查是否有tag 命令
	 * @param v
	 */
	public void checkTagView(View v){
		if(v == null)
			return;
		String initTag = (String) v.getTag();
		//根据tag 命令初始化 view的状态
		if(initTag != null){
			if(v.getTag() instanceof String){
				execTagInitCmd(theAct, v, initTag);
			}
		}
		if(v instanceof ViewGroup){
			ViewGroup vg = (ViewGroup) v;
			int count = vg.getChildCount();
			for(int i=0; i < count; i++){
				View childView = vg.getChildAt(i);
				checkTagView(childView);
			}
		}
		
	}
	
	/**
	 * 利用tag 初始化view 的状态
	 * @param act
	 * @param view
	 */
	private void execTagInitCmd(Activity act, View view, String initTag){
		//计算tag 里面要执行几行命令
		if(TagCheck.needSplit(initTag)){
			String [] tags = initTag.split("\n");
			for (String tag : tags) {
				initViewAttribute(view, tag);
			}
		}else{
				initViewAttribute(view, initTag);
		}
	}
	

	
	/**
	 * 执行view 的初始化!
	 * @param v
	 * @param tag
	 */
	private void initViewAttribute(View v,String tag){
		URI tagUri = URI.create(tag);
		if(tagUri == null)
			return;
		//设置tag中带有view:// 
		if(TagCheck.isSetViewCmd(tagUri)){
			TagSetView.setView(v,tagUri);
			return;
		}
		
		//为view设置 cmd://命令
		if(TagCheck.isCmd(tagUri)){
			TagSetView.setTagClickListener(v, tagClickEvent);
			return;
		}
		
		//设置跳转activity 的命令
		if(TagCheck.isAct(tagUri)){
			TagSetView.setTagClickListener(v, tagClickEvent);
			return; 
		}
		
		//设置向服务器Post数据模块
		if(TagCheck.isPost(tagUri)){
			TagSetView.setTagClickListener(v, tagClickEvent);
			return;
		}
		

	}
}
