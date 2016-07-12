package com.d.dao.ncuter.ui.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dao on 5/23/16.
 * intent跳转管理
 */
public class NavigationManager {

    private static NavigationManager mInstance;

    static {
        mInstance = new NavigationManager();
    }

    public static synchronized NavigationManager getInstance() {
        return mInstance;
    }

    public void gotoActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public void gotoActivity(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }




}
