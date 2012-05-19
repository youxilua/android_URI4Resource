package com.achai.framework.loader;

import org.json.JSONObject;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class JsonLoader extends AsyncTaskLoader<JSONObject> {

	private JSONObject jsonData;

	public JsonLoader(Context context) {
		super(context);
	}

	/*
	 * 2
	 * 
	 * 后台读取
	 */
	@Override
	public JSONObject loadInBackground() {
		return null;
	}

	/*
	 * 3
	 */
	@Override
	public void deliverResult(JSONObject data) {
		if (isReset()) {
			// An async query came in while the loader is stopped. We
			// don't need the result.
			if (jsonData != null) {
				// onReleaseResources(apps);
			}
		}
		if (isStarted()) {
			super.deliverResult(data);
		}

	}

	/*
	 * 1
	 */
	@Override
	protected void onStartLoading() {
		super.onStartLoading();
		if(jsonData != null){
			deliverResult(jsonData);
		}
		forceLoad();
	}

}
