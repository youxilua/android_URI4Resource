package com.achai.framework.command.tag;

import java.net.URI;

import com.achai.framework.command.tag.TagListener.TagOnClickEvents;

import android.view.View;
import android.widget.TextView;



/**
 * 通过tag 的uri 参数 改变view 的显示和设置tag 的监听事件
 * @author tom_achai
 *
 */
public class TagSetView {
	
	/**
	 * 通过tag的 uri 设置view的参数
	 * @param v
	 * @param tagUri
	 */
	protected static void setView(View v,URI tagUri){
		String attribute = tagUri.getHost().toLowerCase();
		String property = tagUri.getFragment().toLowerCase();
		if(attribute != null & property != null){
			setAttribute(v, attribute, property);
		}
		
	}
	
	protected static void setView(View v, String attribute, String param) {
		setAttribute(v, attribute, param);
	}
	
	
	
	/**
	 * 设置  view  的监听
	 * @param v
	 */
	protected static void setTagClickListener(View v,TagOnClickEvents tagListener){
		v.setOnClickListener(tagListener.clickEvent);
	}
	

	
	/**
	 * 设置view的属性
	 * @param v
	 * @param attribute
	 * @param property
	 */
	private static void setAttribute(View v,String attribute, String param){
		
		//1,支持view 是否显示
		if(attribute.equals("visibility")){
			if(param.equals("visible")){
				v.setVisibility(View.VISIBLE);
				return;
			}else if(param.equals("invisible")){
				v.setVisibility(View.INVISIBLE);
				return;
			}else if(param.equals("gone")){
				v.setVisibility(View.GONE);
				return;
			}else{
				return;
			}
		}
		//2,支持设置 view text
		if(attribute.equals("text")){
			setViewText(v, param);
		}
		
		
	}
	
	private static void setViewText(View v,String param) {
		((TextView) v).setText(param);
	}
	
	
}

