package com.achai.test;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.test.suitebuilder.annotation.Smoke;
import android.test.suitebuilder.annotation.Suppress;
import android.widget.TextView;

import com.achai.main.ExecuteActivity;
import com.achai.utils.AnalyseViewURI;
import com.achai.utils.TypeConverts;
import com.jayway.android.robotium.solo.Solo;

public class URITest extends ActivityInstrumentationTestCase2<ExecuteActivity> {

	public URITest() {
		super("com.achai.main", ExecuteActivity.class);
	}
	
	private Solo testSolo;
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		testSolo = new Solo(getInstrumentation(), getActivity());
	}
	@Suppress
	@SmallTest
	@UiThreadTest
	public void testUri(){
		String textId = "res://id/ok";
		String idUrl = "res://com.achai@layout/main";
		int layout = AnalyseViewURI.getResIdFromURL(idUrl);
		getActivity().setContentView(layout);
		//
		TextView tv = (TextView) getActivity().findViewById(AnalyseViewURI.getResIdFromURL(textId));
		tv.setText("haha");
		
		assertEquals("haha", tv.getText());
	}
	
	@UiThreadTest
	@MediumTest
	public void testView(){
		String resId = "res://layout/main";
		Bundle uriBundle = new Bundle();
		
		uriBundle.putString("res", resId);
		Intent uriIntent = new Intent();
		uriIntent.putExtras(uriBundle);
		
		getActivity().setIntent(uriIntent);
		Bundle iBundle = getActivity().getIntent().getExtras();
		assertNotNull(iBundle);
		
		Map<String, String> paraMap = new HashMap<String, String>();
		for(String k : iBundle.keySet()){
			paraMap.put(k, TypeConverts.ObjectToString(iBundle.get(k)));
		}
		assertNotNull(paraMap);
		//set view
		int layoutId = AnalyseViewURI.getResIdFromURL(paraMap.get("res"));
		getActivity().setContentView(layoutId);
	//	getActivity().checkView();
		
		//testSolo.sleep(50000);
		
		
	}
	@UiThreadTest
	public void testViewCount(){
		
	}
}
