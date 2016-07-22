package lefty;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.launcher3.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.CollectionTimeline;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import lefty.com.viewpagerindicator.CirclePageIndicator;
import lefty.custompager.AutoScrollViewPager;
import lefty.models.Example;
import lefty.models.Publication;
import lefty.models.PublisherPojo;
import lefty.models.WallpapersPojo;

public class Lefty_MainActivity extends Fragment implements View.OnClickListener {


    LinearLayout mPagerIndicatorClickContainer;
    LinearLayout mEntertainmentIndicatorContainerLLayout;
    LinearLayout mYoutubeHeader;
    LinearLayout mWallpapersHeader;
    LinearLayout mSettingHeader;
    LinearLayout mTwitterHeader;
    LinearLayout mTrendingAppHeader;
    LinearLayout mTrendingAppsLLayout;
    LinearLayout mTrendingGamesHeader;
    LinearLayout mTrendingGamesLLayout;
    LinearLayout mEntertainmentHeader;
    LinearLayout mTwitterTrendingNewsHeader;


    VideoImageAdapter mAdapter;

    ImageView mSettingImage;
    RelativeLayout mSettingSelectLanguageLayout;
    RelativeLayout mSettingSelectPublisherLayout;

    LinearLayout twiter_trending_news_layout;

    TextView mNext;
    TextView mPrevious;
    TextView mProceed;
    TextView mLanguageTextView;
    TextView mPublisher;
    TextView mYouTubeHeaderTxt;
    TextView mWallpapersHeaderTxt;
    TextView mSettingHeaderTxt;
    TextView mTwiterHeaderTxt;
    TextView mTrendingHeaderTxt;
    TextView mTrendingGamesHeaderTxt;
    TextView mEntertainmentHeaderTxt;
    TextView mTwitterTrendingNewsTxt;

    ImageView mYouTubeIcon;
    ImageView mSettingIcon;
    ImageView mWallpapersIcon;
    ImageView mTwitterIcon;
    ImageView mTrendingAppIcon;
    ImageView mTrendingGameIcon;
    ImageView mEntertainmentIcon;
    ImageView mTwitterTrendingIcon;


    RelativeLayout mSelectLanguageLayout;
    RelativeLayout mSelectPublisherLayout;

    View mMainView;
    Example mCompleteData = null;
    List<Example.Video> mVideoList = new ArrayList<>();

    public SharedPreferences mLeftyPref;
    LeftyActivity mActivity;

    //Weather resources
    ImageView mWeatherBgImageView;
    ImageView type_image_textview;
    TextView type_textview;
    TextView degree_textview;
    TextView weather_location_textview;
    TextView date_textview;


    //Wallpaper
    List<Example.Wallpaper> mWallPaperList = new ArrayList<>();
    List<Example.Entertainment> mEntertainmentList = new ArrayList<>();
    List<Example.Cpa> mTrendingAppList = new ArrayList<>();
    List<Example.Game> mTrendingGameList = new ArrayList<>();
    EntertainmentAdapter mEntertainmentAdapter;

    LinearLayout mTweetContainer;
    EditText mSearchYahooEdt;
    ImageView search_icon;

    LinearLayout mTOILayout;
    boolean mIsSettingsEnabled;
    RelativeLayout small_setting_imageview;

    LinearLayout mSettingsLayout;
    RelativeLayout mWeatherContainer;

    List<Example.TopAdvertisement> mTopList = new ArrayList<>();
    List<Example.BottomAdvertisement> mBottomList = new ArrayList<>();
    List<String> mPublisherSelectedList = new ArrayList<>();

    AutoScrollViewPager mVideoViewPager;
    AutoScrollViewPager mBanner1ViewPager;
    AutoScrollViewPager mBanner2ViewPager;
    AutoScrollViewPager mEntertainmentViewpager;

