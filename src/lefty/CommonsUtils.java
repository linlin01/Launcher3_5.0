package lefty;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by tajinder on 15/6/16.
 */
public class CommonsUtils {


    /*public static final String TWITTER_KEY = "f1s6Ryd4dlUva43K1qWi6FhSY";
    public static final String TWITTER_SECRET = "zAiRxdD8iEUvY7Ogk0t3oi84HO8YEc2qv1E34SE3PCzSRfxzPF";*/

    public static final String TWITTER_KEY = "Iz8Sq16rVfW3WCa12v0JugoM6";
    public static final String TWITTER_SECRET = "oWbfzrNoan8GS0x1atFcdmTCnHpHft0elsk1fQ3JjJ50RFje5q";

    public static final String WALLPAPER_TITLE = "Wallpapers";
    public static final String VIDEOS_TITLE = "Videos";
    public static final String NEWS_TITLE = "News";
    public static final String TWITTER_TITLE = "Twitter";
    public static final String TRENDING_APPS_TITLE = "Trending Apps";
    public static final String TRENDING_GAMES_TITLE = "Trending Games";
    public static final String ENTERTAINMENT_TITLE = "Entertainment";
    public static final String TWITTER_TRENDING_NEWS_TITLE = "Trending News";


    public static final String BASE_URL = "http://lefty.mobi/ApiController/";
    public static final String GET_DATA = BASE_URL + "get_data/";

    public static final String GET_PUBLICATIONS = BASE_URL + "get_publication";
    public static final String SAVE_SETTINGS = BASE_URL + "save_settings";
    public static final String GET_VERSION = BASE_URL + "getVersionList";

    public static final String NO_LOCATION = "No%20location/No%20location";
    //lang=english-hindi-sonon
    //public static final String YAHOO_SEARCH_URL = "https://in.search.yahoo.com/yhs/mobile/search?hspart=testing&hsimp=yhsm-testing_001&p=";

    //public static final String DEVELOPER_KEY = "AIzaSyC5L2osgrIiH6zc51QCNiciiBwnjS40iLw";
    public static final String DEVELOPER_KEY = "AIzaSyAHvgkLZ1txoSv-vwQvYxeip5wGgbU_m50";
    //public static final String GCM_SENDER_KEY = "967880727475";
    public static final String GCM_SENDER_KEY = "576407032788";
    //public static final String TRACKING_ID = "UA-77683556-1";
    public static final String TRACKING_ID = "UA-79995923-1";

    public static final String WEB_SCREEN_NAME = "WebView";
    public static final String NATIVE_SCREEN_NAME = "Native";
    public static final String YOUTUBE_EVENT_NAME = "Youtube";
    public static final String TOI_EVENT_NAME = "TOI NEWS";
    public static final String WALLPAPER_EVENT_NAME = "Wallpaper";
    public static final String TOP_BANNER_EVENT_NAME = "TopBanner";
    public static final String BOTTOM_BANNER_EVENT_NAME = "BottomBanner";
    public static final String TRENDING_APPS_EVENT_NAME = "TrendingApps";
    public static final String TRENDING_GAMES_EVENT_NAME = "TrendingGames";
    public static final String ENTERTAINMENT_EVENT_NAME = "Entertainment";
    public static final String YAHOO_EVENT_NAME = "YahooSearch";


    public static final String SHARED_PREF_NAME = "Lefty";
    public static final String SHARED_PREF_TOKEN_KEY = "token";
    public static final String SHARED_PREF_VERSION_KEY = "version";
    public static final String SHARED_PREF_ADDRESS_KEY = "address";
    public static final String SHARED_PREF_LANGUAGE_KEY = "version";
    public static final String SHARED_PREF_API_RESPONSE_KEY = "response";
    public static final String SHARED_PREF_APP_TYPE_KEY = "app_type";


    public static String getLocationAddress(Context context, double latitude, double longitude) {

        Geocoder geocoder = null;
        List<Address> addresses = null;
        try {
            geocoder = new Geocoder(context, Locale.getDefault());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (geocoder == null) {
            return "";
        }
        String address = "";
        String city = "";
        String state = "";
        String country = "";
        String postalCode = "";
        String knownName = ""; // Only if available else return NULL
        try {

            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            //address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
           /* country = addresses.get(0).getCountryName();
            postalCode = addresses.get(0).getPostalCode();
            knownName = addresses.get(0).getFeatureName(); */// Only if available else return NULL

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(city) || TextUtils.isEmpty(state)) {
            return "";
        }
//        String encodedCity ;
//        try {
//            encodedCity = URLEncoder.encode(city,"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            encodedCity = city;
//        }
//        String encodedState;
//        try {
//            encodedState = URLEncoder.encode(state,"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            encodedState = state;
//        }

        return city.replace(" ", "%20") + "/" + state.replace(" ", "%20");

    }


    public static boolean isConnectedToInternet(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String getNetworkClass(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();

        if (Connectivity.isConnectedWifi(context)) {
            return "wifi";
        } else {


            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return "2G";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return "4G";
                default:
                    return "Unknown";
            }
        }
    }


    public static String requestWebService(String serviceUrl) {
        disableConnectionReuseIfNecessary();

        HttpURLConnection urlConnection = null;
        try {
            // create connection
            URL urlToRequest = new URL(serviceUrl);
            urlConnection = (HttpURLConnection) urlToRequest.openConnection();
//            urlConnection.setReadTimeout(3000);
            urlConnection.setConnectTimeout(15000);

            // handle issues
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                // handle unauthorized (if service requires user login)
            } else if (statusCode != HttpURLConnection.HTTP_OK) {
                // handle any other errors, like 404, 500,..
            }

            // create JSON object from content
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return getResponseText(in);

        } catch (MalformedURLException e) {
            // URL is invalid
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            // data retrieval or connection timed out
            e.printStackTrace();
        } catch (IOException e) {
            // could not read response body
            // (could not create input stream)
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
    }

    /**
     * required in order to prevent issues in earlier Android version.
     */
    private static void disableConnectionReuseIfNecessary() {
        // see HttpURLConnection API doc
        if (Integer.parseInt(Build.VERSION.SDK)
                < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    private static String getResponseText(InputStream inStream) {
        return new Scanner(inStream).useDelimiter("\\A").next();
    }


    public static String getSecureDeviceId(Context context) {
        String deviceId = "";
        try {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }

    public static String requestWebService(String requestURL, HashMap<String, String> postDataParams) {

        String response = "";
        URL url;

        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("ClientVersion", "" + 1);
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    static ProgressDialog mDialog;

    public static void showProgress(Context mContext, String title) {
        if (mDialog == null) {
            mDialog = ProgressDialog.show(mContext, "", title, true);
            mDialog.setCancelable(false);
        }


    }

    public static void dismissProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public static boolean checkPlayServices(Activity mContext) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(mContext);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
//                apiAvailability.getErrorDialog(mContext, resultCode, 100)
//                        .show();
            } else {
            }
            return false;
        }
        return true;
    }

    public static void hideKeyboard(Activity context) {
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
}
