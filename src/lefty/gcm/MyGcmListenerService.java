/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lefty.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.android.launcher3.R;
import com.google.android.gms.gcm.GcmListenerService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyGcmListenerService extends GcmListenerService {
    private static final String TAG = "MyGcmListenerService";
    String message = "";
    String title = "";
    String url = "";
    String banner = "";
    String tag = "";
    String icon = "";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data VersionData bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        /**
         * {title=Tajinder, message=Singh, collapse_key=do_not_collapse}
         */

        try {
            message = data.getString("message");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            title = data.getString("title");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            url = data.getString("url");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            banner = data.getString("banner");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            tag = data.getString("tag");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            icon = data.getString("icon");
        } catch (Exception e) {
            e.printStackTrace();
        }


        /**
         * Async task to download icon and banner for notification
         */
        new GeneratePictureStyleNotification().execute(icon, banner);

        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String title, String message, String url, List<Bitmap> result) {
        Uri mUri = null;
        try {
            mUri = Uri.parse(url);
        } catch (Exception e) {

        }


        Intent intent = new Intent(Intent.ACTION_VIEW, mUri);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        int icon1 = R.mipmap.noti_icon;
        Bitmap mIconBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.intex_icon);
        if (TextUtils.isEmpty(icon)) {
            mIconBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.intex_icon);
        } else {
            if (result != null && result.size() > 0) {
                mIconBitmap = result.get(0);
                if(mIconBitmap==null){
                    mIconBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.intex_icon);
                }
            }
        }

        Bitmap mBannerBitmap = null;
        if (TextUtils.isEmpty(banner)) {
            mBannerBitmap = null;
        } else {
            if (result != null && result.size() > 0) {
                mBannerBitmap = result.get(1);
            }
        }


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(icon1)
                .setLargeIcon(mIconBitmap)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setColor(Color.parseColor("#8f0002"))
                .setContentIntent(pendingIntent);

        if (mBannerBitmap != null) {
            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(mBannerBitmap).setSummaryText(message));
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    void openBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }


    /**
     * this task will get bitmap of banner and icon
     */
    public class GeneratePictureStyleNotification extends AsyncTask<String, Void, List<Bitmap>> {


        @Override
        protected List<Bitmap> doInBackground(String... params) {

            List<Bitmap> bitmaps = new ArrayList<>();

            String icon = params[0];
            String banner = params[1];

            InputStream in;
            URL url;
            HttpURLConnection connection;
            try {
                url = new URL(icon);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                bitmaps.add(BitmapFactory.decodeStream(in));
                in.close();
                connection.disconnect();
            } catch (MalformedURLException e) {
                bitmaps.add(null);
                e.printStackTrace();
            } catch (IOException e) {
                bitmaps.add(null);
                e.printStackTrace();
            }
            try {
                url = new URL(banner);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                in = connection.getInputStream();
                bitmaps.add(BitmapFactory.decodeStream(in));
                in.close();
                connection.disconnect();

            } catch (MalformedURLException e) {
                bitmaps.add(null);
                e.printStackTrace();
            } catch (IOException e) {
                bitmaps.add(null);
                e.printStackTrace();
            }
            return bitmaps;
        }

        @Override
        protected void onPostExecute(List<Bitmap> result) {
            super.onPostExecute(result);
            sendNotification(title, message, url, result);
        }
    }
}
