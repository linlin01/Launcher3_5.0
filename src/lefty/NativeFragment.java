//package lefty;
//
//import android.app.Fragment;
//import android.content.Context;
//import android.os.Bundle;
//import android.os.Parcelable;
//import android.support.annotation.Nullable;
//import android.support.v4.view.PagerAdapter;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//
//import com.android.launcher3.R;
//import com.google.android.youtube.player.YouTubeInitializationResult;
//import com.google.android.youtube.player.YouTubePlayer;
//import com.google.android.youtube.player.YouTubePlayerFragment;
//import com.google.android.youtube.player.YouTubeThumbnailLoader;
//import com.google.android.youtube.player.YouTubeThumbnailView;
//
///**
// * Created by tajinder on 14/6/16.
// */
//public class NativeFragment extends Fragment {
//
//    LinearLayout mPagerIndicatorClickContainer;
//
//    CustomViewPager mViewpager;
//
//    ImageAdapter mAdapter;
//
//
//    public static final String DEVELOPER_KEY = "AIzaSyC5L2osgrIiH6zc51QCNiciiBwnjS40iLw";
//    String videoId = "XyCOJa5dcBE";
//    public void on1InitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//
//    }
//
//    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//        if (youTubeInitializationResult.isUserRecoverableError()) {
//            youTubeInitializationResult.getErrorDialog(this.getActivity(), 1).show();
//        } else {
//            Toast.makeText(this.getActivity(),
//                    "YouTubePlayer.onInitializationFailure(): " + youTubeInitializationResult.toString(),
//                    Toast.LENGTH_LONG).show();
//        }
//    }
//
//    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
//                                        boolean wasRestored) {
//        if (!wasRestored) {
//            player.cueVideo("nCgQDjiotG0");
//        }
//    }
//
////    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
////        return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
////    }
//
//    View view;
//    YouTubePlayerFragment mYoutubePlayerFragment;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        view = inflater.inflate(R.layout.lefty_native_viewstub, null);
//
//        mPagerIndicatorClickContainer = (LinearLayout) view.findViewById(R.id.viewpageclickcontainer);
//        mViewpager = (CustomViewPager) view.findViewById(R.id.matches_viewpager);
//        mViewpager.setOffscreenPageLimit(4);
//        init();
//        /*YouTubePlayerView youTubeView = (YouTubePlayerView) view.findViewById(R.id.youtube_view);
//        youTubeView.initialize(DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
//                if (!wasRestored) {
//                    youTubePlayer.cueVideo("nCgQDjiotG0");
//                }
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//                if (youTubeInitializationResult.isUserRecoverableError()) {
//                    youTubeInitializationResult.getErrorDialog(getActivity(),1).show();
//                } else {
//                    Toast.makeText(getActivity(),
//                            "YouTubePlayer.onInitializationFailure(): " + youTubeInitializationResult.toString(),
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//        });*/
////        View fragmentYoutubeView = inflater.inflate(R.layout.fragment_youtube, container, false);
////        mYoutubePlayerFragment = new YouTubePlayerFragment();
////        youTubePlayerFragment.initialize(DEVELOPER_KEY, this);
//////        mYoutubePlayerFragment.initialize(youtubeKey, this);
////        FragmentManager fragmentManager = getFragmentManager();
////        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////        fragmentTransaction.replace(R.id.fragment_youtube_player, mYoutubePlayerFragment);
////        fragmentTransaction.commit();
////        YouTubePlayerFragment youTubePlayerFragment =
////                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
////        youTubePlayerFragment.initialize(DEVELOPER_KEY, this);
//        return view;
//    }
//
//    private void init() {
//
//
//        setUpIndicator();
//        setUpViewPager();
//
//    }
//
//    private void setUpViewPager() {
//        mAdapter = new ImageAdapter();
//        mViewpager.setAdapter(mAdapter);
//    }
//
//    private void setUpIndicator() {
//
//        final ImageView[] mImageView = new ImageView[4];
//        LinearLayout.LayoutParams mLinearParams = new LinearLayout.LayoutParams(gettingWidth(15, getActivity()), gettingWidth(15, getActivity()));
//
//        for (int i = 0; i < 4; i++) {
//
//            if (i <= 4) {
//
//                mImageView[i] = new ImageView(getActivity());
//                mLinearParams.rightMargin = 20;
//                mImageView[i].setId(i);
//                mImageView[i].setLayoutParams(mLinearParams);
//                mImageView[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
//                mPagerIndicatorClickContainer.addView(mImageView[i]);
//
//                if (i == 0) {
//                    mImageView[i].setImageResource(R.drawable.dote_active);
//                } else {
//                    mImageView[i].setImageResource(R.drawable.blank_b);
//                }
//
//                mImageView[i].setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int id = v.getId();
//                        for (int i = 0; i < 4; i++) {
//                            if (i == id) {
//                                mImageView[i].setImageResource(R.drawable.dote_active);
//                                mViewpager.setCurrentItem(id, true);
//                            } else {
//                                mImageView[i].setImageResource(R.drawable.blank_b);
//                            }
//
//                        }
//                    }
//                });
//
//
//            }
//        }
//    }
//
//    class ImageAdapter extends PagerAdapter {
//
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }
//
//        @Override
//        public int getCount() {
//            return 4;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup view, int position) {
//            View imageLayout = getActivity().getLayoutInflater().inflate(R.layout.youtube_view_row, view, false);
//            YouTubeThumbnailView videoThumbView = (YouTubeThumbnailView)imageLayout.findViewById(R.id.youtube_view);
//
//
//            final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
//                @Override
//                public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
//
//                }
//
//                @Override
//                public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
//                    youTubeThumbnailView.setVisibility(View.VISIBLE);
//                    //holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
//                }
//            };
//
//
//            videoThumbView.initialize(DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
//                @Override
//                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
//                    youTubeThumbnailLoader.setVideo(videoId);
//                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
//
//                }
//
//                @Override
//                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
//
//                }
//            });
//            /*YouTubePlayerView youTubeView = (YouTubePlayerView) imageLayout.findViewById(R.id.youtube_view);
//            youTubeView.initialize(DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
//                @Override
//                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
//                    if (!wasRestored) {
//                        youTubePlayer.cueVideo("nCgQDjiotG0");
//                    }
//                }
//
//                @Override
//                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//                    if (youTubeInitializationResult.isUserRecoverableError()) {
//                        youTubeInitializationResult.getErrorDialog(getActivity(),1).show();
//                    } else {
//                        Toast.makeText(getActivity(),
//                                "YouTubePlayer.onInitializationFailure(): " + youTubeInitializationResult.toString(),
//                                Toast.LENGTH_LONG).show();
//                    }
//                }
//            });*/
//
////            YouTubePlayerView youTubeView = (YouTubePlayerView) imageLayout.findViewById(R.id.youtube_view);
////            youTubeView.initialize(DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
////                @Override
////                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
////                    if (!wasRestored) {
////                        youTubePlayer.cueVideo("nCgQDjiotG0");
////                    }
////                }
////
////                @Override
////                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
////                    if (youTubeInitializationResult.isUserRecoverableError()) {
////                        youTubeInitializationResult.getErrorDialog(getActivity(), 1).show();
////                    } else {
////                        Toast.makeText(getActivity(),
////                                "YouTubePlayer.onInitializationFailure(): " + youTubeInitializationResult.toString(),
////                                Toast.LENGTH_LONG).show();
////                    }
////                }
////            });
//
//            view.addView(imageLayout, 0);
//            return imageLayout;
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view.equals(object);
//        }
//
//        @Override
//        public void restoreState(Parcelable state, ClassLoader loader) {
//        }
//
//        @Override
//        public Parcelable saveState() {
//            return null;
//        }
//
//    }
//
//
//    public static int gettingWidth(int width, Context context) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (width * scale);
//    }
//
////    class Adapter extends FragmentStatePagerAdapter {
////        private String[] getVids() {
////            return new String[]{"ORgucLTtTDI", "WzQ2RvSLR4o", "dQw4w9WgXcQ"};
////        }
////
////        public Adapter(final FragmentManager fm) {
////            super(fm);
////        }
////
////        @Override
////        public android.support.v4.app.Fragment getItem(final int position) {
////            final YouTubePlayerSupportFragment fragment = new YouTubePlayerSupportFragment();
////            fragment.initialize(DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
////                @Override
////                public void onInitializationSuccess(final YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, final boolean b) {
////                    youTubePlayer.cueVideo(getVids()[position]);
////                }
////
////                @Override
////                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
////
////                }
////            });
////
////                return fragment;
////            }
////
////            @Override public int getCount () {
////                return getVids().length;
////            }
////            @Override public CharSequence getPageTitle ( final int position){
////                return getVids()[position] + " [" + position + "]";
////            }
////        }
////
////        @Override
////        public int getCount() {
////            return 0;
////        }
////    }
//}
