package com.zljysoft.SmartDoctor.doctor.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: ASUS
 * Date: 13-10-31
 * Time: 上午9:29
 * To change this template use File | Settings | File Templates.
 */
public class DataUtil {
    public static int compareSameDay(Calendar first,Calendar second){
        return (first.get(Calendar.YEAR)-second.get(Calendar.YEAR))*365
                +(first.get(Calendar.MONTH)-second.get(Calendar.MONTH))*30
                +(first.get(Calendar.DATE)-second.get(Calendar.DATE));
    }
    public static int compareSameMonth(Calendar first,Calendar second){
        return (first.get(Calendar.YEAR)-second.get(Calendar.YEAR))*12
                +(first.get(Calendar.MONTH)-second.get(Calendar.MONTH));
    }
    public static String transferCalendarToString(Calendar calendar){
        return transferCalendarToString(calendar,"yyyyMMdd");
    }
    public static String transferCalendarToString(Calendar calendar,String format){
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(calendar.getTime());
    }
}
