package com.mufeez.taskzero.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Mufeez kashimji on 27-09-19.
 */

public class TimeUtils {

    public static final String FULL_TIME_FORMAT = "EEE, dd MMM HH:mm";

    private TimeUtils(){}

    public static String getFormattedTime(long millis, String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.ENGLISH);
        String dateString = formatter.format(new Date(millis));
        return dateString;
    }
}
