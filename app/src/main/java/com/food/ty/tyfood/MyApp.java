package com.food.ty.tyfood;

import android.app.Application;
import org.xutils.x;
/**
 * Created by ty on 2017/3/2.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false); //输出debug日志，开启会影响性能
    }
}
