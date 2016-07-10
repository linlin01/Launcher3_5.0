package lefty;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by tajinder on 16/6/16.
 */
public class SaveSettingsTask extends AsyncTask<Void, Void, String> {

    String mLanguages = "";
    Context mContext;

    GetPublicationsTask.TaskCallbacks mTaskCallbacks = null;
    HashMap<String, String> mMap;

    public SaveSettingsTask(Context context, String languages, String languagesCode, String publications, GetPublicationsTask.TaskCallbacks callbacks) {
        mContext = context;
        mLanguages = languages;
        mTaskCallbacks = callbacks;
        mMap = new HashMap<>();
        mMap.put("language_names", languages);
        mMap.put("language_codes", languagesCode);
        mMap.put("publisher_names", publications);
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        CommonsUtils.dismissProgress();
        if (!TextUtils.isEmpty(s)) {
            mTaskCallbacks.onSucess(s);
        } else {
            mTaskCallbacks.onFailure();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        CommonsUtils.showProgress(mContext, "Please wait..");
    }

    @Override
    protected String doInBackground(Void... params) {

        String response = "";

        try {
            response = CommonsUtils.requestWebService(CommonsUtils.SAVE_SETTINGS, mMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("publication response", "" + response);


        return response;
    }
}
