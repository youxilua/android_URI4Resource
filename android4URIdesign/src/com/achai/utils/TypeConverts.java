package com.achai.utils;

public class TypeConverts {
	public static String ObjectToString(Object o){
		if(o == null)
			return null;
		else if (o instanceof String)
			return (String)o;
		else
			return o.toString();
	}
}
