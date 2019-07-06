package com.widyatama.tvshowtime.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.widyatama.tvshowtime.R;

public class ThemeUtils {

    public static String themeState = "themeState";
    public static String themeStateKey = "themeStateKey";

    private static SharedPreferences sharedPreferences;

    public static void setState(Context context, boolean value) {
        sharedPreferences = context.getSharedPreferences(themeState, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(themeStateKey, value);
        editor.apply();
    }

    public static boolean getState(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(context.getResources().getString(R.string.pref_theme), false);
    }

    public static void themeState(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isDark = preferences.getBoolean(context.getResources().getString(R.string.pref_theme), false);
        Log.d("DARKMODE", "themeState: " + isDark);
        if (isDark) {
            context.setTheme(R.style.AppTheme_Dark);
        } else {
            context.setTheme(R.style.AppTheme_Light);
        }
    }

    public static void themeDetailState(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isDark = preferences.getBoolean(context.getResources().getString(R.string.pref_theme), false);
        if (isDark) {
            context.setTheme(R.style.AppTheme_Dark_Detail);
        } else {
            context.setTheme(R.style.AppTheme_Light_Detail);
        }
    }

    public static void setLightStatusBar(View view, Activity activity){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    public static void clearLightStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            window.setStatusBarColor(ContextCompat
                    .getColor(activity,R.color.colorPrimaryDark));
        }
    }
}
