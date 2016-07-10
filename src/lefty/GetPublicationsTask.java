package lefty;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by tajinder on 16/6/16.
 */
public class GetPublicationsTask extends AsyncTask<Void, Void, String> {

    String mLanguages = "";
    Context mContext;

    TaskCallbacks mTaskCallbacks = null;
    HashMap<String, String> mMap;

    public GetPublicationsTask(Context context, String languages, TaskCallbacks callbacks) {
        mContext = context;
        mLanguages = languages;
        mTaskCallbacks = callbacks;
        mMap = new HashMap<>();
        mMap.put("lang", languages);
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
        CommonsUtils.showProgress(mContext, "Loading publishers..");
    }

    @Override
    protected String doInBackground(Void... params) {

        String response = "";

        try {
            response = CommonsUtils.requestWebService(CommonsUtils.GET_PUBLICATIONS, mMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public interface TaskCallbacks {
        void onSucess(String response);
        void onFailure();
    }
}
