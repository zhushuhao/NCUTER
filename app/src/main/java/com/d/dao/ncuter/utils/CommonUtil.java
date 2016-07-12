package com.d.dao.ncuter.utils;

import android.view.View;

/**
 * Created by dao on 7/6/16.
 */
public class CommonUtil {

    //view是否可见
    public static boolean isVisible(View view){
        if(view.getVisibility()==View.VISIBLE){
            return true;
        }
        return false;
    }
}
