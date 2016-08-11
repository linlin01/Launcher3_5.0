package com.android.launcher3.preview;

import com.android.launcher3.Launcher;

import android.content.Context;
import android.content.SharedPreferences;

public class RGKPreference implements
        SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = "RGKPreference";
    public static final String RGK_PREFERENCE_NAME = "rgk_preference";
    public static final String KEY_IS_FIRST = "isfirst";
    public static final String KEY_SCREEN_COUNT = "screen_count";
    public static final String KEY_DEFAULT_SCREEN = "default_screen";
    public static final String KEY_SLIDE_ANIMATION = "workspace_animation";
    public static final String KEY_CURRENT_THEME = "current_theme";
    public static final String KEY_THEME_LOST = "theme_lost";

    public static String current_theme = null;

    private SharedPreferences mRGKConfigSharedPrefs;

    SharedPreferences.Editor editor;

    public RGKPreference(Context context) {
        mRGKConfigSharedPrefs = context.getSharedPreferences(
                RGK_PREFERENCE_NAME, 0);
        editor = mRGKConfigSharedPrefs.edit();
        mRGKConfigSharedPrefs.registerOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
            String key) {
//        if (key.equals(KEY_SCREEN_COUNT)) {
//            Launcher.SCREEN_COUNT = mRGKConfigSharedPrefs.getInt(
//                    KEY_SCREEN_COUNT, Launcher.SCREEN_COUNT);
//        } else if (key.equals(KEY_DEFAULT_SCREEN)) {
//            Launcher.DEFAULT_SCREEN = mRGKConfigSharedPrefs.getInt(
//                    KEY_DEFAULT_SCREEN, Launcher.DEFAULT_SCREEN);
//        } else if (key.equals(KEY_CURRENT_THEME)) {
//            current_theme = getPreferenceStringValue(KEY_CURRENT_THEME);
//        }
    }

    public void writeStringValue(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void writeIntValue(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getScreenCount(Context context) {
        int count = mRGKConfigSharedPrefs.getInt(KEY_SCREEN_COUNT, -1);
        return count;
    }

    public String getPreferenceStringValue(String key) {
        return mRGKConfigSharedPrefs.getString(key, null);
    }

    public int getPreferenceIntValue(String key) {
        return mRGKConfigSharedPrefs.getInt(key, 0);
    }

    public int getDefaultScreen() {
        int defaultScreen = mRGKConfigSharedPrefs
                .getInt(KEY_DEFAULT_SCREEN, -1);
        if (defaultScreen == -1) {
//            defaultScreen = Launcher.DEFAULT_SCREEN;
            writeIntValue(KEY_DEFAULT_SCREEN, defaultScreen);
        }
        return defaultScreen;
    }
}
