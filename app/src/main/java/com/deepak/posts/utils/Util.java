package com.deepak.posts.utils;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class Util {

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        return DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString();
    }
}
