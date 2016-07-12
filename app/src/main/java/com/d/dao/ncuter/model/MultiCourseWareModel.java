package com.d.dao.ncuter.model;


import android.util.Log;

import com.d.dao.ncuter.api.BaseUrl;
import com.d.dao.ncuter.api.NCUTER;

import rx.Observable;

/**
 * 多模式教学网课程简介信息获取
 * Created by dao on 7/6/16.
 */
public class MultiCourseWareModel {
    private static MultiCourseWareModel mInstance;

    public synchronized static MultiCourseWareModel getInstance() {
        if (mInstance == null) {
            mInstance = new MultiCourseWareModel();
        }
        return mInstance;
    }

    //多模式教学网课程简介信息获取
    public Observable<String> getCourseWareMsg(String address) {
        Log.e("address2", address);

        String first = "";
        String second = "";
        if (address.indexOf("/") == address.length() - 1) {
            first = address.substring(0, address.length() - 2);
            return NCUTER.getInstance().getApiService(BaseUrl.BASE_MULTI_URL).
                    multi_getCourseWareMsg(first);
        } else {
            String[] temp = address.split("/");
            first = temp[0];
            second = temp[1];
            return NCUTER.getInstance().getApiService(BaseUrl.BASE_MULTI_URL).
                    multi_getCourseWareMsg(first, second);
        }


    }
}
