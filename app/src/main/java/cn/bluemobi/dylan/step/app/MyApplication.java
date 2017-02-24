package cn.bluemobi.dylan.step.app;

import android.app.Application;

import org.xutils.x;

/**
 * Created by yuandl on 2016-10-18.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true); //是否输出debug日志，开启debug会影响性能。
    }
}
