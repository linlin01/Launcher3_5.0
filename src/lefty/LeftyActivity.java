package lefty;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.launcher3.Launcher;
import com.android.launcher3.R;
import com.android.launcher3.Utilities;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.ref.WeakReference;
import java.util.List;
import lefty.ApiCalls.GcmCall;
import lefty.models.Example;
import lefty.webundle.WebViewFragment;
import android.util.Log;
/**
 * Created by tajinder on 14/6/16.
 */
public class LeftyActivity extends Launcher implements LauncherActivityUpdater.Updater {

    private static WeakReference<LeftyActivity> wrActivity = null;

    ApiTask mTask = null;

    Lefty_MainActivity mNativeFragment;
    WebViewFragment mFragmentWeb;
    CountDownTimer mBannerCountdowntimer;
    AdvertismentDialogFragment dialogFragment;
    boolean flag=false;
    SharedPreferences getSharedPreferences() {
        return getSharedPreferences("Lefty", MODE_PRIVATE);
    }

    @Override
    protected boolean hasCustomContentToLeft() {
        return Utilities.isLeftyAllowedPrefEnabled(getApplicationContext(), false);
    }

    RelativeLayout mCustomView;


    GcmCall mGcmCallTask;
    //    ViewStub mNativeViewStub;
    //    ViewStub mWebViewStub;
    ImageView mSplashImageView;
    ImageView mNativeImageView;


