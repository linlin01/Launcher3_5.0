package lefty;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.launcher3.R;

import lefty.webundle.HTML5WebView;

/**
 * Created by sandeep on 17/6/16.
 */
public class YahooSearchActivity extends Activity {

    RelativeLayout mLayout;
    HTML5WebView mWebView;

    String keyboard = "";
    String mYahooUrl = null;
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lefty_yahoo_search_activity);

        gettingExtras();
        init();

    }

    private void gettingExtras() {

        keyboard = getIntent().getStringExtra("keyboard");
        mYahooUrl = getIntent().getStringExtra("url");

    }

    private void init() {

        mLayout = (RelativeLayout) findViewById(R.id.webview_container);
        mWebView = new HTML5WebView(this);
        mProgress = (ProgressBar) findViewById(R.id.progress);
        if (!TextUtils.isEmpty(mYahooUrl)){
            loadWebView();
        }

        mLayout.addView(mWebView.getLayout());
    }

    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    void loadWebView() {


//        mWebView.setInitialScale(1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        mWebView.setWebViewClient(new Callback());
        //mWebView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17");

        keyboard = keyboard.replace(" ", "+");

        String finalURL = mYahooUrl +"&p="+ keyboard;
        mWebView.loadUrl(finalURL);
        mWebView.getChildCount();

    }


    private class Callback extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            mProgress.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mProgress.setVisibility(View.VISIBLE);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mProgress.setVisibility(View.GONE);
        }
    }


}
