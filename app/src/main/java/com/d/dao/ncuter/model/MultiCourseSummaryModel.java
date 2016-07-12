package com.d.dao.ncuter.model;


import com.d.dao.ncuter.api.BaseUrl;
import com.d.dao.ncuter.api.NCUTER;

import rx.Observable;

/**
 * 多模式教学网课程简介信息获取
 * Created by dao on 7/6/16.
 */
public class MultiCourseSummaryModel {
    private static MultiCourseSummaryModel mInstance;

    public synchronized static MultiCourseSummaryModel getInstance() {
        if (mInstance == null) {
            mInstance = new MultiCourseSummaryModel();
        }
        return mInstance;
    }
    //多模式教学网课程简介信息获取
    public Observable<String> getCourseSummaryMsg(String address) {
        return NCUTER.getInstance().getApiService(BaseUrl.BASE_MULTI_URL).multi_getCourseSummaryMsg(address);
    }
}
