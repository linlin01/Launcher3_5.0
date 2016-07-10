package lefty.webundle;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.launcher3.R;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import lefty.CommonsUtils;
import lefty.DottedProgressBar;
import lefty.LeftyActivity;
import lefty.PlayYoutubeActivity;
import lefty.WallpaperSetUpActivity;

public class WebViewFragment extends Fragment {


    private static final String BASE_URL_WIFI = "http://lefty.mobi/mobile/angular1/";
    private static final String BASE_URL_2G = "http://lefty.mobi/mobile/portal2G/";


    View mMainView;
    HTML5WebView mWebView;
    ProgressBar mProgressBar;

    ImageView mImageView;
    RelativeLayout mLayout;
    ImageButton mSettingButton;

    TextView mGreetingsTextView;

    SharedPreferences mSharedPrefs;

    String address = "";

    TextView mMemTextView;
    RelativeLayout progress_layout;
//    TextView mLoadingTextView;

    public WebViewFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPrefs = getActivity().getSharedPreferences(CommonsUtils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public HTML5WebView getWebView() {
        return mWebView;
    }
TextView status_bar_textview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_webview_layout, null);
        mLayout = (RelativeLayout) mMainView.findViewById(R.id.webview_container);
        status_bar_textview = (TextView)mMainView.findViewById(R.id.status_bar_textview);
        status_bar_textview.setVisibility(View.GONE);
        mImageView = (ImageView) mMainView.findViewById(R.id.native_imageview);

        mProgressBar = (ProgressBar) mMainView.findViewById(R.id.progress_bar);
        mSettingButton = (ImageButton) mMainView.findViewById(R.id.setting_button);
        mGreetingsTextView = (TextView) mMainView.findViewById(R.id.greeting_text);
        mMemTextView = (TextView) mMainView.findViewById(R.id.mem_textview);
        mbar = (DottedProgressBar) mMainView.findViewById(R.id.progress);
        progress_layout = (RelativeLayout) mMainView.findViewById(R.id.progress_layout);
//        mLoadingTextView = (TextView) mMainView.findViewById(R.id.loader_textview);
        progress_layout.setVisibility(View.GONE);
//        Spannable wordtoSpan = new SpannableString("Please wait your buddy lefty is loading");
//        wordtoSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#a30002")), 23, 28, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mLoadingTextView.setText(wordtoSpan);

//        try {
//            loadWebView();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mImageView.setVisibility(View.GONE);


            }
        }, 1000);
//        Button mCrashButton = (Button) mMainView.findViewById(R.id.crashButton);
//        mCrashButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                throw new RuntimeException("This exception thrown by tajinder inside " + this.getClass().getName());
//
//            }
//        });


        return mMainView;
    }


    DottedProgressBar mbar;

    void showProgressDialog() {
        Runnable run = new Runnable() {

            @Override
            public void run() {
                mbar.startProgress();
            }
        };
        Handler han = new Handler();
        han.postAtTime(run, 100);
    }

    public void stopProgress() {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mbar.stopProgress();
                progress_layout.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    boolean mFirstTimeLoad = false;

    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void loadWebView() {

        try{
            mTracker.setScreenName(CommonsUtils.WEB_SCREEN_NAME);
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        }catch (Exception e){
            e.printStackTrace();
        }


        setUpWebView();
        String deviceId = CommonUtils.getSecureDeviceId(getActivity());
        String lang = "";
        String address = "";
        if (!TextUtils.isEmpty(mSharedPrefs.getString("lang", null))) {
            lang = mSharedPrefs.getString("lang", null);
        }
        if (!TextUtils.isEmpty(mSharedPrefs.getString("address", null))) {
            address = mSharedPrefs.getString("address", null);
        } else {
            address = "No location/No location";
        }

        String finalURL = "";
        if (CommonUtils.isConnectedToInternet(getActivity())) {
            String tt = CommonUtils.getNetworkClass(getActivity());
            if (!TextUtils.isEmpty(tt) && tt.equalsIgnoreCase("2G")) {
                finalURL = BASE_URL_2G + deviceId + "/" + address + "/" + lang;
            } else {
                finalURL = BASE_URL_WIFI + deviceId + "/" + address + "/" + lang;
            }
            Log.e("Final webview url", "" + finalURL);

        } else {
            //Toast.makeText(getActivity(), "You are offline", Toast.LENGTH_LONG).show();
        }
        mFirstTimeLoad = true;
        mWebView.loadUrl(finalURL);



    }

    public void goBack() {
        if (mWebView != null) mWebView.goBack();
    }

    Tracker mTracker = null;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTracker = LeftyActivity.getDefaultTracker(getActivity());
//        mTracker.setScreenName("LeftyWebView");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    public void sendEvent(String eventName) {
        if (mTracker != null) {
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Settings")
                    .setAction(eventName)
                    .build());

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        destroyWebView();
    }

    public void setUpWebView() {
        mWebView = new HTML5WebView(getActivity());
        mWebView.setWebViewClient(new Callback());
        mLayout.addView(mWebView.getLayout());

    }

    public void destroyWebView() {
        if (mWebView != null) {
            status_bar_textview.setVisibility(View.GONE);
            mLayout.removeView(mWebView);
            mWebView.removeAllViews();
            mWebView.clearCache(true);
            mWebView.clearHistory();
            mWebView.destroy();
            mWebView = null;
        }

    }

    String actionParam = null;
