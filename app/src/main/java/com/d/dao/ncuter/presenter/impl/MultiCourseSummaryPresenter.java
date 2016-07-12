package com.d.dao.ncuter.presenter.impl;

import android.util.Log;

import com.d.dao.ncuter.bean.CourseSummary;
import com.d.dao.ncuter.presenter.BasePresenter;
import com.d.dao.ncuter.ui.MultiCourseSummaryActivity;
import com.d.dao.ncuter.ui.manager.DataManager;
import com.d.dao.ncuter.ui.view.BaseView;
import com.d.dao.ncuter.utils.ReservoirUtils;
import com.d.dao.ncuter.utils.StringUtils;

import java.util.List;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dao on 7/6/16.
 */
public class MultiCourseSummaryPresenter implements BasePresenter {

    private CompositeSubscription mCompositeSubscription;
    private DataManager mDataManager;


    private MultiCourseSummaryActivity mActivity;


    /**
     * 获取课程简介信息
     *
     * @param address 请求的地址
     * @param code    课程编号,用来做本地储存的tag
     */
    public void getCurseSummaryMessage(String address, final String code) {
        mActivity.showProgress();
        this.mCompositeSubscription.add(this.mDataManager.multi_getCourseSummaryMsg(address)
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
                    }
                    @Override
                    public void onNext(String s) {
//                        Log.e("success", s);
                        //截取数据
                        List<CourseSummary> list = StringUtils.getCourseSummaryMsg(s);
                        if(list==null||list.size()==0){
                            onError(new Throwable("获取简介失败"));
                        }else {
                            //储存
                            ReservoirUtils.getInstance().refresh(code, list);
                            mActivity.hideProgress();
                            mActivity.onGetMessageSuccess(list);
                        }
                    }
                }));
    }


    @Override
    public void attachView(BaseView view) {
        this.mActivity = (MultiCourseSummaryActivity) view;
        this.mDataManager = DataManager.getInstance();
        this.mCompositeSubscription = new CompositeSubscription();
    }



    @Override
    public void detachView(BaseView view) {
        this.mActivity = null;
    }
}
