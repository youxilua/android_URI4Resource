package com.achai.main;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.achai.R;
import com.achai.framework.app.UserApp;
import com.achai.framework.cache.CacheUtils;
import com.achai.framework.command.tag.InitViewTag;
import com.achai.framework.command.tag.TagListener;
import com.achai.framework.interfaces.ExecTagListener;
import com.achai.framework.logs.GraphivzLogActivity;

public class ExecuteActivity extends GraphivzLogActivity implements ExecTagListener{
	//初始化命令
	private InitViewTag initTagCmd = new InitViewTag(this);
	Intent i = new Intent();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
		
		
//		checkView();
//		绑定特定的view 的 tag
//		Button b = (Button) findViewById(R.id.button4);
//		//i.setClassName("com.achai", "com.achai.main.TargetActivity");
//		
		View root = getRootView();
		//initTagCmd.checkTagView(root);
	//	Intent targetIntent = new Intent().setClass(this, TargetActivity.class);
//		ComponentName cn = new ComponentName("com.achai", "com.achai.main.TargetActivity");
//		targetIntent.setComponent(cn);
		
	//	startActivity(targetIntent);
//		
//		Map<String, String> viewMap = new HashMap<String, String>();
//		viewMap.put("name", "Hello,world!");
//		
//		String jsonString = "http://api.douban.com/book/subject/1220562?alt=json";
//		
//		JSONObject doubanJson = DataFetch.getJsonData(this, jsonString);
//		
//		String imgString = "http://t.douban.com/spic/s1747553.jpg";
//		
//		File imgFile = DataFetch.dowanLoadBitmap(this, imgString);
//		ImageView iv = (ImageView) findViewById(R.id.imageView1);
//		try {
//			Bitmap img = BitmapFactory.decodeStream(new FileInputStream(imgFile));
//			iv.setImageBitmap(img);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		TextView tvOK = (TextView) findViewById(R.id.ok);
//		testdir();
//		
//		JSONObject js = new JSONObject();
	
		
		
		
	//	tvOK.setText(doubanJson.toString());
		
		
		//Toast.makeText(this, NetConnUtils.getNetWorkType(this), Toast.LENGTH_LONG).show();
		
//		
//		b.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				startActivity(i);
//			}
//		});
	}
	


	
	public View getRootView() {
		return findViewById(android.R.id.content);
	}
	
	public void testdir(){
//	    final String cachePath =
//                Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ||
//                        !CacheUtils.isExternalStorageRemovable() ?
//                        CacheUtils.getExternalCacheDir(context).getPath() :
//                        context.getCacheDir().getPath();
		if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED){
			String s = Environment.getExternalStorageState();
			Log.d("fetch", s);
		}
		
		String p1 = CacheUtils.getExternalCacheDir(this).getPath();
		Log.d("fetch", p1);
		
		String p2 = this.getCacheDir().getPath();
		
		
		Log.d("fetch", p2);
	}


	@Override
	public void execTags(View v) {
		new TagListener(this).doViewClicked(v);
	}

	
	void showDialog(){
		FragmentManager fm = getSupportFragmentManager();
		DialogFragment fg = new DialogFragment();
		fg.show(fm, "aa");
		
	}




	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		System.exit(0);
	}

//	@Override
//	public void execTags(View v) {
//		new TagListener(this).doViewClicked(v);
//	}
	
	
	

}