    CirclePageIndicator mVideoIndicator;
    CirclePageIndicator entertainment_indicator;
    CirclePageIndicator mBanner1Indicator;
    CirclePageIndicator mBanner2Indicator;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (LeftyActivity) activity;
    }

    Tracker mTracker;
    int width;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLeftyPref = mActivity.getSharedPreferences("Lefty", Context.MODE_PRIVATE);
        int screenWidth = getScreenWidth(getActivity());
        int calculatedWidth = gettingWidth(75, getActivity());
        switch (screenWidth) {
            case 480:
                calculatedWidth = gettingWidth(65, getActivity());
                break;
            default:
                break;
        }
        mTracker = LeftyActivity.getDefaultTracker(getActivity());

        mImageParams = new LinearLayout.LayoutParams(calculatedWidth, calculatedWidth);
        switch (screenWidth) {
            case 480:
                width = (getScreenWidth(getActivity()) / 3) + gettingWidth(4, getActivity());
                break;
            default:
                width = getScreenWidth(getActivity()) / 3 - gettingWidth(5, getActivity());
                break;
        }
    }


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.lefty_native_viewstub, container, false);
        mStub = (ViewStub) mMainView.findViewById(R.id.viewstub1);
        view = mStub.inflate();


        return mMainView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();

        init(view);
    }

    void loadData() {
        String response = getDataFromPref();
        if (!TextUtils.isEmpty(response)) {
            Gson gson = new Gson();
            try {
                mCompleteData = gson.fromJson(response, Example.class);
            } catch (Exception e) {
                e.printStackTrace();
                mCompleteData = null;
            }
        }
    }

    List<Example.Twitter> mTwiterList = new ArrayList<>();

    public void refreshFragment() {

        mTracker.setScreenName(CommonsUtils.NATIVE_SCREEN_NAME);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        stopProgress();
        loadData();


        if (mCompleteData != null) {
            mVideoList = mCompleteData.getData().getVideo();
        }
        mAdapter.notifyDataSetChanged();
        setUpWeather();
        setUpNews();


        if (mCompleteData != null) {
            mVideoList = mCompleteData.getData().getVideo();
            mWallPaperList = mCompleteData.getData().getWallpaper();
            mEntertainmentList = mCompleteData.getData().getEntertainment();
            mTrendingAppList = mCompleteData.getData().getCpa();
            mTrendingGameList = mCompleteData.getData().getGames();
            mTwiterList = mCompleteData.getData().getTwitter();


        }
        if (CommonsUtils.isConnectedToInternet(getActivity())) {

            if (mActivity != null && mActivity.isOnCustomContent()) {
                twittes();
                twittesTrending();
            }


        }
        setUpEntertainmentAdapter();
        //setUpWallpaperView();
        setWallpapersAdapterRV();
        setUpTrendingAppsView();
        settingTrendingGameView();
        setUpTOI();
        setUpBanner1Adapter();
        setTitles();
    }

    ImageView mSplashImageView;
    RelativeLayout progress_layout;
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

    ViewStub mStub;

    private void init(View mMainView) {
//
        mSearchYahooEdt = (EditText) mMainView.findViewById(R.id.search_yahoo_edt);
        search_icon = (ImageView) mMainView.findViewById(R.id.search_icon);
        progress_layout = (RelativeLayout) mMainView.findViewById(R.id.progress_layout);
        mbar = (DottedProgressBar) mMainView.findViewById(R.id.progress);
        String savedData = getDataFromPref();
        if (TextUtils.isEmpty(savedData) && CommonsUtils.isConnectedToInternet(getActivity())) {
            progress_layout.setVisibility(View.VISIBLE);
            showProgressDialog();

        } else {
            progress_layout.setVisibility(View.GONE);
        }
        mSplashImageView = (ImageView) mMainView.findViewById(R.id.splash_imageview);
        mTweetContainer = (LinearLayout) mMainView.findViewById(R.id.twiterlayout);
        twiter_trending_news_layout = (LinearLayout) mMainView.findViewById(R.id.twiter_trending_news_layout);
        mSettingImage = (ImageView) mMainView.findViewById(R.id.settingImage);
        setUpSettings();
        mSelectLanguageLayout = (RelativeLayout) mMainView.findViewById(R.id.linear);
        mSelectPublisherLayout = (RelativeLayout) mMainView.findViewById(R.id.linear1);
        mSettingSelectLanguageLayout = (RelativeLayout) mMainView.findViewById(R.id.settinglanguageLayout);
        mSettingSelectPublisherLayout = (RelativeLayout) mMainView.findViewById(R.id.settingpublisherlayout);
        //mWallpaperIndicator = (LinearLayout) mMainView.findViewById(R.id.wallpaper_indicator);
        mTrendingAppsLLayout = (LinearLayout) mMainView.findViewById(R.id.treading_apps_layout);
        mTrendingGamesLLayout = (LinearLayout) mMainView.findViewById(R.id.treading_games_layout);
        mEntertainmentIndicatorContainerLLayout = (LinearLayout) mMainView.findViewById(R.id.entertainment_indicator_container);
        mPagerIndicatorClickContainer = (LinearLayout) mMainView.findViewById(R.id.viewpageclickcontainer);

        mNext = (TextView) mMainView.findViewById(R.id.next);
        mPrevious = (TextView) mMainView.findViewById(R.id.previouscc);
        mProceed = (TextView) mMainView.findViewById(R.id.proceed);


        mPublisher = (TextView) mMainView.findViewById(R.id.select_publisher_txt);
        mLanguageTextView = (TextView) mMainView.findViewById(R.id.language_select);

        mVideoViewPager = (AutoScrollViewPager) mMainView.findViewById(R.id.matches_viewpager);

        mVideoViewPager.setAutoScrollDurationFactor(5);
        mVideoViewPager.setOverScrollMode(1);
        mVideoIndicator = (CirclePageIndicator) mMainView.findViewById(R.id.ferries_detail_indicator);
        mEntertainmentViewpager = (AutoScrollViewPager) mMainView.findViewById(R.id.entertainment_viewpager);
        mEntertainmentViewpager.setAutoScrollDurationFactor(5);

        entertainment_indicator = (CirclePageIndicator) mMainView.findViewById(R.id.entertainment_indicator);
        mWallpapersHeader = (LinearLayout) mMainView.findViewById(R.id.wallpapers_header);
        mSettingHeader = (LinearLayout) mMainView.findViewById(R.id.setting_header);
        mYoutubeHeader = (LinearLayout) mMainView.findViewById(R.id.video_header);
        mTwitterHeader = (LinearLayout) mMainView.findViewById(R.id.twitter_header);
        mTrendingAppHeader = (LinearLayout) mMainView.findViewById(R.id.treading_apps_header);
        mTrendingGamesHeader = (LinearLayout) mMainView.findViewById(R.id.treading_games_header);
        mEntertainmentHeader = (LinearLayout) mMainView.findViewById(R.id.entertainment_header);
        mTwitterTrendingNewsHeader = (LinearLayout) mMainView.findViewById(R.id.twitter_trending_news);

        mWallpapersHeaderTxt = (TextView) mWallpapersHeader.findViewById(R.id.category_header);
        mYouTubeHeaderTxt = (TextView) mYoutubeHeader.findViewById(R.id.category_header);
        mSettingHeaderTxt = (TextView) mSettingHeader.findViewById(R.id.category_header);
        mTwiterHeaderTxt = (TextView) mTwitterHeader.findViewById(R.id.category_header);
        mTrendingHeaderTxt = (TextView) mTrendingAppHeader.findViewById(R.id.category_header);
        mTrendingGamesHeaderTxt = (TextView) mTrendingGamesHeader.findViewById(R.id.category_header);
        mEntertainmentHeaderTxt = (TextView) mEntertainmentHeader.findViewById(R.id.category_header);
        mTwitterTrendingNewsTxt = (TextView) mTwitterTrendingNewsHeader.findViewById(R.id.category_header);

        mYouTubeIcon = (ImageView) mYoutubeHeader.findViewById(R.id.category_icon);
        mSettingIcon = (ImageView) mSettingHeader.findViewById(R.id.category_icon);
        mWallpapersIcon = (ImageView) mWallpapersHeader.findViewById(R.id.category_icon);
        mTwitterIcon = (ImageView) mTwitterHeader.findViewById(R.id.category_icon);
        mTrendingAppIcon = (ImageView) mTrendingAppHeader.findViewById(R.id.category_icon);
        mTrendingGameIcon = (ImageView) mTrendingGamesHeader.findViewById(R.id.category_icon);
        mEntertainmentIcon = (ImageView) mEntertainmentHeader.findViewById(R.id.category_icon);
        mTwitterTrendingIcon = (ImageView) mTwitterTrendingNewsHeader.findViewById(R.id.category_icon);

        mTOILayout = (LinearLayout) mMainView.findViewById(R.id.toi_layout);
        mWallPaperRV = (RecyclerView) mMainView.findViewById(R.id.recyclerview);
        mWallPaperRV.setVisibility(View.GONE);
        small_setting_imageview = (RelativeLayout) mMainView.findViewById(R.id.small_setting_imageview);
        mSettingsLayout = (LinearLayout) mMainView.findViewById(R.id.settings_layout);
        mWeatherContainer = (RelativeLayout) mMainView.findViewById(R.id.weather_container);
        if (mCompleteData != null) {
            mVideoList = mCompleteData.getData().getVideo();
            mWallPaperList = mCompleteData.getData().getWallpaper();
            mEntertainmentList = mCompleteData.getData().getEntertainment();
            mTrendingAppList = mCompleteData.getData().getCpa();
            mTrendingGameList = mCompleteData.getData().getGames();
            mTwiterList = mCompleteData.getData().getTwitter();
        }

        mBanner1ViewPager = (AutoScrollViewPager) mMainView.findViewById(R.id.banner2_viewpager);
        mBanner2ViewPager = (AutoScrollViewPager) mMainView.findViewById(R.id.banner1_viewpager);

        mBanner1Indicator = (CirclePageIndicator) mMainView.findViewById(R.id.banner2_indicator);
        mBanner2Indicator = (CirclePageIndicator) mMainView.findViewById(R.id.banner1_indicator);
        mBanner1ViewPager.setAutoScrollDurationFactor(5);
        mBanner2ViewPager.setAutoScrollDurationFactor(5);
        setUpBanner1Adapter();

        setTitles();
        setUpVideoAdapter();
        setUpTrendingAppsView();
        settingTrendingGameView();
        setUpEntertainmentAdapter();
        setClickListeners();

        mWeatherBgImageView = (ImageView) mMainView.findViewById(R.id.weather_bg);
        type_image_textview = (ImageView) mMainView.findViewById(R.id.type_image_textview);
        type_textview = (TextView) mMainView.findViewById(R.id.type_textview);
        degree_textview = (TextView) mMainView.findViewById(R.id.degree_textview);
        weather_location_textview = (TextView) mMainView.findViewById(R.id.weather_location_textview);
        date_textview = (TextView) mMainView.findViewById(R.id.date_textview);
        setUpWeather();
        setUpNews();
        setUpTOI();
        setUpBanner1Adapter();

        /*mBanner1 = (ImageView)view.findViewById(R.id.banner1);
        mBanner2 = (ImageView)view.findViewById(R.id.banner2);
        ImageLoader.getInstance().displayImage("drawable://"+R.drawable.sample_banner,mBanner1,getVideoDisplayOptions());
        ImageLoader.getInstance().displayImage("drawable://"+R.drawable.sample_banner,mBanner2,getVideoDisplayOptions());*/

    }


    public void setUpSplashAndNative(boolean showNative) {
        mSplashImageView.setVisibility(View.GONE);

        if (showNative) {
            mSplashImageView.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage("drawable://" + R.drawable.native_screen, mSplashImageView);
        }

    }

    public void resetSplash() {
        mSplashImageView.setVisibility(View.VISIBLE);
        ImageLoader.getInstance().displayImage("drawable://" + R.drawable.splash, mSplashImageView);
    }

    int downY = 0;
    int downX = 0;


    boolean shouldSwipe = false;
    boolean inSwipeMode = false;

    private void setClickListeners() {

        mSelectLanguageLayout.setOnClickListener(this);
        mSelectPublisherLayout.setOnClickListener(this);
        mNext.setOnClickListener(this);
        mProceed.setOnClickListener(this);
        mPrevious.setOnClickListener(this);
        mSettingImage.setOnClickListener(this);
        small_setting_imageview.setOnClickListener(this);
        final GestureDetector youtubeGestureDetector = new GestureDetector(getActivity(), new YoutubePagerGesture());

        mVideoViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int mTouchSlop = 5;
                youtubeGestureDetector.onTouchEvent(event);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        shouldSwipe = true;
                        inSwipeMode = false;
                        downY = (int) event.getY();
                        downX = (int) event.getX();

                        mVideoViewPager.requestDisallowInterceptTouchEvent(true);
                        mVideoViewPager.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_MOVE:

                        int deltaY = (int) (event.getY() - downY);
                        int deltaX = (int) (event.getX() - downX);
                        if (!inSwipeMode && (Math.abs(deltaY) > mTouchSlop || Math.abs(deltaX) > mTouchSlop)) {
                            inSwipeMode = true;
                            if (Math.abs(deltaX) > mTouchSlop) {
                                shouldSwipe = true;
                                mVideoViewPager.stopAutoScroll();
                            } else {
                                shouldSwipe = false;
                            }
                        }

                        if (inSwipeMode) {
                            if (shouldSwipe) {
                                mVideoViewPager.requestDisallowInterceptTouchEvent(true);
                            } else {
                                mVideoViewPager.requestDisallowInterceptTouchEvent(false);
                            }
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        shouldSwipe = false;
                        inSwipeMode = false;
                        mVideoViewPager.startAutoScroll(3000);
                        mVideoViewPager.requestDisallowInterceptTouchEvent(false);
                        mVideoViewPager.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;

                }
                return false;
            }
        });
        final GestureDetector mEntertainmentClickDetector = new GestureDetector(getActivity(), new EntertainmentPagerGesture());
        mEntertainmentViewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEntertainmentClickDetector.onTouchEvent(event);


                int mTouchSlop = 5;
                switch (event.getActionMasked()) {

                    case MotionEvent.ACTION_DOWN:
                        shouldSwipe = true;
                        inSwipeMode = false;
                        downY = (int) event.getY();
                        downX = (int) event.getX();

                        mEntertainmentViewpager.requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_MOVE:

                        int deltaY = (int) (event.getY() - downY);
                        int deltaX = (int) (event.getX() - downX);
                        if (!inSwipeMode && (Math.abs(deltaY) > mTouchSlop || Math.abs(deltaX) > mTouchSlop)) {
                            inSwipeMode = true;
                            if (Math.abs(deltaY) > mTouchSlop) {
                                shouldSwipe = false;
                            } else {
                                shouldSwipe = true;
                            }
                        }

                        if (inSwipeMode) {
                            if (shouldSwipe) {
                                mEntertainmentViewpager.stopAutoScroll();
                                mEntertainmentViewpager.requestDisallowInterceptTouchEvent(true);
                            } else {
                                mEntertainmentViewpager.requestDisallowInterceptTouchEvent(false);
                            }
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        shouldSwipe = false;
                        inSwipeMode = false;
                        mEntertainmentViewpager.startAutoScroll(3000);
                        mEntertainmentViewpager.requestDisallowInterceptTouchEvent(false);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;

                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                }
                return false;
            }
        });

        mSearchYahooEdt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            openYahooSearch(mSearchYahooEdt.getText().toString());
