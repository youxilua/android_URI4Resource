package com.achai.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.achai.framework.R;
import com.achai.framework.adapter.AsyncImageAdapter;
import com.example.android.bitmapfun.provider.Images;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryImageShow extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_image_view);
//		setContentView(R.layout.gallery_item);
//		ImageView iv = (ImageView) findViewById(R.id.imageView1);
//		Bitmap m = BitmapFactory.decodeResource(getResources(),
//				R.drawable.aaa);
//		// 根据预览图片设置
//		m =  Bitmap.createScaledBitmap(m, 480, 320, true);
////		
//		iv.setImageBitmap(m);
		String [] test = Images.imageThumbUrls;
		List<Map<String, String>> map_list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < test.length; i++) {
			
			Map<String, String> m = new HashMap<String, String>();
			m.put("imageurl", test[i]);
			map_list.add(m);
		}
		
		Gallery g  = (Gallery) findViewById(R.id.gallery1);
		Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.empty_photo);
		
		AsyncImageAdapter imageAdapter = new AsyncImageAdapter(this, map_list, R.layout.gallery_item,R.drawable.empty_photo,480,320);
		imageAdapter.setFadin(false);
		//	imageAdapter.setImageSize(200, 200);
		g.setAdapter(imageAdapter);
	}

}
