package com.achai.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import com.achai.framework.R;
import com.achai.framework.adapter.AsyncImageAdapter;
import com.achai.framework.cache.CacheUtils;
import com.achai.framework.cache.ImageCache;
import com.achai.framework.cache.ImageCache.ImageCacheParams;
import com.example.android.bitmapfun.provider.Images;

public class ShowImageHaveImagecache extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.async_imge_view);
		ListView lv = (ListView) findViewById(R.id.listView1);
		List<Map<String, String>> map_list = new ArrayList<Map<String, String>>();
		// String [] test = {"aa","bb"};
		// String [] test = Cheeses.CHEESES;
		String[] test = Images.imageThumbUrls;
		for (int i = 0; i < test.length; i++) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("imageurl", test[i]);
			map_list.add(m);
		}

		AsyncImageAdapter aia = new AsyncImageAdapter(this, map_list,
				R.layout.tag_listview_item, null, R.drawable.ic_launcher);
		ImageCacheParams cacheParams = new ImageCacheParams(
				CacheUtils.IMAGE_CACHE_DIR);
		cacheParams.memCacheSize = 1024 * 1024 * CacheUtils
				.getMemoryClass(this) / 3;
		aia.setImageCache(ImageCache.findOrCreateCache(this, cacheParams));

		lv.setAdapter(aia);
	}
}
