package lefty;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.BuildConfig;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.tweetui.TweetUi;

import io.fabric.sdk.android.DefaultLogger;
import io.fabric.sdk.android.Fabric;

/**
 * Created by tajinder on 25/6/16.
 */
public class LeftyApplication extends Application {


    static LeftyActivity mCActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
// Set up Crashlytics, disabled for debug builds
        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();

        TwitterAuthConfig authConfig = new TwitterAuthConfig(CommonsUtils.TWITTER_KEY, CommonsUtils.TWITTER_SECRET);
        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Twitter(authConfig),new Crashlytics())

                .logger(new DefaultLogger(Log.DEBUG))
                .debuggable(true)
                .build();

        Fabric.with(fabric);
        Fabric.with(this, new Twitter(authConfig));
        Fabric.with(this, new TwitterCore(authConfig), new TweetUi());

    }

    public static void setmCActivity(LeftyActivity mCActivity) {
        mCActivity = mCActivity;
    }

    public static LeftyActivity getmCActivity() {
        return mCActivity;
    }

    public static void initImageLoader(Context context) {
        /**
         * This configuration tuning is custom. You can tune every option, you
         * may tune some of them, or you can create default configuration by
         * ImageLoaderConfiguration.createDefault(this); method.
         **/
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .diskCacheExtraOptions(480, 320, null)
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
}



