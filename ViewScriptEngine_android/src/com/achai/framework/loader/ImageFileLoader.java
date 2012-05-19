package com.achai.framework.loader;

import java.io.File;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class ImageFileLoader extends AsyncTaskLoader<File>{
	
	File f = null;
	
	public ImageFileLoader(Context context) {
		super(context);
	}

	@Override
	public File loadInBackground() {
		return null;
	}
	
	@Override
	protected void onStartLoading() {
		if(f == null){
			forceLoad();
		}
	}

}
