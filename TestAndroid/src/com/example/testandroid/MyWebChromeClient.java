package com.example.testandroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;

public class MyWebChromeClient extends WebChromeClient {

	private CustomViewCallback mCustomViewCallback;
	private int mOriginalOrientation = 1;
	private Context context;
	private FrameLayout mFullscreenContainer;
	private FrameLayout mContentView;
	private View mCustomView = null;

	public MyWebChromeClient(Context context) {
		super();
		this.context = context;
		View view= LayoutInflater.from(context).inflate(R.layout.act_flashplay, null); 
		mFullscreenContainer = (FrameLayout) view.findViewById(R.id.fullscreen_custom_content);
		mContentView = (FrameLayout) view.findViewById(R.id.main_content);
	}

	@Override
	public void onShowCustomView(View view, CustomViewCallback callback) {
		// TODO Auto-generated method stub
		onShowCustomView(view, mOriginalOrientation, callback);
		super.onShowCustomView(view, callback);

	}

	public void onShowCustomView(View view, int requestedOrientation,
			WebChromeClient.CustomViewCallback callback) {
		if (mCustomView != null) {
			callback.onCustomViewHidden();
			return;
		}
		if (getPhoneAndroidSDK() >= 14) {
			mFullscreenContainer.addView(view);
			mCustomView = view;
			mCustomViewCallback = callback;
			mOriginalOrientation = ((Activity) context)
					.getRequestedOrientation();
			mContentView.setVisibility(View.INVISIBLE);
			mFullscreenContainer.setVisibility(View.VISIBLE);
			mFullscreenContainer.bringToFront();
			((Activity) context).setRequestedOrientation(mOriginalOrientation);
		}

	}

	public void onHideCustomView() {
		mContentView.setVisibility(View.VISIBLE);
		if (mCustomView == null) {
			return;
		}
		mCustomView.setVisibility(View.GONE);
		mFullscreenContainer.removeView(mCustomView);
		mCustomView = null;
		mFullscreenContainer.setVisibility(View.GONE);
		try {
			mCustomViewCallback.onCustomViewHidden();
		} catch (Exception e) {
		}
		// Show the content view.
		((Activity) context).setRequestedOrientation(mOriginalOrientation);
	}

	public static int getPhoneAndroidSDK() {
		// TODO Auto-generated method stub
		int version = 0;
		try {
			version = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return version;

	}
}
