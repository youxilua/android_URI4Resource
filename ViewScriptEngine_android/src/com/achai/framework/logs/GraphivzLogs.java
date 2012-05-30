package com.achai.framework.logs;

import org.json.JSONObject;

public class GraphivzLogs {

	private final JSONObject graphJson;
	
	public GraphivzLogs(JSONObject logJson) {
		// TODO Auto-generated constructor stub
		this.graphJson = logJson;
	}
	
	
	@Override
	public String toString() {
		graphJson.keys();
		
		return super.toString();
	}
}
