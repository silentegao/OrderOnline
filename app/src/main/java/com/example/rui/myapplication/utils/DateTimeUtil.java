package com.example.rui.myapplication.utils;


import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/11/28/028.
 */

public class DateTimeUtil {

    /**
     * String 转换 Date
     *
     * @param str
     * @param format
     * @return
     */
    public static Date string2Date(String str, String format) {
        try {
            return new SimpleDateFormat(format).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String stringFromat(String str, String formatOld, String format) {
        String newDate = "";
        if (TextUtils.isEmpty(str) == false) {
            try {
                Date date = new SimpleDateFormat(formatOld).parse(str);
                newDate = date2String(date.getTime(), format);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return newDate;
    }

    /**
     * Date（long） 转换 String
     *
     * @param time
     * @param format
     * @return
     */
    public static String date2String(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(time);
        return s;
    }


    /**
     * 年份加减
     *
     * @param time
     * @param format
     * @return
     */
    public static String addYear(String time, String format, int amount) {
        Date date = DateTimeUtil.string2Date(time, format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, amount);
        date = calendar.getTime();
        return DateTimeUtil.date2String(date.getTime(), format);

    }

    public static long getTimeLong(String time, String format) {
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }
}
