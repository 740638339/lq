package com.lq.yl.product.count.app.util;

import android.content.Context;

import com.lq.yl.product.count.app.mdl.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wb-liuquan.e on 2016/11/9.
 */
public class DateUtils {

    public DateUtils() {

    }

    public static String getCrtDateAndTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public static String getCrtDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    public static String getCrtTime() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(new Date());
    }

    public static int compare_date(String date,User user) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date crtDate = new Date(System.currentTimeMillis());//获取当前时间

            Date otherDate = df.parse(date);

            long time = (crtDate.getTime()-otherDate.getTime());
            if (time >= 1000*60*60*6) {
                user.resetUser();
                return 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 1;
    }


    public static String getStateTime(int lastDay){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, - lastDay);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }
}