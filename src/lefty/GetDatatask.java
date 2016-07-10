//package lefty;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.google.gson.Gson;
//
//import lefty.models.Example;
//
///**
// * Created by tajinder on 16/6/16.
// */
//public class GetDatatask extends AsyncTask<Void,Void,String> {
//    String deviceId = "";
//    String response = "";
//
//    GetDatatask(Context mContext)
//
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }
//
//    @Override
//    protected String doInBackground(Void... params) {
//
//        String address = "No location/No location";
//        if (!TextUtils.isEmpty(params[0])) {
//            address = params[0];
//        }
//        deviceId = CommonsUtils.getSecureDeviceId(LeftyActivity.this);
//        String url = CommonsUtils.GET_DATA + deviceId +"/"+ address;
//        try {
//            response = CommonsUtils.requestWebService(url);
//            if (!TextUtils.isEmpty(response)) {
//                Gson gson = new Gson();
//                gson.fromJson(response, Example.class);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        Log.e("Response", "" + response);
//        return null;
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//    }
//}