//                            Intent intent = new Intent(getActivity(), YahooSearchActivity.class);
//                            intent.putExtra("keyboard", mSearchYahooEdt.getText().toString());
//                            startActivity(intent);
//                            mSearchYahooEdt.setText("");
//                            CommonsUtils.hideKeyboard(getActivity());
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        mWallPaperRV.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                hitGA(mWallPaperList.get(position).getBannerImage(), mWallpapersHeaderTxt.getText().toString());
                launchWallpaperActivity(mWallPaperList.get(position).getBannerImage());
            }
        }));

        search_icon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openYahooSearch(mSearchYahooEdt.getText().toString());
            }
        });

        final GestureDetector banner1GestureDetector = new GestureDetector(getActivity(), new Banner1PagerGesture());
        mBanner1ViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int mTouchSlop = 5;
                banner1GestureDetector.onTouchEvent(event);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        shouldSwipe = true;
                        inSwipeMode = false;
                        downY = (int) event.getY();
                        downX = (int) event.getX();

                        mBanner1ViewPager.requestDisallowInterceptTouchEvent(true);
                        mBanner1ViewPager.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_MOVE:

                        int deltaY = (int) (event.getY() - downY);
                        int deltaX = (int) (event.getX() - downX);
                        if (!inSwipeMode && (Math.abs(deltaY) > mTouchSlop || Math.abs(deltaX) > mTouchSlop)) {
                            inSwipeMode = true;
                            if (Math.abs(deltaX) > mTouchSlop) {
                                shouldSwipe = true;
                                mBanner1ViewPager.stopAutoScroll();
                            } else {
                                shouldSwipe = false;
                            }
                        }

                        if (inSwipeMode) {
                            if (shouldSwipe) {
                                mBanner1ViewPager.requestDisallowInterceptTouchEvent(true);
                            } else {
                                mBanner1ViewPager.requestDisallowInterceptTouchEvent(false);
                            }
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        shouldSwipe = false;
                        inSwipeMode = false;
                        mBanner1ViewPager.startAutoScroll(3000);
                        mBanner1ViewPager.requestDisallowInterceptTouchEvent(false);
                        mBanner1ViewPager.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;

                }

                return false;
            }
        });

        final GestureDetector banner2GestureDetector = new GestureDetector(getActivity(), new Banner2PagerGesture());
        mBanner2ViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int mTouchSlop = 5;
                banner2GestureDetector.onTouchEvent(event);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        shouldSwipe = true;
                        inSwipeMode = false;
                        downY = (int) event.getY();
                        downX = (int) event.getX();

                        mBanner2ViewPager.requestDisallowInterceptTouchEvent(true);
                        mBanner2ViewPager.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_MOVE:

                        int deltaY = (int) (event.getY() - downY);
                        int deltaX = (int) (event.getX() - downX);
                        if (!inSwipeMode && (Math.abs(deltaY) > mTouchSlop || Math.abs(deltaX) > mTouchSlop)) {
                            inSwipeMode = true;
                            if (Math.abs(deltaX) > mTouchSlop) {
                                shouldSwipe = true;
                                mBanner2ViewPager.stopAutoScroll();
                            } else {
                                shouldSwipe = false;
                            }
                        }

                        if (inSwipeMode) {
                            if (shouldSwipe) {
                                mBanner2ViewPager.requestDisallowInterceptTouchEvent(true);
                            } else {
                                mBanner2ViewPager.requestDisallowInterceptTouchEvent(false);
                            }
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        shouldSwipe = false;
                        inSwipeMode = false;
                        mBanner2ViewPager.startAutoScroll(3000);
                        mBanner2ViewPager.requestDisallowInterceptTouchEvent(false);
                        mBanner2ViewPager.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;

                }

                return false;
            }
        });


    }

    void setUpBannerGesture() {

    }

    void openYahooSearch(String query) {

        if (!CommonsUtils.isConnectedToInternet(getActivity()) || TextUtils.isEmpty(query)) {
            return;
        }


        mSearchYahooEdt.setText("");
        Intent intent = new Intent(getActivity(), YahooSearchActivity.class);
        intent.putExtra("keyboard", query);

        String url = "";
        try {
            url = mCompleteData.getData().getYahooSearch().get(0).getUrl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        hitGA(url + "&p=" + query, "YahooSearch");
        intent.putExtra("url", url);
        startActivity(intent);
        mSearchYahooEdt.setText("");
        CommonsUtils.hideKeyboard(getActivity());
    }

    void setUpVideoAdapter() {
        setUpViewPager();
        setUpIndicator(mVideoIndicator);
    }

    void setUpIndicator(CirclePageIndicator mIndicator) {
//        final float density = getResources().getDisplayMetrics().density;
//        mIndicator.setRadius(7 * density);
    }

    void setUpWeather() {
        if (TextUtils.isEmpty(getAddressFromPref())) {
            mWeatherContainer.setVisibility(View.GONE);
            return;

        }

        mWeatherContainer.setVisibility(View.VISIBLE);
        if (mCompleteData != null) {
            if (mCompleteData.getData() != null) {
                List<Example.Weather> weather = mCompleteData.getData().getWeather();
                if (weather != null && weather.size() > 0) {
                    ImageLoader.getInstance().displayImage(weather.get(0).getImage(), mWeatherBgImageView, getVideoDisplayOptions());
                    ImageLoader.getInstance().displayImage(weather.get(0).getIcon(), type_image_textview);

                    weather_location_textview.setText(weather.get(0).getLocation());
                    date_textview.setText(weather.get(0).getDate());
                    type_textview.setText(weather.get(0).getType());
                    if (!TextUtils.isEmpty(weather.get(0).getTemperature())) {
                        degree_textview.setText(Html.fromHtml(weather.get(0).getTemperature()));
                    }

                }

            }


        }

    }


//    private void setUpWallpaperView() {
//
//        if (mWallPaperList == null || mWallPaperList.size() == 0) {
//            return;
//        }
//
//        final ImageView[] mImageView = new ImageView[mWallPaperList.size()];
//        LinearLayout.LayoutParams mLinearParams = new LinearLayout.LayoutParams(gettingWidth(100, getActivity()), gettingWidth(165, getActivity()));
//        mWallPaperContainerLLayout.removeAllViews();
//        for (int i = 0; i < mWallPaperList.size(); i++) {
//            mImageView[i] = new ImageView(getActivity());
//            mLinearParams.rightMargin = gettingWidth(10, getActivity());
//            mImageView[i].setId(i);
//            mImageView[i].setLayoutParams(mLinearParams);
//            ImageLoader.getInstance().displayImage(mWallPaperList.get(i).getBannerThumbImage(), mImageView[i], getVideoDisplayOptions());
//            mImageView[i].setScaleType(ImageView.ScaleType.FIT_XY);
//            mImageView[i].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    launchWallpaperActivity(mWallPaperList.get(v.getId()).getBannerImage());
//                }
//            });
//            mWallPaperContainerLLayout.addView(mImageView[i]);
//        }
//
//
//    }

    LinearLayout.LayoutParams mImageParams = null;

    private void setUpTrendingAppsView() {

        if (mTrendingAppList == null || mTrendingAppList.size() == 0) {
            return;
        }

        mTrendingAppsLLayout.removeAllViews();

        final ImageView[] mImageView = new ImageView[mTrendingAppList.size()];
        final TextView[] mAppName = new TextView[mTrendingAppList.size()];
        LinearLayout.LayoutParams mAppNameParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout mContainerlayout;
        int screentWidth = getScreenWidth(getActivity()) / 4 - gettingWidth(5, getActivity());
        LinearLayout.LayoutParams mContainerLayoutParams = new LinearLayout.LayoutParams(screentWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < mTrendingAppList.size(); i++) {

            mContainerlayout = new LinearLayout(getActivity());
            mContainerlayout.setGravity(Gravity.CENTER);
            mContainerlayout.setOrientation(LinearLayout.VERTICAL);
            mContainerlayout.setLayoutParams(mContainerLayoutParams);

            mImageView[i] = new ImageView(getActivity());
            mImageView[i].setId(i);
            mImageView[i].setLayoutParams(mImageParams);
            mImageView[i].setScaleType(ImageView.ScaleType.FIT_XY);
            ImageLoader.getInstance().displayImage(mTrendingAppList.get(i).getBannerThumbImage(), mImageView[i], getDisplayOptions());
            mContainerlayout.addView(mImageView[i]);

            mAppName[i] = new TextView(getActivity());
            mAppName[i].setText(mTrendingAppList.get(i).getTitle());
            mAppName[i].setMaxLines(1);
            mAppName[i].setEllipsize(TextUtils.TruncateAt.END);
            mAppName[i].setLayoutParams(mContainerLayoutParams);
            mAppNameParams.topMargin = 10;
            mAppName[i].setTextColor(Color.parseColor("#000000"));
            mAppName[i].setGravity(Gravity.CENTER);
            mAppName[i].setTextSize(13);
            mImageView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hitGA(mTrendingAppList.get(v.getId()).getRedirectLink(), CommonsUtils.TRENDING_APPS_EVENT_NAME);
                    openBrowser(mTrendingAppList.get(v.getId()).getRedirectLink());
                }
            });
            mContainerlayout.addView(mAppName[i]);

            mTrendingAppsLLayout.addView(mContainerlayout);
        }

    }

    private void settingTrendingGameView() {

        if (mTrendingGameList == null || mTrendingGameList.size() == 0) {
            return;
        }
        mTrendingGamesLLayout.removeAllViews();

        final ImageView[] mImageView = new ImageView[mTrendingGameList.size()];

        final TextView[] mAppName = new TextView[mTrendingGameList.size()];
        LinearLayout.LayoutParams mAppNameParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout mContainerlayout;
        int screentWidth = (getScreenWidth(getActivity()) / 4) - gettingWidth(5, getActivity());
        LinearLayout.LayoutParams mContainerLayoutParams = new LinearLayout.LayoutParams(screentWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < mTrendingGameList.size(); i++) {


            mContainerlayout = new LinearLayout(getActivity());
            mContainerlayout.setGravity(Gravity.CENTER);
            mContainerlayout.setOrientation(LinearLayout.VERTICAL);
            mContainerlayout.setLayoutParams(mContainerLayoutParams);

            mImageView[i] = new ImageView(getActivity());
            mImageView[i].setId(i);
            mImageView[i].setLayoutParams(mImageParams);
            mImageView[i].setScaleType(ImageView.ScaleType.FIT_XY);
            ImageLoader.getInstance().displayImage(mTrendingGameList.get(i).getBannerThumbImage(), mImageView[i], getDisplayOptions());
            mContainerlayout.addView(mImageView[i]);

            mAppName[i] = new TextView(getActivity());
            mAppName[i].setText(mTrendingGameList.get(i).getTitle());
            mAppName[i].setLayoutParams(mContainerLayoutParams);
            mAppName[i].setTextColor(Color.parseColor("#000000"));
            mAppName[i].setGravity(Gravity.CENTER);
            mAppNameParams.topMargin = 10;
            mAppName[i].setMaxLines(1);
            mAppName[i].setEllipsize(TextUtils.TruncateAt.END);
            mAppName[i].setTextSize(13);
            mContainerlayout.addView(mAppName[i]);
            mImageView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hitGA(mTrendingGameList.get(v.getId()).getRedirectLink(), CommonsUtils.TRENDING_GAMES_EVENT_NAME);
                    openBrowser(mTrendingGameList.get(v.getId()).getRedirectLink());
                }
            });
            mTrendingGamesLLayout.addView(mContainerlayout);

        }
    }

    void setUpSettings() {
        ImageLoader.getInstance().displayImage("drawable://" + R.drawable.lefty_news_set_up, mSettingImage);
    }


    String[] languageArray;
    String[] languageActualArray;
    List<Example.Language> mLanguageArrayList = null;
    List<Publication.Publisher> mPublicationArrayList = null;
    List<String> mPublisherList = new ArrayList<>();
    List<PublisherPojo> mPublisherPojoList = new ArrayList<>();
    PublisherAdapter mPublisherAdapter;


    void setUpNews() {
        if (mCompleteData != null) {
            if (mCompleteData.getData() != null) {
                mLanguageArrayList = null;
                mPublicationArrayList = null;
                mLanguageArrayList = mCompleteData.getData().getLanguages();
                if (mLanguageArrayList != null && mLanguageArrayList.size() > 0) {
                    languageArray = new String[mLanguageArrayList.size()];
                    languageActualArray = new String[mLanguageArrayList.size()];
                    for (int i = 0; i < mLanguageArrayList.size(); i++) {
                        languageArray[i] = mLanguageArrayList.get(i).getName();
                        languageActualArray[i] = mLanguageArrayList.get(i).getValue();
                    }
                }

            }


        }
    }

    void setUpEntertainmentAdapter() {


        if (mEntertainmentAdapter == null) {
            mEntertainmentAdapter = new EntertainmentAdapter();
            mEntertainmentViewpager.setAdapter(mEntertainmentAdapter);
            entertainment_indicator.setViewPager(mEntertainmentViewpager);
        } else {
            mEntertainmentAdapter.notifyDataSetChanged();
        }
    }

    String getDataFromPref() {
        return mLeftyPref.getString("response", null);
    }

    List<Example.News> newsList;

    public void setUpTOI() {
        mTOILayout.setVisibility(View.GONE);
        mSettingsLayout.setVisibility(View.GONE);
        mSettingSelectLanguageLayout.setVisibility(View.GONE);
        mSettingSelectPublisherLayout.setVisibility(View.GONE);
        small_setting_imageview.setVisibility(View.GONE);
        mLanguageTextView.setText("");
        mPublisher.setText("");
        if (mCompleteData != null) {
            if (mCompleteData.getData() != null) {
                mTOILayout.removeAllViews();
                newsList = null;
                newsList = mCompleteData.getData().getNews();
                if (newsList == null || newsList.size() == 0) {
                    mTOILayout.setVisibility(View.GONE);
                    mSettingsLayout.setVisibility(View.VISIBLE);
                    mSettingImage.setVisibility(View.VISIBLE);
                    small_setting_imageview.setVisibility(View.GONE);
                } else {
                    small_setting_imageview.setVisibility(View.VISIBLE);
                    mTOILayout.setVisibility(View.VISIBLE);
                    mSettingImage.setVisibility(View.GONE);
                    mSettingsLayout.setVisibility(View.GONE);
                }
                for (int i = 0; i < newsList.size(); i++) {
                    View newsLayout = getActivity().getLayoutInflater().inflate(R.layout.lefty_news_layout, null);
                    newsLayout.setId(i);
                    TextView titleTextView = (TextView) newsLayout.findViewById(R.id.news_title);
                    TextView contentTextView = (TextView) newsLayout.findViewById(R.id.news_content);
                    TextView timeTextView = (TextView) newsLayout.findViewById(R.id.news_time);
                    ImageView imageview = (ImageView) newsLayout.findViewById(R.id.news_image);

                    titleTextView.setText(newsList.get(i).getHeading());
                    if (!TextUtils.isEmpty(newsList.get(i).getDescription())) {
                        contentTextView.setVisibility(View.VISIBLE);
                        contentTextView.setText(newsList.get(i).getDescription());
                    } else {
                        contentTextView.setVisibility(View.GONE);
                    }

                    if (!TextUtils.isEmpty(newsList.get(i).getDatetime())) {
                        timeTextView.setVisibility(View.VISIBLE);
                        timeTextView.setText(newsList.get(i).getDatetime());
                    } else {
                        timeTextView.setVisibility(View.GONE);
                    }

                    timeTextView.setText(newsList.get(i).getDatetime());
                    ImageLoader.getInstance().displayImage(newsList.get(i).getImage(), imageview, new DisplayImageOptions.Builder()
                            .cacheOnDisk(false)
                            .cacheInMemory(false)
                            .bitmapConfig(Bitmap.Config.RGB_565)
                            .considerExifParams(true)
                            .build());
                    newsLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String deepLinkUrl = newsList.get(v.getId()).getRedirectLink();
                            if (!TextUtils.isEmpty(deepLinkUrl)) {
                                openTOI(deepLinkUrl);
                                hitGA(deepLinkUrl, mSettingHeaderTxt.getText().toString());
                            }

                        }
                    });
                    mTOILayout.addView(newsLayout);
                    if (i < newsList.size() - 1) {
                        View v = new View(getActivity());
                        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5));
                        v.setBackgroundResource(R.drawable.lefty_shade_drawable);
                        mTOILayout.addView(v);
                    }
                }
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        mVideoViewPager.stopAutoScroll();
        mEntertainmentViewpager.stopAutoScroll();
        mBanner1ViewPager.stopAutoScroll();
        mBanner2ViewPager.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            mVideoViewPager.startAutoScroll(3000);
            mEntertainmentViewpager.startAutoScroll(3000);
            mBanner1ViewPager.startAutoScroll(3000);
            mBanner2ViewPager.startAutoScroll(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setUpViewPager() {
        if (mCompleteData != null) {
            mVideoList = mCompleteData.getData().getVideo();
        }

        if (mAdapter == null) {
            mAdapter = new VideoImageAdapter();
            mVideoViewPager.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mVideoIndicator.setViewPager(mVideoViewPager);
        mVideoViewPager.setOffscreenPageLimit(4);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.settingImage:

                settingPreviouslySelectedLanguageTxt();
                mIsSettingsEnabled = true;
                mSettingSelectLanguageLayout.setVisibility(View.VISIBLE);
                mSettingSelectPublisherLayout.setVisibility(View.GONE);
                mSettingImage.setVisibility(View.GONE);

                break;

            case R.id.linear:
                if (mLanguageArrayList == null || mLanguageArrayList.size() == 0) {
                    return;
                }
                showingDialogToSelectLanguage();
                break;

            case R.id.linear1:
                mPublisherSelectedList = new ArrayList<>();
                showDialogForPublisher();
                break;

            case R.id.next:

                if (mLanguageArrayList == null || mLanguageArrayList.size() == 0) {
                    return;
                }

                if (TextUtils.isEmpty(mLanguageTextView.getText().toString())) {
                    Toast.makeText(getActivity(), "Select language", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (CommonsUtils.isConnectedToInternet(getActivity())) {
                    mPublicationTask = new GetPublicationsTask(getActivity(), mSelectedLang, new GetPublicationsTask.TaskCallbacks() {
                        @Override
                        public void onSucess(String response) {
                            mPublicationTask = null;
                            mPublisherPojoList = new ArrayList<>();
                            if (!TextUtils.isEmpty(response) && CommonsUtils.isJSONValid(response)) {

                                mSettingSelectLanguageLayout.setVisibility(View.GONE);
                                mSettingSelectPublisherLayout.setVisibility(View.VISIBLE);
                                mSettingImage.setVisibility(View.GONE);

                                Gson gson = new Gson();

                                Publication mPublication = gson.fromJson(response, Publication.class);
                                if (mPublication != null && mPublication.getSuccess()) {
                                    mPublicationArrayList = mPublication.getPublisher();

                                    for (int i = 0; i < mPublicationArrayList.size(); i++) {

                                        String mLanguageCode = mPublicationArrayList.get(i).getLanguageCode();
                                        String mLanguage = mPublicationArrayList.get(i).getLanguage();
                                        PublisherPojo mpojo = new PublisherPojo();
                                        mpojo.setHeader(mPublicationArrayList.get(i).getLanguage());
                                        mpojo.setIsHeader(true);
                                        mpojo.setIsSelected(false);
                                        mPublisherPojoList.add(mpojo);

                                        List<Publication.Datum> mInnerList = mPublicationArrayList.get(i).getData();
                                        if (mInnerList != null && mInnerList.size() == 0) {

                                        } else {
                                            for (int j = 0; j < mInnerList.size(); j++) {
                                                mpojo = new PublisherPojo();
                                                mpojo.setPublisher(mInnerList.get(j).getAppnameEng());
                                                mpojo.setIsHeader(false);
                                                mpojo.setLanguage(mLanguage);
                                                mpojo.setLanguageCode(mLanguageCode);
                                                mpojo.setIsSelected(false);
                                                mPublisherPojoList.add(mpojo);
                                            }
                                        }

                                    }

                                } else {
                                    Toast.makeText(getActivity(), "No publication found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure() {
                            mPublisherPojoList = new ArrayList<>();
                            mPublicationTask = null;
                            Toast.makeText(getActivity(), "No publication found", Toast.LENGTH_SHORT).show();
                        }
                    });

                    TaskHelper.execute(mPublicationTask);

                }


                break;

            case R.id.previouscc:

                mPublisherList = new ArrayList<>();
                mPublisherPojoList = new ArrayList<>();
                mSettingSelectLanguageLayout.setVisibility(View.VISIBLE);
                mSettingSelectPublisherLayout.setVisibility(View.GONE);
                mSettingImage.setVisibility(View.GONE);
                mPublisher.setText("");
                break;

            case R.id.proceed:

                if (TextUtils.isEmpty(mPublisher.getText().toString())) {
                    Toast.makeText(getActivity(), "Select publisher", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mPublisherList == null || mPublisherList.size() <= 0) {
                    return;
                }
                String mLCode = TextUtils.join(",", mSelectedPubLanguageCode);
                String mL = TextUtils.join(",", mSelectedPubLanguage);
                String pubs = TextUtils.join(",", mPublisherList);
                if (CommonsUtils.isConnectedToInternet(getActivity())) {
                    mSaveSettingsTask = new SaveSettingsTask(getActivity(), mL, mLCode, pubs, new GetPublicationsTask.TaskCallbacks() {
                        @Override
                        public void onSucess(String response) {
                            mSaveSettingsTask = null;
                            if (!TextUtils.isEmpty(response)) {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    if (obj != null) {
                                        boolean success = obj.getBoolean("success");
                                        if (success) {
                                            saveLanguageInPref(mSelectedLang);//{"deeplink":"newspoint:\/\/save$\/$pubs=1:India TV&lang=english"}
                                            mLanguageBoolArray = null;
                                            mLanguagesPositionBoolean = null;
                                            System.gc();

                                            String deepLink = obj.getString("data");
                                            if (!TextUtils.isEmpty(deepLink) && CommonsUtils.isJSONValid(deepLink)) {

                                                try {
                                                    JSONObject mDeepJson = new JSONObject(deepLink);
                                                    String dd = mDeepJson.getString("deeplink");
                                                    openTOI(dd);

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }


                                                if (CommonsUtils.isConnectedToInternet(getActivity())) {
                                                    ApiTask mTask = new ApiTask();
                                                    TaskHelper.execute(mTask, getAddressFromPref());
                                                }

                                            }
                                        }
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure() {
                            mSaveSettingsTask = null;
                        }
                    });
                    TaskHelper.execute(mSaveSettingsTask);
                } else {
                    Toast.makeText(getActivity(), "You are offline", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.small_setting_imageview:

                settingPreviouslySelectedLanguageTxt();
                if (mIsSmallSettingClicked) {
                    mIsSmallSettingClicked = false;

                    mTOILayout.setVisibility(View.VISIBLE);
                    //mSettingImage.setVisibility(View.VISIBLE);
                    mSettingsLayout.setVisibility(View.GONE);
                    mSettingSelectLanguageLayout.setVisibility(View.GONE);
                    mSettingSelectPublisherLayout.setVisibility(View.GONE);
                } else {
                    mIsSmallSettingClicked = true;

                    mTOILayout.setVisibility(View.GONE);
                    mSettingImage.setVisibility(View.GONE);
                    mSettingsLayout.setVisibility(View.VISIBLE);
                    mSettingSelectLanguageLayout.setVisibility(View.VISIBLE);
                }


                break;
            default:
                break;

        }

    }

    boolean mIsSmallSettingClicked;
    GetPublicationsTask mPublicationTask;
    SaveSettingsTask mSaveSettingsTask = null;

    class VideoImageAdapter extends PagerAdapter {


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mVideoList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = getActivity().getLayoutInflater().inflate(R.layout.lefty_view_pager_row, view, false);
            ImageView mImageview = (ImageView) imageLayout.findViewById(R.id.pagerImageView);
            ImageLoader.getInstance().displayImage(mVideoList.get(position).getThumb(), mImageview, getVideoDisplayOptions());
            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

    }


    Banner1ImageAdapter mBanner1Adapter;
    Banner2ImageAdapter mBanner2Adapter;

    void setUpBanner1Adapter() {
        if (mCompleteData != null) {
            mBottomList = mCompleteData.getData().getBottomAdvertisement();
            mTopList = mCompleteData.getData().getTopAdvertisement();
        }
        if (mBanner1Adapter == null) {
            mBanner1Adapter = new Banner1ImageAdapter();
            mBanner1ViewPager.setAdapter(mBanner1Adapter);
        } else {
            mBanner1Adapter.notifyDataSetChanged();
        }


        mBanner1Indicator.setViewPager(mBanner1ViewPager);
        mBanner1ViewPager.setOffscreenPageLimit(3);

        if (mBanner2Adapter == null) {
            mBanner2Adapter = new Banner2ImageAdapter();
            mBanner2ViewPager.setAdapter(mBanner2Adapter);
        } else {
            mBanner2Adapter.notifyDataSetChanged();
        }
        mBanner2Indicator.setViewPager(mBanner2ViewPager);
        mBanner2ViewPager.setOffscreenPageLimit(3);


        //final float density = getResources().getDisplayMetrics().density;
//        mBanner1Indicator.setRadius(7 * density);
//        mBanner2Indicator.setRadius(7 * density);
    }

    class Banner1ImageAdapter extends PagerAdapter {

        @Override

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mTopList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = getActivity().getLayoutInflater().inflate(R.layout.lefty_viewpager_weather_row, view, false);
            ImageView mImageview = (ImageView) imageLayout.findViewById(R.id.pagerImageView);
            ImageLoader.getInstance().displayImage(mTopList.get(position).getBannerImage(), mImageview, getVideoDisplayOptions());
            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

    }

    class Banner2ImageAdapter extends PagerAdapter {

        @Override

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mBottomList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = getActivity().getLayoutInflater().inflate(R.layout.lefty_viewpager_weather_row, view, false);
            ImageView mImageview = (ImageView) imageLayout.findViewById(R.id.pagerImageView);
            //ImageLoader.getInstance().displayImage(mVideoList.get(position).getThumb(), mImageview, getVideoDisplayOptions());
            ImageLoader.getInstance().displayImage(mBottomList.get(position).getBannerImage(), mImageview, getVideoDisplayOptions());
            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

    }

    String getAddressFromPref() {
        return mLeftyPref.getString("address", null);
    }

    //    String[] mLanguages = {"Hindi", "English", "English(United States)", "Arbian", "Spanish", "Punjabi"};
    boolean[] mLanguagesPositionBoolean;
    boolean[] mLanguageBoolArray;
    ArrayList<String> mLanguagesList;
    String mSelectedLang = "";

    private Dialog showingDialogToSelectLanguage() {


        if (mLanguagesPositionBoolean == null) {
            mLanguagesPositionBoolean = new boolean[languageArray.length];
            String languageString = getLanguageFromPref();
            if (!TextUtils.isEmpty(languageString)) {
                if (languageString.endsWith("-")) {
                    languageString = languageString.substring(0, languageString.length() - 1);
                }
                String langPref[] = languageString.split("-");
                List<String> stringList = new ArrayList<>(Arrays.asList(langPref));
                List<String> mainList = new ArrayList<>(Arrays.asList(languageActualArray));
                for (int i = 0; i < stringList.size(); i++) {
                    String lang = stringList.get(i);
                    if (mainList.contains(lang)) {
                        mLanguagesPositionBoolean[mainList.indexOf(lang)] = true;
                    }

                }
            }

        }
        mLanguageBoolArray = new boolean[mLanguagesPositionBoolean.length];
        for (int i = 0; i < mLanguagesPositionBoolean.length; i++) {
            mLanguageBoolArray[i] = mLanguagesPositionBoolean[i];
        }


        return new AlertDialog.Builder(getActivity()).setTitle("Select Language").setMultiChoiceItems(languageArray,
                mLanguageBoolArray, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    }
                })
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        mLanguagesPositionBoolean = new boolean[mLanguageBoolArray.length];
                        for (int i = 0; i < mLanguageBoolArray.length; i++) {
                            mLanguagesPositionBoolean[i] = mLanguageBoolArray[i];
                        }
                        StringBuilder mBuilder = new StringBuilder();
                        mLanguagesList = gettingSelectLanguages(mLanguagesPositionBoolean, languageArray);
                        if (mLanguagesList.size() > 0) {
                            for (int i = 0; i < mLanguageArrayList.size(); i++) {

                                if (mLanguagesPositionBoolean[i]) {
                                    String value = mLanguageArrayList.get(i).getValue();
                                    if (i == mLanguageArrayList.size() - 1) {
                                        mBuilder.append(value);

                                    } else {
                                        mBuilder.append(value + "-");
                                    }
                                }


                            }
                            mSelectedLang = mBuilder.toString();
                            mLanguageTextView.setText(mLanguagesList.size() + " Language(s) Selected");
                            /*if (mLanguagesList.size() > 3) {
                                mLanguageTextView.setText(mLanguagesList.size() + " Language(s) Selected");
                            } else {
                                mLanguageTextView.setText(TextUtils.join(",", mLanguagesList));
                            }*/
                            mNext.setBackground(getResources().getDrawable(R.drawable.lefty_next_bg_active));
                            dialog.dismiss();
                        } else {
                            mLanguageTextView.setText("");
                            mNext.setBackground(getResources().getDrawable(R.drawable.lefty_next_bg_inactive));
                        }


                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }


//    List<String>


    private void setTitles() {
        /*mWallpapersHeaderTxt.setText(CommonsUtils.WALLPAPER_TITLE);
        mYouTubeHeaderTxt.setText(CommonsUtils.VIDEOS_TITLE);
        mSettingHeaderTxt.setText(CommonsUtils.NEWS_TITLE);
        mTwiterHeaderTxt.setText(CommonsUtils.TWITTER_TITLE);
        mTrendingHeaderTxt.setText(CommonsUtils.TRENDING_APPS_TITLE);
        mTrendingGamesHeaderTxt.setText(CommonsUtils.TRENDING_GAMES_TITLE);
        mEntertainmentHeaderTxt.setText(CommonsUtils.ENTERTAINMENT_TITLE);
        mTwitterTrendingNewsTxt.setText(CommonsUtils.TWITTER_TRENDING_NEWS_TITLE);*/
        List<Example.Title> mTitleList = new ArrayList<>();
        if (mCompleteData != null) {
            mTitleList = mCompleteData.getData().getTitles();
            if (mTitleList != null && mTitleList.size() > 0) {
                mWallpapersHeaderTxt.setText(mTitleList.get(0).getWallpaper());
                mYouTubeHeaderTxt.setText(mTitleList.get(0).getVideo());
                mSettingHeaderTxt.setText(mTitleList.get(0).getNews());
                mTwiterHeaderTxt.setText(mTitleList.get(0).getTwitter());
                mTrendingHeaderTxt.setText(mTitleList.get(0).getCpa());
                mTrendingGamesHeaderTxt.setText(mTitleList.get(0).getGames());
                mEntertainmentHeaderTxt.setText(mTitleList.get(0).getEntertainment());
                mTwitterTrendingNewsTxt.setText(mTitleList.get(0).getTrendingNews());
            }
        } else {
            mWallpapersHeaderTxt.setText(CommonsUtils.WALLPAPER_TITLE);
            mYouTubeHeaderTxt.setText(CommonsUtils.VIDEOS_TITLE);
            mSettingHeaderTxt.setText(CommonsUtils.NEWS_TITLE);
            mTwiterHeaderTxt.setText(CommonsUtils.TWITTER_TITLE);
            mTrendingHeaderTxt.setText(CommonsUtils.TRENDING_APPS_TITLE);
            mTrendingGamesHeaderTxt.setText(CommonsUtils.TRENDING_GAMES_TITLE);
            mEntertainmentHeaderTxt.setText(CommonsUtils.ENTERTAINMENT_TITLE);
            mTwitterTrendingNewsTxt.setText(CommonsUtils.TWITTER_TRENDING_NEWS_TITLE);

        }


        mYouTubeIcon.setBackgroundResource(R.drawable.lefty_viedo);
        mWallpapersIcon.setImageResource(R.drawable.lefty_wallpaper);
        mSettingIcon.setImageResource(R.drawable.lefty_news);
        mTrendingAppIcon.setImageResource(R.drawable.lefty_apps);
        mTrendingGameIcon.setImageResource(R.drawable.lefty_games);
        mEntertainmentIcon.setImageResource(R.drawable.lefty_viedo);
        mTwitterIcon.setBackgroundResource(R.drawable.lefty_twitter);
        mTwitterTrendingIcon.setBackgroundResource(R.drawable.lefty_twitter);
    }

    private void showDialogForPublisher() {

        final Dialog dialog = new Dialog(getActivity(), R.style.cust_dialog);
        dialog.setContentView(R.layout.lefty_publisher_list_dialog);

        ListView mPublisherListView = (ListView) dialog.findViewById(R.id.publisher_list);
        TextView mCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView mDone = (TextView) dialog.findViewById(R.id.done);
        dialog.setCancelable(true);
        dialog.setTitle("Select Publisher");
        dialog.show();
        //settingDataForPublisher();

        mPublisherAdapter = new PublisherAdapter();
        mPublisherListView.setAdapter(mPublisherAdapter);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

//                if (mPublisherPojoList != null && mPublisherPojoList.size() > 0) {
//                    for (int i = 0; i < mPublisherPojoList.size(); i++) {
//                        mPublisherPojoList.get(i).setIsSelected(false);
//                    }
//                }
            }
        });

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();


                mPublisherList = settingDataInPublisherList();
                if (mPublisherList == null) {
                    return;
                }

                if (mPublisherList.size() > 0) {
                    mPublisher.setText(mPublisherList.size() + " Publisher(s) Selected");
                    /*if (mPublisherList.size() > 3) {
                        mPublisher.setText(mPublisherList.size() + " Publisher(s) Selected");
                    } else {
                        mPublisher.setText(TextUtils.join(",", mPublisherList));
                    }*/
                    mProceed.setBackground(getResources().getDrawable(R.drawable.lefty_next_bg_active));
                } else {
                    mPublisher.setText("");
                    mProceed.setBackground(getResources().getDrawable(R.drawable.lefty_next_bg_inactive));
                }

            }
        });

    }

    private void settingPreviouslySelectedLanguageTxt() {
        String languageString = getLanguageFromPref();

        if (TextUtils.isEmpty(languageString)) {
            return;
        }

        if (languageString.endsWith("-")) {
            languageString = languageString.substring(0, languageString.length() - 1);
        }
        String langPref[] = languageString.split("-");

        Iterator it = mCompleteData.getData().getLanguages().iterator();
        String selectedLanguage = "";

        for (int i = 0; i < langPref.length; i++) {

            while (it.hasNext()) {

                Example.Language language = (Example.Language) it.next();
                if (language.getValue().equalsIgnoreCase(langPref[i])) {
                    selectedLanguage = selectedLanguage + language.getName() + ",";
                    break;
                }
            }


        }

        mNext.setBackground(getResources().getDrawable(R.drawable.lefty_next_bg_active));
        if (langPref.length > 3) {
            mLanguageTextView.setText(langPref.length + " Language(s) Selected");
        } else {

            if (selectedLanguage.endsWith(",")) {
                selectedLanguage = selectedLanguage.substring(0, selectedLanguage.length() - 1);
            }

            languageString = languageString.replace("-", ",");
            mLanguageTextView.setText(selectedLanguage);
        }
    }

    List<String> mSelectedPubLanguage = new ArrayList<>();
    List<String> mSelectedPubLanguageCode = new ArrayList<>();

    private List<String> settingDataInPublisherList() {
        if (mPublisherPojoList == null) {
            return null;
        }
        mPublisherList = new ArrayList<>();
        mSelectedPubLanguage = new ArrayList<>();
        mSelectedPubLanguageCode = new ArrayList<>();

        for (int i = 0; i < mPublisherPojoList.size(); i++) {
            if (mPublisherPojoList.get(i).isSelected() && !mPublisherPojoList.get(i).isHeader()) {
                mPublisherList.add(mPublisherPojoList.get(i).getPublisher());
                mSelectedPubLanguage.add(mPublisherPojoList.get(i).getLanguage());
                mSelectedPubLanguageCode.add(mPublisherPojoList.get(i).getLanguageCode());
            }
        }

        return mPublisherList;
    }


    private ArrayList<String> gettingSelectLanguages(boolean[] databoolean, String[] data) {
        ArrayList<String> rents = new ArrayList<>();
        String rentmonths = "";
        for (int i = 0; i < databoolean.length; i++) {

            if (databoolean[i]) {
                rents.add(data[i]);
            }
        }

        return rents;


    }

    public static int gettingWidth(int width, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (width * scale);
    }

    public class PublisherAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return mPublisherPojoList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        ViewHolder holder;

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            holder = new ViewHolder();
            if (convertView == null) {

                convertView = getActivity().getLayoutInflater().inflate(R.layout.lefty_publisher_list_raw, null);
                holder.mPublisherCheckBox = (CheckBox) convertView.findViewById(R.id.publisher_checkbox);
                holder.mPublisherHeader = (TextView) convertView.findViewById(R.id.publisher_header);
                convertView.setTag(holder);

            }

            holder = (ViewHolder) convertView.getTag();


            holder.mPublisherCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mPublisherPojoList.get(position).setIsSelected(isChecked);
                }
            });


            if (mPublisherPojoList.get(position).isHeader()) {
                holder.mPublisherHeader.setText(mPublisherPojoList.get(position).getHeader());
                holder.mPublisherHeader.setVisibility(View.VISIBLE);
                holder.mPublisherCheckBox.setVisibility(View.GONE);

            } else {
                holder.mPublisherCheckBox.setText(mPublisherPojoList.get(position).getPublisher());
                holder.mPublisherCheckBox.setChecked(mPublisherPojoList.get(position).isSelected());
                holder.mPublisherHeader.setVisibility(View.GONE);
                holder.mPublisherCheckBox.setVisibility(View.VISIBLE);
            }

            return convertView;


        }

        public class ViewHolder {
            TextView mPublisherHeader;
            CheckBox mPublisherCheckBox;
        }
    }


    List<WallpapersPojo> mWallpapersList = new ArrayList<>();



    public static int getScreenWidth(Activity context) {

        Display display = context.getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            display.getSize(size);
            return size.x;
        }
        return display.getWidth();
    }


