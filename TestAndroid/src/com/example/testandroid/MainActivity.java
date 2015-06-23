package com.example.testandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	Button go, load_local;
	EditText edit;
	VideoEnabledWebView webview;
	WebViewClient wvc = new WebViewClient() {

		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			view.loadUrl(url);

			return true;
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();

	}

	private void initView() {
		go = (Button) findViewById(R.id.go);
		load_local = (Button) findViewById(R.id.load_local);
		edit = (EditText) findViewById(R.id.edit);
		webview = (VideoEnabledWebView) findViewById(R.id.webview);
		webview.setWebViewClient(wvc);
		WebSettings webSettings = webview.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setPluginState(PluginState.ON);
		webSettings.setPluginsEnabled(true);// 可以使用插件
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setDefaultTextEncodingName("UTF-8");
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setUseWideViewPort(true);
		webview.setVisibility(View.VISIBLE);
		edit.setText("http://dc.dcdmt.cn/File/20150609/OrdinaryInformation/949ffb2d-8fe3-4954-b322-ac6cea52ad1d.mp4");
		go.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String url = edit.getText().toString();
				if (url != null) {
					webview.loadUrl(url);
				}
			}
		});
		
		load_local.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				webview.loadUrl("file:///android_asset/zqwhs_2_26M.mp4");
				//webview.loadUrl("http://www.ydtsystem.com/CardImage/21/video/20140305/20140305124807_37734.mp4");
				//webview.loadUrl("http://player.youku.com/embed/XNTM5MTUwNDA0");
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		webview.onPause();

	}

	@Override
	public void onResume() {// 继承自Activity
		super.onResume();
		webview.onResume();
	}

	@Override
	protected void onDestroy() {
		webview.pauseTimers();
		webview.stopLoading();
		webview.destroy();
		webview = null;
	}
}
