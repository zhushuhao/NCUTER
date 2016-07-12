package com.d.dao.ncuter.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by dao on 5/22/16.
 * 控制显示toast
 */
public class ToastUtils {

    /**
     * 默认显示时间为Toast.LENGTH_SHORT
     * @param context 上下文
     * @param content 显示的内容
     */
    public static void showToast(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }

    /**
     * @param context 上下文
     * @param content 显示的内容
     * @param type    0 代表LENGTH_SHORT 1代表LENGTH_LONG
     */
    public static void showToast(Context context,String content,int type){
        if(type==1){
            Toast.makeText(context,content,Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
        }
    }
}
