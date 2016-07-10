package lefty;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.android.launcher3.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


/**
 * Created by tajinder on 16/6/16.
 */
public class PlayYoutubeActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener, YouTubePlayer.OnFullscreenListener, YouTubePlayer.PlayerStateChangeListener {

    YouTubePlayerView youTubeView;
    YouTubePlayer mYouTubePlayer;
    String id = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_youtube);

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        gettingExtras();

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(CommonsUtils.DEVELOPER_KEY, this);
    }


    private void gettingExtras() {

        id = getIntent().getStringExtra("id");
        Log.e("id", id);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!TextUtils.isEmpty(id)) {
            mYouTubePlayer = youTubePlayer;
            mYouTubePlayer.setOnFullscreenListener(this);
            mYouTubePlayer.setPlayerStateChangeListener(this);
            //mYouTubePlayer.setFullscreen(true);
            youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
            if (!wasRestored) {
                youTubePlayer.cueVideo(id);

            }
        }


    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }


    @Override
    public void onFullscreen(boolean b) {
        Log.e("onfullscreem", "" + b);

//        if (b) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
////            mYouTubePlayer.play();
//        } else {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
////            mYouTubePlayer.play();
//        }
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onLoaded(String s) {
        mYouTubePlayer.play();
    }

    @Override
    public void onAdStarted() {
    }

    @Override
    public void onVideoStarted() {
    }

    @Override
    public void onVideoEnded() {
    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {
        Log.e("onvideo", "ended");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == getResources().getConfiguration().ORIENTATION_PORTRAIT) {
            mYouTubePlayer.setFullscreen(false);
//            mYouTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        } else {
//            Intent intent = YouTubeStandalonePlayer.createVideoIntent(
//                    this, CommaonsUtils.DEVELOPER_KEY, id, 0, true, false);
//            startActivity(intent);
            mYouTubePlayer.setFullscreen(true);
//            mYouTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
        }


    }

}