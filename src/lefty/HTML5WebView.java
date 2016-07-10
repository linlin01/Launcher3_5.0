//package lefty;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.pm.ActivityInfo;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.AttributeSet;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.webkit.GeolocationPermissions;
//import android.webkit.PermissionRequest;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.widget.FrameLayout;
//
//import com.android.launcher3.LauncherSettings;
//import com.android.launcher3.R;
//import com.android.launcher3.Utilities;
//
//public class HTML5WebView extends WebView {
//
//    private Context mContext;
//    private MyWebChromeClient mWebChromeClient;
//    private View mCustomView;
//    private FrameLayout mCustomViewContainer;
//    private WebChromeClient.CustomViewCallback mCustomViewCallback;
//
//    private FrameLayout mContentView;
//    private FrameLayout mBrowserFrameLayout;
//    private FrameLayout mLayout;
//
//    static final String LOGTAG = "HTML5WebView";
//
//    private void init(Context context) {
//        mContext = context;
//        Activity a = (Activity) mContext;
//
//        mLayout = new FrameLayout(context);
//
//        mBrowserFrameLayout = (FrameLayout) LayoutInflater.from(a).inflate(R.layout.lefty_custom_screen, null);
//        mContentView = (FrameLayout) mBrowserFrameLayout.findViewById(R.id.main_content);
//        mCustomViewContainer = (FrameLayout) mBrowserFrameLayout.findViewById(R.id.fullscreen_custom_content);
//
//        mLayout.addView(mBrowserFrameLayout, COVER_SCREEN_PARAMS);
//
//
//        //setWebViewClient(new MyWebViewClient());
//
//        // Configure the webview
//        WebSettings s = getSettings();
//        s.setBuiltInZoomControls(false);
//        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        s.setUseWideViewPort(true);
//        s.setLoadWithOverviewMode(true);
//        s.setSavePassword(true);
//        s.setSaveFormData(true);
//        s.setJavaScriptEnabled(true);
//
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
////            s.setMediaPlaybackRequiresUserGesture(true);
////        }
//
//        // enable navigator.geolocation
////	    s.setGeolocationEnabled(true);
////	    s.setGeolocationDatabasePath("/data/data/com.android.launcher3/databases/");
//
//        // enable Web Storage: localStorage, sessionStorage
//        s.setAppCacheEnabled(true);
//        s.setLoadsImagesAutomatically(true);
//        s.setDomStorageEnabled(true);
//        s.setAllowFileAccess(true);
//        if (Build.VERSION.SDK_INT > 7) {
//            s.setPluginState(WebSettings.PluginState.ON);
//        }
//        mWebChromeClient = new MyWebChromeClient();
//        setWebChromeClient(mWebChromeClient);
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////            WebView.setWebContentsDebuggingEnabled(true);
////
////        }
//
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////            //WebView.enableSlowWholeDocumentDraw();
////        }
//        mContentView.addView(this);
//    }
//
//    public HTML5WebView(Context context) {
//        super(context.getApplicationContext());
//        init(context);
//    }
//
//    public HTML5WebView(Context context, AttributeSet attrs) {
//        super(context.getApplicationContext(), attrs);
//        init(context);
//    }
//
//    public HTML5WebView(Context context, AttributeSet attrs, int defStyle) {
//        super(context.getApplicationContext(), attrs, defStyle);
//        init(context);
//    }
//
//    public FrameLayout getLayout() {
//        return mLayout;
//    }
//
//    public boolean inCustomView() {
//        return (mCustomView != null);
//    }
//
//    public void hideCustomView() {
//        mWebChromeClient.onHideCustomView();
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//
//            if ((mCustomView == null) && canGoBack()) {
//                goBack();
//                return true;
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    private class MyWebChromeClient extends WebChromeClient {
//        private Bitmap mDefaultVideoPoster;
//        private View mVideoProgressView;
//
//        @Override
//        public void onShowCustomView(View view, CustomViewCallback callback) {
//            //Log.i(LOGTAG, "here in on ShowCustomView");
//            HTML5WebView.this.setVisibility(View.GONE);
//
//            // if a view already exists then immediately terminate the new one
//            if (mCustomView != null) {
//                callback.onCustomViewHidden();
//                return;
//            }
//
//            mCustomViewContainer.addView(view);
//            mCustomView = view;
//            mCustomViewCallback = callback;
//            mCustomViewContainer.setVisibility(View.VISIBLE);
//            Activity activity = (Activity) mContext;
//            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            allowOrient(true);
//
//
//        }
//
//        @Override
//        public void onHideCustomView() {
//
//            if (mCustomView == null)
//                return;
//
//            // Hide the custom view.
//            mCustomView.setVisibility(View.GONE);
//
//            // Remove the custom view from its container.
//            mCustomViewContainer.removeView(mCustomView);
//            mCustomView = null;
//            mCustomViewContainer.setVisibility(View.GONE);
//            mCustomViewCallback.onCustomViewHidden();
//
//            HTML5WebView.this.setVisibility(View.VISIBLE);
//            Activity activity = (Activity) mContext;
//            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            allowOrient(false);
//
//            //Log.i(LOGTAG, "set it to webVew");
//        }
//
//        @Override
//        public Bitmap getDefaultVideoPoster() {
//            //Log.i(LOGTAG, "here in on getDefaultVideoPoster");
//            if (mDefaultVideoPoster == null) {
//                mDefaultVideoPoster = BitmapFactory.decodeResource(
//                        getResources(), R.drawable.red_dot);
//            }
//            return mDefaultVideoPoster;
//        }
//
//        @Override
//        public View getVideoLoadingProgressView() {
//            //Log.i(LOGTAG, "here in on getVideoLoadingPregressView");
//
//            if (mVideoProgressView == null) {
//                LayoutInflater inflater = LayoutInflater.from(mContext);
//                mVideoProgressView = inflater.inflate(R.layout.lefty_video_loading_progress, null);
//            }
//            return mVideoProgressView;
//        }
//
//        @Override
//        public void onReceivedTitle(WebView view, String title) {
//            ((Activity) mContext).setTitle(title);
//        }
//
//        @Override
//        public void onProgressChanged(WebView view, int newProgress) {
//            ((Activity) mContext).getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress * 100);
//        }
//
//        @Override
//        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
//            callback.invoke(origin, true, false);
//        }
//
//        @Override
//        public void onPermissionRequest(PermissionRequest request) {
//            super.onPermissionRequest(request);
//        }
//
//
//
//    }
//
//
//    void allowOrient(Boolean value){
//        Bundle extras = new Bundle();
//        extras.putBoolean(LauncherSettings.Settings.EXTRA_VALUE,  value);
//        Activity activity = (Activity) mContext;
//        activity .getContentResolver().call(
//                LauncherSettings.Settings.CONTENT_URI,
//                LauncherSettings.Settings.METHOD_SET_BOOLEAN,
//                Utilities.ALLOW_ROTATION_PREFERENCE_KEY, extras);
//    }
////	private class MyWebViewClient extends WebViewClient {
////	    @Override
////	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
////	    	Log.i(LOGTAG, "shouldOverrideUrlLoading: "+url);
////	    	// don't override URL so that stuff within iframe can work properly
////	        // view.loadUrl(url);
////	        return false;
////	    }
////	}
//
//    static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS =
//            new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
////    @Override
////
////    protected void onDraw( Canvas canvas ) {
////
////        if ( surface != null ) {
////
////            // Requires a try/catch for .lockCanvas( null )
////
////            try {
////
////                final Canvas surfaceCanvas = surface.lockCanvas( null ); // Android canvas from surface
////
////                super.onDraw( surfaceCanvas ); // Call the WebView onDraw targetting the canvas
////
////                surface.unlockCanvasAndPost( surfaceCanvas ); // We're done with the canvas!
////
////            } catch ( OutOfResourcesException excp ) {
////
////                excp.printStackTrace();
////
////            }
////
////        }
////
////        // super.onDraw( canvas ); // <- Uncomment this if you want to show the original view
////
////    }
//}