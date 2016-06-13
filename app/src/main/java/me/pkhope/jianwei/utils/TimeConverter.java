package me.pkhope.jianwei.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by pkhope on 2016/6/9.
 */
public class TimeConverter {

    public static String convert(String time){

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH)+1;
        int currentDay   = calendar.get(Calendar.DAY_OF_MONTH);
        int currentHour = calendar.get(Calendar.HOUR);
        int currentMinute = calendar.get(Calendar.MINUTE);

        Date date = parse(time);
        calendar.setTime(date);
        int createdYear = calendar.get(Calendar.YEAR);
        int createdMonth = calendar.get(Calendar.MONTH)+1;
        int createdDay   = calendar.get(Calendar.DAY_OF_MONTH);
        int createdHour = calendar.get(Calendar.HOUR);
        int createdMinute = calendar.get(Calendar.MINUTE);

        String result = "";
        if (currentYear > createdYear){
            result = String.format("%d-%d-%d",createdYear,createdMonth,createdDay);
        } else if (currentMonth > createdMonth){
            result = String.format("%d-%d",createdMonth,createdDay);
        } else if (currentDay > createdDay){
            result = String.format("%d-%d",createdMonth,createdDay);
        } else if (currentHour > createdHour){
            result = String.format("%dh",currentHour - createdHour);
        } else if (currentMinute > createdMinute){
            result = String.format("%dm",currentMinute - createdMinute);
        } else {
            result = "1m";
        }
        return result;
    }

    protected static Date parse(String time){

        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy",
                Locale.US);
        Date date = null;
        try {
            date = sdf.parse(time);
        }catch (Exception e){
            e.printStackTrace();
        }

        return date;

    }

}
