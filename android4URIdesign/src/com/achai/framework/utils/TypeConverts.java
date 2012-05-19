package com.achai.framework.utils;

import java.util.Date;

public class TypeConverts {
	public static String ObjectToString(Object o){
		if(o == null)
			return null;
		else if (o instanceof String)
			return (String)o;
		//预留一个处理字符串的
		else if (o instanceof Date)
			return o.toString();
		else
			return o.toString();
	}
}
