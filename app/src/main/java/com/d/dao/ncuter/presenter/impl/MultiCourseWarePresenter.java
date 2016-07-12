package com.d.dao.ncuter.presenter.impl;

import android.util.Log;

import com.d.dao.ncuter.bean.CourseWare;
import com.d.dao.ncuter.presenter.BasePresenter;
import com.d.dao.ncuter.ui.MultiCourseWareActivity;
import com.d.dao.ncuter.ui.manager.DataManager;
import com.d.dao.ncuter.ui.view.BaseView;
import com.d.dao.ncuter.utils.StringUtils;

import java.util.List;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dao on 7/6/16.
 */
public class MultiCourseWarePresenter implements BasePresenter {

    private CompositeSubscription mCompositeSubscription;
    private DataManager mDataManager;
    private MultiCourseWareActivity mActivity;

    /**
     * 获取课程简介信息
     *
     * @param address 请求的地址
     */
    public void getCurseWareList(String address) {
        Log.e("myaddress", address);
        mActivity.showProgress();
        this.mCompositeSubscription.add(this.mDataManager.multi_getCourseWareList(address)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        if (mCompositeSubscription != null) {
                            mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                        mActivity.hideProgress();
                        mActivity.onError();
                    }

                    @Override
                    public void onNext(String s) {
                        mActivity.hideProgress();
                        List<CourseWare> list = StringUtils.getCourseWareMsg(s);
                        mActivity.hideProgress();
                        mActivity.onGetCourseWare(list);
                    }
                }));
    }


    @Override
    public void attachView(BaseView view) {
        this.mActivity = (MultiCourseWareActivity) view;
        this.mDataManager = DataManager.getInstance();
        this.mCompositeSubscription = new CompositeSubscription();
    }


    @Override
    public void detachView(BaseView view) {
        this.mActivity = null;
    }
}
