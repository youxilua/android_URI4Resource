package com.achai.command.tag;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 检查 tag 里面的命令行数
 * @author tom_achai
 *
 */
public class TagCheck {
	
	public static boolean needSplit(String tag){
		if(tag.indexOf('\n') > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isSetViewCmd(URI tag){
		if(tag != null){
			if(tag.getScheme().equals("view")){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	public static boolean isCmd(URI tag){
		if(tag != null){
			if(tag.getScheme().equals("cmd")){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	public static boolean isAct(URI tag){
		if(tag != null){
			if(tag.getScheme().equals("act")){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	public static Map<String,String> getMap(String query){
		Map<String, String> qMap = new HashMap<String, String>();
		String[] temp = query.split("&");
		for (String string : temp) {
			String[] keyAndValue = string.split("=");
			//去除参数中带有的空白
			qMap.put(keyAndValue[0].trim(), keyAndValue[1].trim());
		}
		return qMap;
	}
	
	
}