//    private void setUpIndicatorForEntertainment() {
//        mEntertainmentViewpager.setAdapter(new EntertainmentAdapter());
//        final ImageView[] mImageView = new ImageView[2];
//        LinearLayout.LayoutParams mLinearParams = new LinearLayout.LayoutParams(gettingWidth(15, getActivity()), gettingWidth(15, getActivity()));
//
//        for (int i = 0; i < 2; i++) {
//
//            mImageView[i] = new ImageView(getActivity());
//            mLinearParams.rightMargin = 20;
//            mImageView[i].setId(i);
//            mImageView[i].setLayoutParams(mLinearParams);
//            mImageView[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
//            mEntertainmentIndicatorContainerLLayout.addView(mImageView[i]);
//
//            if (i == 0) {
//                mImageView[i].setImageResource(R.drawable.dote_active);
//            } else {
//                mImageView[i].setImageResource(R.drawable.blank_b);
//            }
//
//            mImageView[i].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int id = v.getId();
//                    for (int i = 0; i < 2; i++) {
//                        if (i == id) {
//                            mImageView[i].setImageResource(R.drawable.dote_active);
//                            mEntertainmentViewpager.setCurrentItem(id, true);
//                        } else {
//                            mImageView[i].setImageResource(R.drawable.blank_b);
//                        }
//
//                    }
//                }
//            });
//
//        }
//    }


    private class EntertainmentAdapter extends PagerAdapter {


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            if (mEntertainmentList == null || mEntertainmentList.size() == 0) {
                return 0;
            } else {
                return mEntertainmentList.size();
            }

        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = getActivity().getLayoutInflater().inflate(R.layout.lefty_view_pager_row, view, false);
            ((ImageView) imageLayout.findViewById(R.id.play_icon)).setVisibility(View.GONE);
            ImageView mImageview = (ImageView) imageLayout.findViewById(R.id.pagerImageView);
            ImageLoader.getInstance().displayImage(mEntertainmentList.get(position).getBannerThumbImage(), mImageview, getVideoDisplayOptions());
            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

    }

    public DisplayImageOptions getVideoDisplayOptions() {
        return new DisplayImageOptions.Builder()//.displayer(new RoundedBitmapDisplayer(10000))
                //.showImageForEmptyUri(R.drawable.my_info)
                //.showImageOnFail(R.drawable.my_info)
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .considerExifParams(true)
                .build();
    }

    void openTOI(String deepLinkURL) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(deepLinkURL));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "No app found", Toast.LENGTH_SHORT).show();
        }

    }

    public class ApiTask extends AsyncTask<String, Void, String> {

        String deviceId = "";
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String address = CommonsUtils.NO_LOCATION;
            String adress = getAddressFromPref();
            if (!TextUtils.isEmpty(adress)) {
                address = adress;
            }
            if (!TextUtils.isEmpty(params[0])) {
                address = params[0];
            }
            deviceId = CommonsUtils.getSecureDeviceId(getActivity());
            String language = getLanguageFromPref();

            String url;
            if (!TextUtils.isEmpty(language)) {
                if (language.endsWith("-")) {
                    language = language.substring(0, language.length() - 1);
                }
                url = CommonsUtils.GET_DATA + deviceId + "/" + address + "/" + language;
            } else {
                url = CommonsUtils.GET_DATA + deviceId + "/" + address;
            }

            try {
                response = CommonsUtils.requestWebService(url);
                if (!TextUtils.isEmpty(response) && CommonsUtils.isJSONValid(response)) {
                    Gson gson = new Gson();
                    gson.fromJson(response, Example.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            Log.e("Response", "" + response);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (!TextUtils.isEmpty(response) && CommonsUtils.isJSONValid(response)) {
                saveDataInPref(response);
                refreshFragment();
            }


        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    public void saveDataInPref(String response) {
        SharedPreferences.Editor editor = mLeftyPref.edit();
        editor.putString("response", "" + response);
        editor.commit();
    }

    public void saveLanguageInPref(String response) {
        Log.e("Language saved", "" + response);
        SharedPreferences.Editor editor = mLeftyPref.edit();
        editor.putString("language", "" + response);
        editor.commit();
    }

    public String getLanguageFromPref() {
        return mLeftyPref.getString("language", "");
    }


    private class YoutubePagerGesture extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mVideoList != null && mVideoList.size() > 0) {
                openYouTube(mVideoList.get(mVideoViewPager.getCurrentItem()).getYoutubeLink());
                hitGA(mVideoList.get(mVideoViewPager.getCurrentItem()).getYoutubeLink(), mYouTubeHeaderTxt.getText().toString());
            }

            return super.onSingleTapUp(e);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            GoogleAnalytics.getInstance(getActivity()).reportActivityStart(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            GoogleAnalytics.getInstance(getActivity()).reportActivityStop(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void hitGA(String actionName, String category) {
        mActivity.sendEvent(actionName, category, CommonsUtils.NATIVE_SCREEN_NAME);
    }

    private class Banner1PagerGesture extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            try {
                String redirectLink = mTopList.get(mBanner1ViewPager.getCurrentItem()).getRedirectLink();
                openBrowser(redirectLink);
                hitGA(redirectLink, CommonsUtils.TOP_BANNER_EVENT_NAME);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return super.onSingleTapUp(e);
        }
    }

    private class Banner2PagerGesture extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            try {
                String redirectLink = mBottomList.get(mBanner2ViewPager.getCurrentItem()).getRedirectLink();
                openBrowser(redirectLink);
                hitGA(redirectLink, CommonsUtils.BOTTOM_BANNER_EVENT_NAME);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return super.onSingleTapUp(e);
        }
    }


    private void openYouTube(String uri) {
        String id = Uri.parse(uri).getQueryParameter("v");
        Intent youTubeIntent = new Intent(getActivity(), PlayYoutubeActivity.class);
        youTubeIntent.putExtra("id", id);
        startActivity(youTubeIntent);

    }

    TimelineResult<Tweet> mTwoTweetsTimeline;
    TimelineResult<Tweet> mFourTweetsTimeline;

    private void twittes() {

        if (mTwiterList == null || mTwiterList.size() == 0) {
            return;
        }
//        CollectionTimeline timeline = new CollectionTimeline.Builder().id(743439345550524416L).maxItemsPerRequest(2).build();
        CollectionTimeline timeline = new CollectionTimeline.Builder().id(Long.parseLong(mTwiterList.get(0).getCollectionId())).maxItemsPerRequest(Integer.parseInt(mTwiterList.get(0).getTweet_count())).build();
        timeline.next(null, new Callback<TimelineResult<Tweet>>() {
            @Override
            public void success(Result<TimelineResult<Tweet>> result) {
                mTwoTweetsTimeline = result.data;
                if (mTwoTweetsTimeline != null) {
                    List<Tweet> tweet = mTwoTweetsTimeline.items;
                    loadTweets(tweet);
                }

            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("twitter", "failure" + exception.getMessage());
            }
        });


    }

    private void twittesTrending() {

        if (mTwiterList == null || mTwiterList.size() <= 1) {
            return;
        }
        //CollectionTimeline timeline = new CollectionTimeline.Builder().id(743422922220519430L).maxItemsPerRequest(4).build();
        CollectionTimeline timeline = new CollectionTimeline.Builder().id(Long.parseLong(mTwiterList.get(1).getCollectionId())).maxItemsPerRequest(Integer.parseInt(mTwiterList.get(1).getTweet_count())).build();

        timeline.next(null, new Callback<TimelineResult<Tweet>>() {
            @Override
            public void success(Result<TimelineResult<Tweet>> result) {

                mFourTweetsTimeline = result.data;
                if (mFourTweetsTimeline != null) {
                    List<Tweet> tweet = mFourTweetsTimeline.items;
                    loadTrendingTweets(tweet);
                }

            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("twitter", "failure" + exception.getMessage());
            }
        });


    }

    /**
     * loading tweets add adding in tweetcontainer
     *
     * @param tweet
     */
    int count = 0;

    private void loadTweets(List<Tweet> tweet) {
        count = 0;

        if (tweet == null || tweet.size() == 0) {
            return;
        }

        List<Long> ids = new ArrayList<Long>();

        for (int i = 0; i < tweet.size(); i++) {
            ids.add(tweet.get(i).getId());
        }


        TweetUtils.loadTweets(ids, new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                if (mTweetContainer != null) mTweetContainer.removeAllViews();
                for (Tweet tweet : result.data) {
                    try {
                        count = count + 1;

                        CompactTweetView mTweetView = new CompactTweetView(getActivity(), tweet);
                        if (mTweetContainer != null) mTweetContainer.addView(mTweetView);
                        if (count == 1) {
                            View v = new View(getActivity());
                            v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5));
                            v.setBackgroundResource(R.drawable.lefty_shade_drawable);
                            if (mTweetContainer != null) mTweetContainer.addView(v);
                        }
                    } catch (Exception e) {

                    }

                }
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
//
    }


    int trendingCount = 0;

    private void loadTrendingTweets(List<Tweet> tweet) {
        trendingCount = 0;

        if (tweet == null || tweet.size() == 0) {
            return;
        }

        List<Long> ids = new ArrayList<Long>();

        for (int i = 0; i < tweet.size(); i++) {
            ids.add(tweet.get(i).getId());
        }


        TweetUtils.loadTweets(ids, new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                twiter_trending_news_layout.removeAllViews();
                for (Tweet tweet : result.data) {
                    //trendingCount = trendingCount + 1;

                    CompactTweetView mTweetView = new CompactTweetView(getActivity(), tweet);

//                    final BaseTweetView tv = new TweetView(getActivity(), tweet, R.style.tw__TweetLightWithActionsStyle);
//                    tv.setOnActionCallback(null);
//                    tv.setId(tv.getId());

                    twiter_trending_news_layout.addView(mTweetView);
                    //if (trendingCount == 1 || trendingCount == 3) {
                    View v = new View(getActivity());
                    v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5));
                    v.setBackgroundResource(R.drawable.lefty_shade_drawable);
                    twiter_trending_news_layout.addView(v);
                    //}
                }
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
//
    }

    void launchWallpaperActivity(String url) {
        if (CommonsUtils.isConnectedToInternet(getActivity())) {
            Intent intent = new Intent(getActivity(), WallpaperSetUpActivity.class);
            intent.putExtra("url", "" + url);
            startActivity(intent);
        }

    }


    void openBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    WallpaperAdapter mWallpapterAdapter;
    RecyclerView mWallPaperRV;

    private void setWallpapersAdapterRV() {

        if (mWallpapersList == null || mWallPaperList.size() == 0) {
            mWallPaperRV.setVisibility(View.GONE);
        } else {
            mWallPaperRV.setVisibility(View.VISIBLE);
        }
        if (mWallpapterAdapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mWallPaperRV.setLayoutManager(linearLayoutManager);
            mWallPaperRV.setHasFixedSize(false);
            mWallpapterAdapter = new WallpaperAdapter();
            mWallPaperRV.setAdapter(mWallpapterAdapter);
        } else {
            mWallpapterAdapter.notifyDataSetChanged();
        }


    }

    private class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.MyViewHolder> {

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView mWallpaperIMG;
            LinearLayout mWallpaperMainLayoutLL;

            public MyViewHolder(View view) {
                super(view);
                mWallpaperIMG = (ImageView) view.findViewById(R.id.wallpaper);
                mWallpaperMainLayoutLL = (LinearLayout) view.findViewById(R.id.wallpaper_main_layout);
            }
        }

        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, final int position) {

            //int width = getScreenWidth(getActivity()) / 3 - gettingWidth(5, getActivity());
            LinearLayout.LayoutParams mLinearParams = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
            myViewHolder.mWallpaperMainLayoutLL.setLayoutParams(mLinearParams);
            ImageLoader.getInstance().displayImage(mWallPaperList.get(position).getBannerThumbImage(), myViewHolder.mWallpaperIMG, getVideoDisplayOptions());

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.lefty_wallpapers_row, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public int getItemCount() {
            if (mWallPaperList == null || mWallPaperList.size() == 0) {
                return 0;
            } else {
                return mWallPaperList.size();
            }
        }
    }

    public DisplayImageOptions getDisplayOptions() {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(25))
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .considerExifParams(true)

                .build();

        return options;

    }

    private class EntertainmentPagerGesture extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            //Toast.makeText(getActivity(), "clicked " + mVideoViewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();
            hitGA(mEntertainmentList.get(mEntertainmentViewpager.getCurrentItem()).getRedirectLink(), CommonsUtils.ENTERTAINMENT_EVENT_NAME);
            openBrowser(mEntertainmentList.get(mEntertainmentViewpager.getCurrentItem()).getRedirectLink());
            return super.onSingleTapUp(e);
        }
    }
}
