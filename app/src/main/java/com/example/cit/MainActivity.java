package com.example.cit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String url="http://www.chennaiinstituteoftechnology.com/";
    WebView myWebView;
    ProgressBar progressBar;
    RelativeLayout loadingprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!DetectConnection.checkInternetConnection(this)) {
            setContentView(R.layout.activity_connection);
        }
        else{
            setContentView(R.layout.activity_main);
            loadingprogress = findViewById(R.id.loadingprogress);
            progressBar = findViewById(R.id.progress_circular);
            loadingprogress.setVisibility(View.VISIBLE);
            myWebView =  findViewById(R.id.webview);
            myWebView.loadUrl(url);
            WebSettings webSettings = myWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            myWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    loadingprogress.setVisibility(View.VISIBLE);
                    view.loadUrl(request.getUrl().toString());
                    return false;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    // TODO Auto-generated method stub
                    super.onPageFinished(view, url);
                    loadingprogress.setVisibility(View.GONE);
                }
            });

        }


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (DetectConnection.checkInternetConnection(this)) {
            if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
                myWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }



    //Starting new Class for internt connection


    public static class DetectConnection {
        public static boolean checkInternetConnection(Context context) {

            ConnectivityManager con_manager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);

            return (con_manager.getActiveNetworkInfo() != null
                    && con_manager.getActiveNetworkInfo().isAvailable()
                    && con_manager.getActiveNetworkInfo().isConnected());
        }

    }

}




