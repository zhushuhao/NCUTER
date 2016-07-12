package com.d.dao.ncuter.presenter.impl;

import com.d.dao.ncuter.presenter.BasePresenter;
import com.d.dao.ncuter.ui.LoginActivity;
import com.d.dao.ncuter.ui.MultiMainActivity;
import com.d.dao.ncuter.ui.manager.DataManager;
import com.d.dao.ncuter.ui.view.BaseView;
import com.d.dao.ncuter.utils.ReservoirUtils;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by dao on 7/6/16.
 */
public class MultiMainPresenter implements BasePresenter {

    private CompositeSubscription mCompositeSubscription;
    private DataManager mDataManager;
    private ReservoirUtils reservoirUtils;


    private MultiMainActivity mMultiActivity;


//    /**
//     * 获取课程信息页面
//     * @param address 请求的地址
//     */
//    public void getCurseDetailMessage(String address){
//    }


    @Override
    public void attachView(BaseView view) {
        this.mMultiActivity = (MultiMainActivity) view;
        this.mDataManager = DataManager.getInstance();
        this.mCompositeSubscription = new CompositeSubscription();
        reservoirUtils = ReservoirUtils.getInstance();
    }

    @Override
    public void detachView(BaseView view) {
        this.mMultiActivity = null;
    }
}
