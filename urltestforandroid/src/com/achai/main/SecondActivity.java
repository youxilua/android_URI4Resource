package com.achai.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.achai.framework.R;
import com.achai.framework.adapter.AsyncImageAdapter;
import com.achai.framework.app.UserApp;
import com.achai.framework.logs.GraphivzLogActivity;
import com.cyrilmottier.android.listviewtipsandtricks.data.Cheeses;
import com.example.android.bitmapfun.provider.Images;

public class SecondActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		List<Map<String, String>> map_list = new ArrayList<Map<String, String>>();
		// String [] test = {"aa","bb"};
//		String [] test = Cheeses.CHEESES;
		String [] test = Images.imageThumbUrls;
		for (int i = 0; i < test.length; i++) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("imageurl", test[i]);
			map_list.add(m);
		}

//		AsyncImageAdapter aia = new AsyncImageAdapter(this, map_list,
//				R.layout.tag_listview_item, new int[] { R.id.textView1,
//						R.id.textView2 });
		
		AsyncImageAdapter aia = new AsyncImageAdapter(this, map_list,
				R.layout.tag_listview_item,R.drawable.ic_launcher);
		aia.setRound(100);
		
		setListAdapter(aia);
		
		

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		AsyncImageAdapter aa = (AsyncImageAdapter) l.getAdapter();
		String ss = (String) aa.getItem(position).toString();
		UserApp.showMessage(this, ss);
	}

}
