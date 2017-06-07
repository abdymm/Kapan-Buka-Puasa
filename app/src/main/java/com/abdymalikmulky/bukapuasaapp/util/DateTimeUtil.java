package com.abdymalikmulky.bukapuasaapp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 6/5/17.
 */

public class DateTimeUtil {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String ONLY_DATE_FORMAT = "yyyy-MM-dd";
    private static final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public DateTimeUtil() {
    }

    public Date getNowDate(){
        return new Date();
    }

    public static String getStringNowOnlyDate(){
        return new SimpleDateFormat(ONLY_DATE_FORMAT).format(new Date());
    }

    public long getTimeDiff(String dateStop){
        long diff = 0;
        String dateStart = dateFormat.format(getNowDate());

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = dateFormat.parse(dateStart);
            d2 = dateFormat.parse(dateStop);
            diff = d2.getTime() - d1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return diff;
    }

    public static final String removeSecondInStringTime(String time){
        return time.substring(0, time.length() - 3);
    }
}
