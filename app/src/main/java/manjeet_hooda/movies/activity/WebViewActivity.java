package manjeet_hooda.movies.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import manjeet_hooda.movies.R;
import manjeet_hooda.movies.global.GlobalDataContainer;

public class WebViewActivity extends AppCompatActivity {

    private String  mUrl;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private static String movieTitle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getIntent().getExtras().getString(GlobalDataContainer.MOVIE_URI);
        movieTitle = getIntent().getExtras().getString(GlobalDataContainer.MOVIE_TITLE);

        Toast.makeText(this, mUrl, Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_web_view);
        mToolbar = (Toolbar)findViewById(R.id.webViewToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        try {
            getSupportActionBar().setTitle(movieTitle);
        }catch (NullPointerException e){

        }
        mProgressBar =  (ProgressBar)findViewById(R.id.webview_progress_bar);
        mProgressBar.setMax(100);
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });
        mWebView.loadUrl(mUrl);
    }

}
