package com.achai.framework.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class ObjectLoader extends AsyncTaskLoader<Object>{

	public ObjectLoader(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object loadInBackground() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	@Override
	protected void onStartLoading() {
		super.onStartLoading();
		forceLoad();
	}

}
