package com.widyatama.tvshowtime.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String dateToString(String date, String oldFormat, String newFormat){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat, Locale.getDefault());
            Date newDate = dateFormat.parse(date);
            dateFormat = new SimpleDateFormat(newFormat, Locale.getDefault());
            return dateFormat.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return " ";
        }
    }

    public static String dateToString(String date, String oldFormat, String newFormat, Locale oldLocale, Locale newLocale) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat, oldLocale);
            Date newDate = dateFormat.parse(date);
            dateFormat = new SimpleDateFormat(newFormat, newLocale);
            return dateFormat.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return " ";
        }
    }
}
