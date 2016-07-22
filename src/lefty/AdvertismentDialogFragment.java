package lefty;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.launcher3.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by sandeep on 6/7/16.
 */
public class AdvertismentDialogFragment extends DialogFragment implements View.OnClickListener {


    ImageView mCross;
    ImageView mAdv_Image;

    ProgressBar mProgressBar;

    String banner_image;
    String redirect_link;

    public static AdvertismentDialogFragment newInstance(String banner_image, String redirect_link) {
        AdvertismentDialogFragment f = new AdvertismentDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("image", banner_image);
        args.putString("redirect_link", redirect_link);
        f.setArguments(args);

        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        banner_image = getArguments().getString("image");
        redirect_link = getArguments().getString("redirect_link");

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        //must setBackgroundDrawable(TRANSPARENT) in onActivityCreated()
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View view = inflater.inflate(R.layout.lefty_advertisment_dialog, container, false);

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCross = (ImageView) view.findViewById(R.id.cross);
        mAdv_Image = (ImageView) view.findViewById(R.id.adv_image);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress);

        mCross.setOnClickListener(this);
        mAdv_Image.setOnClickListener(this);


        ImageLoader.getInstance().displayImage(banner_image, mAdv_Image, getDisplayOptions(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                super.onLoadingFailed(imageUri, view, failReason);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                super.onLoadingStarted(imageUri, view);
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    public DisplayImageOptions getDisplayOptions() {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .considerExifParams(true)

                .build();

        return options;

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.adv_image:
                openBrowser(redirect_link);
                break;

            case R.id.cross:
                getDialog().dismiss();
                break;

            default:
                break;

        }


    }

    void openBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
