package com.wbteam.onesearch.app.weight;//package com.wbteam.onesearch.app.weight;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.widget.TextView;
//
//import com.wbteam.onesearch.R;
//
///**
// * 在Activity中使用
// */
//public class CustomActivity extends Activity {
//	private TextView textView;
//	private CustomScrollView2 scrollView;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.custom_scrollview_layout);
//		scrollView = (CustomScrollView2) findViewById(R.id.scoll_view);
//		textView = (TextView) findViewById(R.id.animation_view);
//	}
//
//	@Override
//	public void onWindowFocusChanged(boolean hasFocus) {
//		super.onWindowFocusChanged(hasFocus);
//		if (hasFocus) {
//			scrollView.setAnimationView(textView);
//		}
//	}
//}