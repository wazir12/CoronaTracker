package com.lwazir.coronatracker;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Self_Diagnosis_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Self_Diagnosis_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private WebView webView;
    private  String Load_url="https://covid.apollo247.com/?utm_source=linkedin&utm_medium=organic&utm_campaign=bot_scanner";
    //"https://www.arcgis.com/apps/opsdashboard/index.html#/85320e2ea5424dfaaa75ae62e5c06e61";
    private final static long threshold = 150000;


    public Self_Diagnosis_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment Self_Diagnosis_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Self_Diagnosis_Fragment newInstance(String param1, String param2) {
        Self_Diagnosis_Fragment fragment = new Self_Diagnosis_Fragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_self__diagnosis_, container, false);
        webView = v.findViewById(R.id.mbEmbeddedWebView);
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
        // Inflate the layout for this fragment
        return v;
    }
    private void openURL() {
        webView.loadUrl(Load_url);
        webView.requestFocus();
    }
}