//    String tempActionParam;


    boolean mStopLoading;

    private class Callback extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("shouldOverridLoading", "" + url);
            Uri uri = Uri.parse(url);
            if (url.contains("action=entertainment")) {
                openBrowser(url);
                return true;
            } else if (url.contains("youtube")) {
                openYouTube(url);
                return true;

            } else if (url.contains("newspoint://")) {
                String deepLink;
                if (uri != null) {
                    deepLink = uri.getQueryParameter("deeplink");
                    if (!TextUtils.isEmpty(deepLink)) {
                        String language = uri.getQueryParameter("lang");
                        if (!TextUtils.isEmpty(language)){
                            saveLanguageInPref(language);
                            destroyWebView();
                            loadWebView();
                        }

                        openTOI(deepLink);
                        return true;
                    } else {
                        openTOI(url);
                        return true;
                    }
                }
            }else if (url.contains("wallpaper")){
                if (uri != null) {
                    actionParam = uri.getQueryParameter("action");
                    if (!TextUtils.isEmpty(actionParam) && actionParam .equalsIgnoreCase("wallpaper")){
                        String wallURL = uri.getQueryParameter("url");
                        if (!TextUtils.isEmpty(wallURL)){
                            launchWallpaperActivity(url);
                            return true;
                        }

                    }
                }
            }else {
                openBrowser(url);
            }
            /*else if (actionParam != null) {
                actionParam = null;
                if (!TextUtils.isEmpty(url)) {
                    launchWallpaperActivity(url);
                    return true;
                }
            }*/