    String mApplicationType = "";
    Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wrActivity = new WeakReference<>(this);
        LeftyApplication.setmCActivity(this);
        createLocationRequest();
        LauncherActivityUpdater.getInstance().setListeners(this);
    }

    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.getInstance().setUserIdentifier("12345");
        Crashlytics.getInstance().setUserEmail("user@fabric.io");
        Crashlytics.getInstance().setUserName("Test User");
    }

    @Override
    protected void populateCustomContentContainer() {
//        if (mCustomView != null) {
//            //getWorkspace().removeCustomContentPage();
//            mCustomView = null;
//        }
//        if (mCustomView != null){
//            mCustomView.removeAllViews();
//            mCustomView =null;
//        }

        try {
            mCustomView = (RelativeLayout) getLayoutInflater().inflate(R.layout.lefty_main_container, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Button mCrashButton = (Button) mCustomView.findViewById(R.id.crashButton);
//        mCrashButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                throw new RuntimeException("This exception thrown by tajinder inside " + this.getClass().getName());
//            }
//        });
        //if (mCustomView != null)
		Log.d("LUORAN","addCustomContentToLeft");
        addCustomContentToLeft(mCustomView);
    }


//    public static void saveGA_Youtube(String GA,String youtube){
//        SharedPreferences pref = getSha
//
//    }


    public void addCustomContentToLeft(final View customView) {


        CustomContentCallbacks callbacks = new CustomContentCallbacks() {

            @Override
            public void onScrollProgressChanged(float progress) {
                CommonsUtils.hideKeyboard(LeftyActivity.this);

                if (dialogFragment != null) {
                    dialogFragment.dismiss();
                }

                if (mBannerCountdowntimer != null) {
                    mBannerCountdowntimer.cancel();
                    mBannerCountdowntimer = null;
                }
            }

            @Override
            public boolean isScrollingAllowed() {
                return true;
            }

            @Override
            public void onShow(boolean fromResume) {
            flag=true;
                if (fromResume) {
                    return;
                }
                if (mSearchDropTargetBar != null) {
                    mSearchDropTargetBar.hideSearchBar(false);
               }
                if (CommonsUtils.isConnectedToInternet(LeftyActivity.this)) {

                    if (TextUtils.isEmpty(getAppTypeFromPref())) {

                        if (mGcmCallTask != null && mGcmCallTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
                            return;
                        }

                        mGcmCallTask = new GcmCall(LeftyActivity.this, new GetPublicationsTask.TaskCallbacks() {
                            @Override
                            public void onSucess(String response) {
                                mGcmCallTask = null;
                                if (!isOnCustomContent()) {
                                    return;
                                }

                                if (!TextUtils.isEmpty(response)) {
                                    saveNumOfSwipeInPref();
                                    saveAppTypeInPref(response);
                                    mApplicationType = response;
                                    whichFragment();

                                    if (CommonsUtils.checkPlayServices(LeftyActivity.this)) {

                                        if (isGPSEnabled()) {
                                            buildGoogleApiClient();
                                        } else {
                                            //whichFragment();
                                        }


                                    } else {
//                                        showNativeStaticImage();/
                                        if (isGPSEnabled()) {
                                            buildGoogleApiClient();
                                        } else {
                                            //whichFragment();
                                        }
                                    }

                                } else {
                                    //showNativeStaticImage();
                                }

                            }

                            @Override
                            public void onFailure() {
                                mGcmCallTask = null;
                            }
                        });
                        if (isOnCustomContent()) {
                            TaskHelper.execute(mGcmCallTask);
                        }
                    } else {
                        mApplicationType = getAppTypeFromPref();
                        saveNumOfSwipeInPref();
                        whichFragment();
                        if (CommonsUtils.checkPlayServices(LeftyActivity.this)) {
                            if (isGPSEnabled()) {
                                buildGoogleApiClient();
                            } else {
                                //whichFragment();
                            }


                        } else {
                            //showNativeStaticImage();
                        }
                        if (CommonsUtils.isConnectedToInternet(LeftyActivity.this)) {
                            if (mGcmCallTask != null && mGcmCallTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
                                return;
                            }
                            mGcmCallTask = new GcmCall(LeftyActivity.this, new GetPublicationsTask.TaskCallbacks() {
                                @Override
                                public void onSucess(String response) {
                                    mGcmCallTask = null;
                                    if (!TextUtils.isEmpty(response)) {
                                        saveAppTypeInPref(response);
                                    }
                                }

                                @Override
                                public void onFailure() {
                                    mGcmCallTask = null;
                                }
                            });
                            TaskHelper.execute(mGcmCallTask);
                        }

                    }

                    //throw new IllegalArgumentException("Exception by tajinder");
                } else {
                    showHideFragment(mNativeFragment, false);
                    showHideFragment(mFragmentWeb, false);
                    mSplashImageView.setVisibility(View.GONE);
                    mNativeImageView.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onHide() {
			    flag=false;
                if (mSearchDropTargetBar != null) {
                    mSearchDropTargetBar.showSearchBar(false);
                }
                if (mBannerCountdowntimer != null) {
                    mBannerCountdowntimer.cancel();
                    mBannerCountdowntimer = null;
                }

                if (isOnCustomContent()) {
                    return;
                }
//                if (mGcmCallTask != null && mGcmCallTask.getStatus().equals(AsyncTask.Status.RUNNING)){
//                    mGcmCallTask.cancel(true);
//                    mGcmCallTask = null;
//                }
                if (isWebView()) {
                    mFragmentWeb.destroyWebView();
                }
                showHideFragment(mNativeFragment, false);
                showHideFragment(mFragmentWeb, false);
                mNativeImageView.setVisibility(View.GONE);
                mSplashImageView.setVisibility(View.VISIBLE);
            }
        };


        addToCustomContentPage(customView, callbacks, "custom view");
    }

    void saveNumOfSwipeInPref() {


        int how_many_swipe_done = getSharedPreferences().getInt(CommonsUtils.SHARED_PREF_NO_OF_SWIPE, 1);
        String swipe_frequency = getSharedPreferences().getString(CommonsUtils.SHARED_PREF_ADV_FREQUENCY, "1");


        /**
         * if swipe_frequency is 0 mean we don't want to show any advertisment
         */
        if (Integer.parseInt(swipe_frequency) == 0) {
            SharedPreferences.Editor editor = getSharedPreferences().edit();
            editor.putInt(CommonsUtils.SHARED_PREF_NO_OF_SWIPE, 1);
            editor.commit();
            return;
        }

        if (Integer.parseInt(swipe_frequency) == how_many_swipe_done || how_many_swipe_done > Integer.parseInt(swipe_frequency)) {

            /**
             * if we do not have any data of lefy or getdata api isn't hit yet then we simply return with increasing
             * no of swipe counter
             */
            String response = getDataFromPref();
            if (TextUtils.isEmpty(response)) {
                SharedPreferences.Editor editor = getSharedPreferences().edit();
                editor.putInt(CommonsUtils.SHARED_PREF_NO_OF_SWIPE, ++how_many_swipe_done);
                editor.commit();
                return;
            }

            showDialogForAdvertisment();
            SharedPreferences.Editor editor = getSharedPreferences().edit();
            editor.putInt(CommonsUtils.SHARED_PREF_NO_OF_SWIPE, 1);
            editor.commit();
        } else {


            SharedPreferences.Editor editor = getSharedPreferences().edit();
            editor.putInt(CommonsUtils.SHARED_PREF_NO_OF_SWIPE, ++how_many_swipe_done);
            editor.commit();
        }

    }

    String getDataFromPref() {
        return getSharedPreferences().getString("response", null);
    }


    /**
     * countdown to show Advertisment Dialog
     */
    private void showDialogForAdvertisment() {

        String showTime = getSharedPreferences().getString(CommonsUtils.SHARED_PREF_ADV_SHOW_TIME, "10");

        mBannerCountdowntimer = new CountDownTimer(Integer.parseInt(showTime) * 100, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                String image = getSharedPreferences().getString(CommonsUtils.SHARED_PREF_ADV_IMAGE, "");
                String redirect_link = getSharedPreferences().getString(CommonsUtils.SHARED_PREF_ADV_REDIRECT_URL, "");
                dialogFragment = AdvertismentDialogFragment.newInstance(image, redirect_link);
                dialogFragment.show(getFragmentManager(), AdvertismentDialogFragment.class.getName());
            }
        }.start();


    }


    void showNativeStaticImage() {
        showHideFragment(mNativeFragment, false);
        showHideFragment(mFragmentWeb, false);
        mNativeImageView.setVisibility(View.VISIBLE);
        mSplashImageView.setVisibility(View.GONE);

    }

    void whichFragment() {
        // mApplicationType = "0";
        if (mApplicationType.equalsIgnoreCase("0")) {
            showHideFragment(mNativeFragment, true);
            showHideFragment(mFragmentWeb, false);


        } else if (mApplicationType.equalsIgnoreCase("1")) {
            showHideFragment(mNativeFragment, false);
            showHideFragment(mFragmentWeb, true);
        }
        if (isOnCustomContent()) {
            refreshScreen();
        }

    }

    boolean isWebView() {
        if (mApplicationType.equalsIgnoreCase("1")) {
            return true;
        }
        return false;
    }
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mGoogleApiClient.isConnected()) {
                        mGoogleApiClient.reconnect();
                    }
                } else {

                    if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                        mGoogleApiClient.disconnect();
                        mGoogleApiClient = null;
                        //stopLocationUpdates();
                    }
                    //whichFragment();
                }
                return;
            }
        }
    }

