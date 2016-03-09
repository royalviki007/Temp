package com.example.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by root on 28/2/16.
 */
public class DateUtil {


    public static int getYear(String dateString) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());
        cal.setTime(sdf.parse(dateString));
        return cal.get(Calendar.YEAR);
    }

}
