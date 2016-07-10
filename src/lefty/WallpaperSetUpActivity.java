package lefty;

import android.app.ActionBar;
import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.launcher3.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by tajinder on 11/5/16.
 */

public class WallpaperSetUpActivity extends Activity {

    String url = "";
    ImageView mImageView;

    Button mButton;

    ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lefty_activity_wallpaper_setup);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) actionBar.hide();

        url = getIntent().getStringExtra("url");
        mButton = (Button) findViewById(R.id.set_wallpaper_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmaptwo == null) {
                    return;
                }
                SetTask mTask = new SetTask();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    mTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    mTask.execute();
                }

            }
        });
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);


        if (CommonsUtils.isConnectedToInternet(this)) {
            mBackgroundTask = new BackgroundTask();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                mBackgroundTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
            } else {
                mBackgroundTask.execute(url);
            }
        }

        mImageView = (ImageView) findViewById(R.id.wall_imageview);


    }

    BackgroundTask mBackgroundTask = null;

    public Bitmap bitmaptwo = null;

    public class BackgroundTask extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);

        }

        protected Bitmap doInBackground(String... url) {
            //--- download an image ---
            Bitmap bitmap = null;
            try {
               bitmap = DownloadImage(url[0]);
           }catch (Exception e){
               e.printStackTrace();
           }
            return bitmap;
        }

        protected void onPostExecute(Bitmap bitmap) {
            bitmaptwo = bitmap;
            mImageView.setImageBitmap(bitmaptwo);
            mProgressBar.setVisibility(View.GONE);


        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public class SetTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            CommonsUtils.showProgress(WallpaperSetUpActivity.this,"Please wait..");

        }

        @Override
        protected Void doInBackground(Void... params) {
            WallpaperManager wManager = WallpaperManager.getInstance(WallpaperSetUpActivity.this);
            Bitmap scaledBitmap;
//            switch ()

            try {
                if (bitmaptwo != null){
                    int screeenWidth = Lefty_MainActivity.getScreenWidth(WallpaperSetUpActivity.this);
                    if (screeenWidth == 480){
                        scaledBitmap = Bitmap.createScaledBitmap(bitmaptwo,screeenWidth,800,false);
                        wManager.setBitmap(scaledBitmap);
                    }else{
                        wManager.setBitmap(bitmaptwo);
                    }


                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CommonsUtils.dismissProgress();
            finish();
        }
    }



    private InputStream OpenHttpConnection(String urlString) throws IOException {
        InputStream in = null;
        int response = -1;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        } catch (Exception ex) {
            throw new IOException("Error connecting");
        }
        return in;
    }

    private Bitmap DownloadImage(String URL) {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e1) {
//            Toast.makeText(this, e1.getLocalizedMessage(),
//                    Toast.LENGTH_LONG).show();

            e1.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBackgroundTask != null && mBackgroundTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            mBackgroundTask.cancel(true);
        }

        if (bitmaptwo != null){
            bitmaptwo.recycle();
            bitmaptwo = null;
        }

    }
}


