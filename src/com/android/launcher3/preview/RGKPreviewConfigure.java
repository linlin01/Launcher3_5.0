package com.android.launcher3.preview;

import android.app.Activity;
import android.util.DisplayMetrics;

public class RGKPreviewConfigure {
    public static int screenHeight = 0;
    public static int screenWidth = 0;
    public static float screenDensity = 0;
    public static boolean isMove = false;
    public static boolean isChangingPage = false;
    public static boolean isHideButton = false;
    public static int curentPage = 0;
    public static int countPages = 0;
    public static int startDragItemIndex = 0;
    public static int endDragItemIndex = 0;
    public static int moveNum = 0;

    public static final int WORKSPACE_PREVIEW_PAGE_EXCHANG_DENSITY = 40;
    public static final int WORKSPACE_PREVIEW_COLUMNS = 3;
    public static final int WORKSPACE_PREVIEW_PAGE_SIZE = 9;

    public static void init(Activity context) {
        if (screenDensity == 0 || screenWidth == 0 || screenHeight == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(dm);
            RGKPreviewConfigure.screenDensity = dm.density;
            RGKPreviewConfigure.screenHeight = dm.heightPixels;
            RGKPreviewConfigure.screenWidth = dm.widthPixels;
        }
        curentPage = 0;
        countPages = 0;
    }

}
