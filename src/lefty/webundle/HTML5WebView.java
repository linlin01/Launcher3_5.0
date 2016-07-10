package lefty.webundle;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

//import com.android.launcher3.LauncherSettings;
import com.android.launcher3.R;
import com.android.launcher3.Utilities;

public class HTML5WebView extends WebView {

    private Context mContext;
    private View mCustomView;
    private FrameLayout mCustomViewContainer;

    private FrameLayout mContentView;
    private FrameLayout mBrowserFrameLayout;
    private FrameLayout mLayout;

    static final String LOGTAG = "HTML5WebView";

    private void init(Context context) {
        mContext = context;
        Activity a = (Activity) mContext;

        mLayout = new FrameLayout(context);

        mBrowserFrameLayout = (FrameLayout) LayoutInflater.from(a).inflate(R.layout.lefty_custom_screen, null);
        mContentView = (FrameLayout) mBrowserFrameLayout.findViewById(R.id.main_content);
        mCustomViewContainer = (FrameLayout) mBrowserFrameLayout.findViewById(R.id.fullscreen_custom_content);
        mLayout.addView(mBrowserFrameLayout, COVER_SCREEN_PARAMS);

        // Configure the webview
        WebSettings s = getSettings();
        s.setBuiltInZoomControls(false);
        s.setAppCachePath(a.getCacheDir().getPath());
        s.setAppCacheEnabled(true);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
//        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
//        s.setSavePassword(true);
//        s.setSaveFormData(true);
        s.setJavaScriptEnabled(true);

//        s.setDomStorageEnabled(true);
//        s.setAllowFileAccess(true);
        mContentView.addView(this);
    }

    public HTML5WebView(Context context) {
        super(context.getApplicationContext());
        init(context);
    }

    public HTML5WebView(Context context, AttributeSet attrs) {
        super(context.getApplicationContext(), attrs);
        init(context);
    }

    public HTML5WebView(Context context, AttributeSet attrs, int defStyle) {
        super(context.getApplicationContext(), attrs, defStyle);
        init(context);
    }

    public FrameLayout getLayout() {
        return mLayout;
    }

    public boolean inCustomView() {
        return (mCustomView != null);
    }

    public void hideCustomView() {
//        mWebChromeClient.onHideCustomView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {


            if (canGoBack()) {
                goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    void allowOrient(Boolean value) {
//        Bundle extras = new Bundle();
//        extras.putBoolean(LauncherSettings.Settings.EXTRA_VALUE, value);
//        Activity activity = (Activity) mContext;
//        activity.getContentResolver().call(
//                LauncherSettings.Settings.CONTENT_URI,
//                LauncherSettings.Settings.METHOD_SET_BOOLEAN,
//                Utilities.ALLOW_ROTATION_PREFERENCE_KEY, extras);
    }

    static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS =
            new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

}