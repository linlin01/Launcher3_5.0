package lefty.webundle;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;
import java.util.Locale;

import lefty.Connectivity;

/**
 * Created by amit on 8/3/16.
 */

/*This class will contain all the common/static methods*/

public class CommonUtils {
    public static final String DEVELOPER_KEY = "AIzaSyC5L2osgrIiH6zc51QCNiciiBwnjS40iLw";
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

        if (Connectivity.isConnectedWifi(context)){
            return "wifi";
        }else {


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

    public static String gettingDeviceId(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }


    public static String getSecureDeviceId(Context context){
        String deviceId = "";
         try{
             deviceId = Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
         }catch (Exception e){
             e.printStackTrace();
         }
        return deviceId;
    }

    /**
     * this will date in yyyy-MM-dd date format
     *
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @return
     */
    public static String formatDate(int year, int monthOfYear, int dayOfMonth) {
        String month, day;
        if (monthOfYear < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = "" + (monthOfYear + 1);
        }
        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = "" + dayOfMonth;
        }

        return year + "-" + month + "-" + day;
    }
    /* Titles used to dislplay in activity_view_3  under names*/
    public static final String[] sDescriptions = new String[] {
            "Sold by E-Mobiles",
            "Sold by cloudtail", "Sold by INDIA RETAIL",
            "Sold by E-Mobiles" };




    /* Titles used to dislplay in activity_view_6  under images*/
    public static final String[] sFoodNames = new String[] { "Pan Base Puf Pizza",
            "Frozen Yogurt at Yumz", "Farm fresh granola Cake" };


    /* Titles used to dislplay in activity_view_6  under names*/
    public static final String[] sFoodDescriptions = new String[] {
            "At Olive Theory,diners aren't..",
            "Oatmeat cookie,Red velvet..", "Ketchuop is good on your..",
             };


    public static DisplayImageOptions getDisplayOptions(Context context,int durationMillis){
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .resetViewBeforeLoading(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .displayer(new FadeInBitmapDisplayer(durationMillis))
                .build();
    }

    public static String getLocationAddress(Context context,double latitude,double longitude){

        Geocoder geocoder = null;
        List<Address> addresses=null;
        try{
            geocoder = new Geocoder(context, Locale.getDefault());
        }catch (Exception e){
            e.printStackTrace();
        }
        if (geocoder== null){
            return "";
        }
        String address="";
        String city="";
        String state="" ;
        String country ="" ;
        String postalCode = "";
        String knownName = ""; // Only if available else return NULL
        try {

            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            postalCode = addresses.get(0).getPostalCode();
            knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

        }catch (Exception e){

        }
        //Log.e("city"+"-----"+"state",""+state+"/"+country);
        return city+"/"+state;

    }
    public static ProgressDialog mDialog;

    public static void showProgress(String title,Context context) {
        try {
            if (mDialog == null){
                mDialog = ProgressDialog.show(context, "", title, false, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissProgress() {
        try {
            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
