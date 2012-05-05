package com.achai.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.TextView;

import com.achai.main.ExecuteActivity;
import com.achai.utils.AnalyseViewURI;

public class URITest extends ActivityInstrumentationTestCase2<ExecuteActivity> {

	public URITest() {
		super("com.achai.main", ExecuteActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		getActivity();
	}
	@SmallTest
	@UiThreadTest
	public void testUri(){
		String textId = "res://id/ok";
		
		String idUrl = "res://com.achai:1/main";
		int layout = AnalyseViewURI.getResIdFromURL(idUrl);
		getActivity().setContentView(layout);
		//
		TextView tv = (TextView) getActivity().findViewById(AnalyseViewURI.getResIdFromURL(textId));
		tv.setText("haha");
		
		assertEquals("haha", tv.getText());
	}
}
