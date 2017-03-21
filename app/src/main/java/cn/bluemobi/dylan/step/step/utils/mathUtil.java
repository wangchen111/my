package cn.bluemobi.dylan.step.step.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 创建日期：2017/2/16
 * @author wangchen
 * @version 1.0
 * 文件名称：MathUtil
 * 类说明：计算工具类
 */

public class mathUtil {
    private static double distance;
    private static double calories;
    private double stride;

    public static String getMails(int step){
        distance = step*0.5*0.001;
        DecimalFormat df = new DecimalFormat("######0.00");
        String str = df.format(distance);
        return str;
    }

    public static String getCalories(int step){
        calories = distance*3.4 *60;
        DecimalFormat df = new DecimalFormat("######0.00");
        String str = df.format(calories);
        return str;
    }
    public static String getDay(String beginTime,String endTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/MM/dd");//输入日期的格式
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(beginTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = simpleDateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int dayCount = (int)(cal2.getTimeInMillis()-cal1.getTimeInMillis())/(1000*3600*24);//从间隔毫秒变成间隔天数
        return dayCount+"";
    }
}