//            if (uri != null) {
//                actionParam = uri.getQueryParameter("action");
//                if (!TextUtils.isEmpty(actionParam) && actionParam .equalsIgnoreCase("wallpaper")){
//                    String wallURL = uri.getQueryParameter("url");
//                    if (TextUtils.isEmpty(wallURL)){
//                        launchWallpaperActivity(url);
//                        return true;
//                    }
//
//                }else{
//                    actionParam = null;
//                    openBrowser(url);
//                    return true;
//                }
//            }


            return true;
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (mFirstTimeLoad) {
                mFirstTimeLoad = false;
                if (CommonUtils.isConnectedToInternet(getActivity())) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {

                            progress_layout.setVisibility(View.VISIBLE);
                            showProgressDialog();
                        }
                    });
                }
            }
            /*Log.e("Start URL", "" + url);
            Uri uri = Uri.parse(url);
            if (uri != null) {
                String id = uri.getQueryParameter("v");
                if (!TextUtils.isEmpty(id)) {
                    view.stopLoading();
                    if (view.canGoBack()) {
                        goBack();
                    }
                    watchYoutubeVideo(id);
                } else {

                    if (tempActionParam != null) {
                        tempActionParam = null;
                        if (!TextUtils.isEmpty(url)) {
                            launchWallpaperActivity(url);
                        }
                        view.stopLoading();
                        if (view.canGoBack()) {
                            goBack();
                        }


                    } else {
                        try {
                            actionParam = uri.getQueryParameter("action");
                            if (!TextUtils.isEmpty(actionParam) && actionParam.equalsIgnoreCase("wallpaper")) {
                                tempActionParam = actionParam;
                                mStopLoading = true;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (tempActionParam == null) {

                            if (!(url.contains(".jpg")) || !(url.contains(".png"))) {
                                mStopLoading = false;
                                if (url.contains("newspoint://")) {
                                    String deepLink = null;
                                    if (uri != null) {
                                        deepLink = uri.getQueryParameter("deeplink");
                                        if (!TextUtils.isEmpty(deepLink)) {
                                            String language = uri.getQueryParameter("lang");
                                            saveLanguageInPref(language);
                                            // newsPointCount = newsPointCount + 1;
//                                            if (newsPointCount == 2) {
//                                                newsPointCount = 0;
                                            openTOI(deepLink);
//                                            }
                                        } else {
                                            openTOI(deepLink);
                                        }
                                    }
                                }

                                super.onPageStarted(view, url, favicon);
                                if (CommonUtils.isConnectedToInternet(getActivity())) {
                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {

                                            progress_layout.setVisibility(View.VISIBLE);
                                            showProgressDialog();


                                        }
                                    });
                                }
                            }

                        }

                    }


                }*/
//                if (tempActionParam != null) {
//                    tempActionParam = null;
//
//                    view.stopLoading();
//                    if (view.canGoBack()) {
//                        goBack();
//                    }
//                    if (!TextUtils.isEmpty(url)) {
//                        launchWallpaperActivity(url);
//                    }
//
//                    return;
//                } else {
//                    try {
//                        actionParam = uri.getQueryParameter("action");
//                        if (!TextUtils.isEmpty(actionParam) && actionParam.equalsIgnoreCase("wallpaper")) {
//                            tempActionParam = actionParam;
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    super.onPageStarted(view, url, favicon);
//                    if (CommonUtils.isConnectedToInternet(getActivity())) {
//                        new Handler().post(new Runnable() {
//                            @Override
//                            public void run() {
//                                progress_layout.setVisibility(View.VISIBLE);
//                                showProgressDialog();
//                            }
//                        });
//                    }
//                }

//            }


        }

        @Override
        public void onPageFinished(WebView view, String url) {
            stopProgress();
            mProgressBar.setVisibility(View.GONE);
            status_bar_textview.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse(url);
//            String deepLink = null;
//            if (uri != null) {
//                deepLink = uri.getQueryParameter("deeplink");
//                if (!TextUtils.isEmpty(deepLink)) {
//                    String language = uri.getQueryParameter("lang");
//                    saveLanguageInPref(language);
//                    newsPointCount = newsPointCount + 1;
//                    if (newsPointCount == 2) {
//                        newsPointCount = 0;
//                        openTOI(deepLink);
//                    }
//                }
//            }


        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }
    }


    private void openYouTube(String uri) {
        String id = Uri.parse(uri).getQueryParameter("v");
        Log.e("id", id);
        Intent youTubeIntent = new Intent(getActivity(), PlayYoutubeActivity.class);
        youTubeIntent.putExtra("id", id);
        startActivity(youTubeIntent);
    }

    int newsPointCount = 0;

    void launchWallpaperActivity(String url) {
        if (CommonUtils.isConnectedToInternet(getActivity())) {
            Intent intent = new Intent(getActivity(), WallpaperSetUpActivity.class);
            intent.putExtra("url", "" + url);
            startActivity(intent);
        }

    }


    void openBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    void openTOI(String deepLinkURL) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = null;
        try {
            uri = Uri.parse(deepLinkURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (uri != null) {
            intent.setData(uri);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "No app found", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Unable to parse link", Toast.LENGTH_SHORT).show();
        }


    }

    void saveLanguageInPref(String language) {
        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.putString("lang", language);
        editor.commit();
    }

    public void watchYoutubeVideo(String id) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(intent);
        }
    }

}
