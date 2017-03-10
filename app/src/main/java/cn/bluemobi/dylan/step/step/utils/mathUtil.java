package cn.bluemobi.dylan.step.step.utils;

import java.text.DecimalFormat;

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
        distance = step*0.6*0.001;
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
}
