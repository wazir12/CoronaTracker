package com.example.coronatracker;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class india_statewiise_corona_cases extends AppCompatActivity {

    private WebView webView;
    private  String Load_url="https://covidout.in/";
//"""//https://bnonews.com/index.php/2020/02/the-latest-coronavirus-cases/";
    private final static long threshold = 150000;
    private Handler handler = null;
    private HandlerThread handlerThread = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_india_statewiise_corona_cases);
        webView = (WebView) findViewById(R.id.mbEmbeddedIndiaStateWiseWebView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
        openURL();

    }
    private void openURL() {
        webView.loadUrl(Load_url);
        webView.requestFocus();
    }
}
