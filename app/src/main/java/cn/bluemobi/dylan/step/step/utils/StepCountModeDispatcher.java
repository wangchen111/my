package cn.bluemobi.dylan.step.step.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
/**
 * 创建日期：2016/12/26
 * @author wangchen
 * @version 1.0
 * 文件名称：StepCountModeDispatcher
 * 类说明： 检测手机是否支持计步
 */
public class StepCountModeDispatcher {

    private final static int MICROSECONDS_IN_ONE_MINUTE = 60000000;

    //    private StepCountListener listener;
//    private StepDetector stepDetector;
    private Context context;
    private boolean hasSensor;

    public StepCountModeDispatcher(Context context) {
        this.context = context;
        hasSensor = isSupportStepCountSensor();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public boolean isSupportStepCountSensor() {
        return context.getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER);
    }

    /**
     * 判断该设备是否支持计歩
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isSupportStepCountSensor(Context context) {
        // 获取传感器管理器的实例
        SensorManager sensorManager = (SensorManager) context
                .getSystemService(context.SENSOR_SERVICE);
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        return countSensor != null || detectorSensor != null;
    }
}
