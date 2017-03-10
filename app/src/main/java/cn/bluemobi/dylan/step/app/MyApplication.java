package cn.bluemobi.dylan.step.app;

import android.app.Application;

import org.xutils.x;

/**
 * 创建日期：2016 12 20
 * @author wangchen
 * @version 1.0
 * 文件名称：MyApplication
 * 类说明：
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true); //是否输出debug日志，开启debug会影响性能。
    }
}
