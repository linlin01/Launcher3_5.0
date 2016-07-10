package lefty.ApiCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.HashMap;

import lefty.CommonsUtils;
import lefty.GetPublicationsTask;
import lefty.models.VersionData;

/**
 * Created by tajinder on 20/6/16.
 */
public class GetVersionApi extends AsyncTask<Void, Void, Boolean> {


    Context mContext;
    GetPublicationsTask.TaskCallbacks mCallbacks;


    public GetVersionApi(Context context, GetPublicationsTask.TaskCallbacks callbacks) {
        mContext = context;
        mCallbacks = callbacks;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        String response = "";
        String device_id = "";
        String device_token = "";
        try {
            device_id = CommonsUtils.getSecureDeviceId(mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        device_token = mContext.getSharedPreferences(CommonsUtils.SHARED_PREF_NAME, mContext.MODE_PRIVATE).getString(CommonsUtils.SHARED_PREF_TOKEN_KEY, "");


        HashMap<String, String> mMap = new HashMap<>();
        mMap.put("device_id", device_id);
        mMap.put("device_token", device_token);

        try {
            response = CommonsUtils.requestWebService(CommonsUtils.GET_VERSION, mMap);

            if (!TextUtils.isEmpty(response) && CommonsUtils.isJSONValid(response)){
                Gson gson = new Gson();
                VersionData vData = gson.fromJson(response, VersionData.class);
                if (vData != null){
                    if(vData.getSuccess()){
                        String versionNo = vData.getData().getApplicationType();

                        mCallbacks.onSucess(versionNo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected Boolean doInBackground(Void... params) {


        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
