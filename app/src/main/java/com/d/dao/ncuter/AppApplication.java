package com.d.dao.ncuter;

import android.app.Application;
import android.content.Context;

import com.anupcowkur.reservoir.Reservoir;
import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * Created by dao on 7/6/16.
 */
public class AppApplication extends Application {

    public static Context mContext;
    public static final long ONE_KB = 1024L;
    public static final long ONE_MB = ONE_KB * 1024L;
    public static final long CACHE_DATA_MAX_SIZE = ONE_MB * 3L;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = AppApplication.this;
        AutoLayoutConifg.getInstance().useDeviceSize();
        this.initReservoir();
    }
    private void initReservoir() {
        try {
            Reservoir.init(this, CACHE_DATA_MAX_SIZE);
        } catch (Exception e) {
            //failure
            e.printStackTrace();
        }
    }

    public static Context getContext(){
        return mContext;
    }
}