*/
    @Override
    public QSBScroller addToCustomContentPage(View customContent, CustomContentCallbacks callbacks, String description) {
        super.addToCustomContentPage(customContent, callbacks, description);
        mNativeFragment = (Lefty_MainActivity) getFragmentManager().findFragmentById(R.id.fragment_id_native);
        mFragmentWeb = (WebViewFragment) getFragmentManager().findFragmentById(R.id.fragment_id_web);

        showHideFragment(mNativeFragment, false);
        showHideFragment(mFragmentWeb, false);

        mSplashImageView = (ImageView) mCustomView.findViewById(R.id.splash_imageview);
        mNativeImageView = (ImageView) mCustomView.findViewById(R.id.native_imageview);
        ImageLoader.getInstance().displayImage("drawable://" + R.drawable.native_screen, mNativeImageView);
        ImageLoader.getInstance().displayImage("drawable://" + R.drawable.splash, mSplashImageView);
        return mQsbScroller;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        Log.e("new intent","")
//        String intentExtra = intent.getStringExtra("from");
//        if (!TextUtils.isEmpty(intentExtra) && intentExtra.equalsIgnoreCase("N")){
//            Toast.makeText(LeftyActivity.this, "You welcome", Toast.LENGTH_SHORT).show();
//            showWorkspace(0,);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //ViewServer.get(this).setFocusedWindow(this);
		 if(flag){
            if (mSearchDropTargetBar != null) {
                mSearchDropTargetBar.hideSearchBar(false);
            }
        }
    }

    void saveAddressInPref(String address) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(CommonsUtils.SHARED_PREF_ADDRESS_KEY, address);
        editor.commit();
    }

    void saveAppTypeInPref(String appType) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(CommonsUtils.SHARED_PREF_APP_TYPE_KEY, appType);
        editor.commit();
    }

    String getAddressFromPref() {
        return getSharedPreferences().getString(CommonsUtils.SHARED_PREF_ADDRESS_KEY, null);
    }

    String getAppTypeFromPref() {
        return getSharedPreferences().getString(CommonsUtils.SHARED_PREF_APP_TYPE_KEY, null);
    }

    void callGetDataApi(String address) {
        if (mTask != null && mTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            return;
        }
        if (CommonsUtils.isConnectedToInternet(this)) {
            mTask = new ApiTask();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                mTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, address);
            } else {
                mTask.execute(address);
            }
        }
    }

    void refreshWebView() {
        if (!CommonsUtils.isConnectedToInternet(this)) {
            return;
        }

        if (isOnCustomContent()) {
            mFragmentWeb.loadWebView();
        }

    }

    @Override
    protected void invalidateHasCustomContentToLeft() {
        super.invalidateHasCustomContentToLeft();
    }

    @Override
    public void refreshActivity() {
        invalidateHasCustomContentToLeft();
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
            String adress = "";
//            if (mLastLocation != null) {
//                adress = CommonsUtils.getLocationAddress(LeftyActivity.this, mLastLocation.getLatitude(), mLastLocation.getLongitude());
//                if (!TextUtils.isEmpty(adress)) {
//                    saveAddressInPref(adress);
//                } else {
//                    adress = getAddressFromPref();
//                }
//            } else {
//                adress = getAddressFromPref();
//            }
            adress = getAddressFromPref();

            if (!TextUtils.isEmpty(adress)) {
                address = adress;
            }
            deviceId = CommonsUtils.getSecureDeviceId(LeftyActivity.this);
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
                    Example example = gson.fromJson(response, Example.class);
                    saveAdvDataInPref(example.getData().getAdvbanner());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mTask = null;

            if (!TextUtils.isEmpty(response) && CommonsUtils.isJSONValid(response)) {
                saveDataInPref(response);
                if (isOnCustomContent()) {
                    mNativeFragment.refreshFragment();

                }
            }


        }

    }

    private void saveAdvDataInPref(List<Example.AdvBanner> banner1) {

        if (banner1 == null || banner1.size() == 0) {
            SharedPreferences.Editor editor = getSharedPreferences().edit();
            editor.putString(CommonsUtils.SHARED_PREF_ADV_BANNER_ID, "");
            editor.commit();

            SharedPreferences.Editor editor2 = getSharedPreferences().edit();
            editor2.putString(CommonsUtils.SHARED_PREF_ADV_FREQUENCY, "0");
            editor2.commit();

            editor.putString(CommonsUtils.SHARED_PREF_ADV_REDIRECT_URL, "");
            // editor.commit();

            editor.putString(CommonsUtils.SHARED_PREF_ADV_IMAGE, "");
            // editor.commit();

            editor.putString(CommonsUtils.SHARED_PREF_ADV_URL, "");
            // editor.commit();

            editor.putString(CommonsUtils.SHARED_PREF_ADV_SHOW_TIME, "");
            editor.commit();
            return;
        }

        Example.AdvBanner banner = banner1.get(0);
        // String bannerId = getSharedPreferences().getString(CommonsUtils.SHARED_PREF_ADV_BANNER_ID, "");

        // if (!bannerId.equalsIgnoreCase(banner.getId())) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(CommonsUtils.SHARED_PREF_ADV_BANNER_ID, banner.getId());
        editor.commit();

        SharedPreferences.Editor editor2 = getSharedPreferences().edit();
        editor2.putString(CommonsUtils.SHARED_PREF_ADV_FREQUENCY, banner.getFrequency());
        editor2.commit();

        editor.putString(CommonsUtils.SHARED_PREF_ADV_REDIRECT_URL, banner.getRedirect_link());
        // editor.commit();

        editor.putString(CommonsUtils.SHARED_PREF_ADV_IMAGE, banner.getBanner_image());
        // editor.commit();

        editor.putString(CommonsUtils.SHARED_PREF_ADV_URL, banner.getUrl());
        // editor.commit();

        editor.putString(CommonsUtils.SHARED_PREF_ADV_SHOW_TIME, banner.getDelay());
        editor.commit();

        // }
    }



    public void saveDataInPref(String response) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(CommonsUtils.SHARED_PREF_API_RESPONSE_KEY, "" + response);
        editor.commit();
    }


    boolean isGPSEnabled() {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return enabled;
    }


    protected synchronized void buildGoogleApiClient() {
        if (mGoogleApiClient != null) {
            return;
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
//                        if (!checkLocationPermission()) {
//                            return;
//                        }
                        startLocationUpdates();

//                                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//                        Toast.makeText(LeftyActivity.this, "Location = " + mLastLocation.getLatitude(), Toast.LENGTH_SHORT).show();
//                        if (mLastLocation != null) {
//                            //updateLocation(mLastLocation);
//                            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
//                                mGoogleApiClient.disconnect();
//                                mGoogleApiClient = null;
//                                //stopLocationUpdates();
//                            }
//                        }
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        // Log.e("Location Services", "Connection suspended=" + i);
                    }

                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        //Log.e("Location Services", "Connection failed" + connectionResult.getErrorCode());
                    }
                })
                .addApi(LocationServices.API)
                .build();


        connectGoogleApiClient();
    }

    void startLocationUpdates() {
        //if (!checkLocationPermission()) {
        //    return;
        // }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                mLocationRequest,
                mLocationListener
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                mRequestingLocationUpdates = true;
            }
        });
    }

    GeocoderTask mGeocoderTask = null;

    private LocationListener mLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            if (mGoogleApiClient.isConnected()) {

                stopLocationUpdates();
                if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.disconnect();
                    mGoogleApiClient = null;
                    //stopLocationUpdates();
                }
                mLastLocation = location;
                if (mGeocoderTask != null && mGeocoderTask.getStatus().equals(AsyncTask.Status.RUNNING)) {

                } else {
                    mGeocoderTask = new GeocoderTask();
                    TaskHelper.execute(mGeocoderTask);
                }
               /* String address = CommonsUtils.getLocationAddress(LeftyActivity.this, location.getLatitude(), location.getLongitude());
                Log.e("Local Geocoder", "" + address);
                if (!TextUtils.isEmpty(address)) {
                    saveAddressInPref(address);

                } else {
                    address = getAddressFromPref();

//                    if (mGeocoderTask == null) {
//                        if (CommonsUtils.isConnectedToInternet(LeftyActivity.this)) {
//                            mGeocoderTask = new GeocoderTask();
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                                mGeocoderTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "" + location.getLatitude(), "" + location.getLongitude());
//                            } else {
//                                mGeocoderTask.execute("" + location.getLatitude(), "" + location.getLongitude());
//                            }
//                        }
//
//                    }


                }*/
                //refreshScreen();
                //whichFragment();
                //
            }

        }
    };


    void refreshScreen() {
        if (mApplicationType.equalsIgnoreCase("0")) {
            callGetDataApi(null);
        } else {
//            if (mWebTask != null && mWebTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
//                return;
//            }
//            mWebTask = new RefreshWebViewAsync();
//            TaskHelper.execute(mWebTask);

            refreshWebView();


        }
    }


    //RefreshWebViewAsync mWebTask;

    /*public class RefreshWebViewAsync extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
//            String address = "";

//            if (mLastLocation != null) {
//                address = CommonsUtils.getLocationAddress(LeftyActivity.this, mLastLocation.getLatitude(), mLastLocation.getLongitude());
//                // Log.e("Local Geocoder", "" + address);
//                if (!TextUtils.isEmpty(address)) {
//                    saveAddressInPref(address);
//                }
//            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mWebTask = null;
            refreshWebView();
        }
    }*/


    public String getLanguageFromPref() {
        return getSharedPreferences().getString("language", "");
    }

    protected Boolean mRequestingLocationUpdates;
    public GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 20000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    public static Tracker mTracker;

    /**
     * Gets the default {@link Tracker} for this .
     *
     * @return tracker
     */
    synchronized public static Tracker getDefaultTracker(Context context) {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(CommonsUtils.TRACKING_ID);
        }
        return mTracker;
    }


