package com.example.taras.weather;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Taras on 14.11.2017.
 */

public class CalendarMethods {
    public synchronized static String getTime2400(Long time){
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);
        String newTime ;
        if(calendar.get(Calendar.MINUTE) < 9){
            newTime = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY)) + ":0" + calendar.get(Calendar.MINUTE);
        }else{newTime = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY)) + ":" +
                Integer.toString(calendar.get(Calendar.MINUTE));}
        return newTime;
    }
    public synchronized static String getDayOfWeek(Long time) {
        Map<Integer, String> days = new HashMap<>();
        days.put(1, "Sunday");
        days.put(2, "Monday");
        days.put(3, "Tuesday");
        days.put(4, "Wednesday");
        days.put(5, "Thursday");
        days.put(6, "Friday");
        days.put(7, "Saturday");
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return days.get(day);
    }
    public synchronized static String getDate(Long time){
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);
        String date = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + "." +
                Integer.toString(calendar.get(Calendar.MONTH)+ 1) + "." +
                Integer.toString(calendar.get(Calendar.YEAR));
        return date;
    }
}
