package manjeet_hooda.movies.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
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
    private static String movieTitle;
    private Toolbar mToolbar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setupToolbar();
        setupWebView();
    }

    private void setupToolbar(){
        movieTitle = getIntent().getExtras().getString(GlobalDataContainer.MOVIE_TITLE);
        mToolbar = (Toolbar)findViewById(R.id.webViewToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        try {
            getSupportActionBar().setTitle(movieTitle);
        }catch (NullPointerException e){

        }

    }

    private void setupWebView(){
        mUrl = getIntent().getExtras().getString(GlobalDataContainer.MOVIE_URI);

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.setWebChromeClient(new MyWebViewClient());
        mWebView.setWebViewClient(new MyWebViewNavClient());

        progressBar = (ProgressBar)findViewById(R.id.webProgressBar);
        progressBar.setMax(100);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(mUrl);
        WebViewActivity.this.progressBar.setProgress(0);
        Toast.makeText(this,"Loading... ",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyWebViewClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress){
            WebViewActivity.this.setValue(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }

    private class MyWebViewNavClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            /*if (Uri.parse(url).getHost().equals("www.example.com")) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }*/
            view.loadUrl(url);
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            //startActivity(intent);
            return true;
        }
    }

    public void setValue(int Progress) {
        if (Progress >= 100) {
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            this.progressBar.setProgress(Progress);
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.copyBackForwardList().getCurrentIndex() > 0) {
            mWebView.goBack();
        }
        else {
            // Your exit alert code, or alternatively line below to finish
            super.onBackPressed(); // finishes activity
        }
    }

}