//    public static void sendScreenName(String screenName, Activity context) {
//        try {
//            Tracker t = ((AucklandApplication) context.getApplication()).getTracker();
//            t.setScreenName(screenName);
//            t.send(new HitBuilders.AppViewBuilder().build());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * @param actionName
     * @param screenName
     * @param label
     */
    public static void sendEvent(String actionName, String screenName, String label) {
        try {

            HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder();
            eventBuilder.setCategory(screenName);
            eventBuilder.setAction(actionName);
            if (label != null) {
                eventBuilder.setLabel(label);
            }

            mTracker.send(eventBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient,
                mLocationListener
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                mRequestingLocationUpdates = false;
            }
        });
    }

    public void connectGoogleApiClient() {
        if (mGoogleApiClient != null && !mGoogleApiClient.isConnecting()) {
            mGoogleApiClient.connect();
        }

    }
/*
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    0);
            return false;

        }
        return true;
    }
*/
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    void showHideFragment(Fragment fragment, boolean showHide) {

        final Activity activity = wrActivity.get();
        if (activity != null && !activity.isFinishing()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (!activity.isDestroyed()) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    if (showHide) {
                        ft.show(fragment);
                    } else {
                        ft.hide(fragment);
                    }
                    ft.commitAllowingStateLoss();
                }
            } else {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if (showHide) {
                    ft.show(fragment);
                } else {
                    ft.hide(fragment);
                }
                ft.commitAllowingStateLoss();
            }
        }

    }

    public class GeocoderTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String adress;
            if (mLastLocation != null) {
                adress = CommonsUtils.getLocationAddress(LeftyActivity.this, mLastLocation.getLatitude(), mLastLocation.getLongitude());
                if (!TextUtils.isEmpty(adress)) {
                    saveAddressInPref(adress);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mGeocoderTask = null;
        }
    }

}
