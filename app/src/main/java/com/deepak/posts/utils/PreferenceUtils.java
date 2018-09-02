package com.deepak.posts.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.deepak.posts.R;

/**
 * Class to manage Preference utility methods
 * Created by mohit.sharma on 07-07-2016.
 *
 * @version 2.0
 */
public class PreferenceUtils {

    /**
     * Private Constructor to hide the implicit public one
     */
    private PreferenceUtils() {
        // No initialization required
    }

    /**
     * Set Application preference for type string
     *
     * @param mContext calling application context
     * @param key      preference key
     * @param value    string preference value
     */
    public static void setAppPreference(Context mContext, String key, String value) {
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getString(R.string.app_name), 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Get Application preference of type string
     *
     * @param mContext calling application context
     * @param key      preference key
     * @param defValue default preference value
     * @return preference value as string
     */
    public static String getAppStringPreference(Context mContext, String key, String defValue) {
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getString(R.string.app_name), 0);
        return prefs.getString(key, defValue);
    }
}
