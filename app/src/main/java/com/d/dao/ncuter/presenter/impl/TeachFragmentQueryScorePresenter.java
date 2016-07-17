package com.d.dao.ncuter.presenter.impl;

import android.util.Log;

import com.d.dao.ncuter.bean.PersonInfo;
import com.d.dao.ncuter.bean.TeachScore;
import com.d.dao.ncuter.presenter.BasePresenter;
import com.d.dao.ncuter.ui.fragment.TeachPersonInfoFragment;
import com.d.dao.ncuter.ui.fragment.TeachScoreFragment;
import com.d.dao.ncuter.ui.manager.DataManager;
import com.d.dao.ncuter.ui.view.BaseView;
import com.d.dao.ncuter.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dao on 7/6/16.
 * 查询成绩
 */
public class TeachFragmentQueryScorePresenter implements BasePresenter {

    private CompositeSubscription mCompositeSubscription;
    private DataManager mDataManager;

    private TeachScoreFragment mActivity;

    public void queryScore(final boolean reQuery) {
        if(!reQuery){
            mActivity.showProgress();
        }
        this.mCompositeSubscription.add(this.mDataManager.teach_query_score()
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        if (mCompositeSubscription != null) {
                            mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.hideProgress();
                        mActivity.onQueryScoreFailure();
                        Log.e("error", e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        mActivity.hideProgress();
                        if (s.contains("rxnf")) {//得到查询结果
                            Log.e("查询结果", "得到未处理的数据");
                            try {
                                List<TeachScore> list = StringUtils.getTeachScoreList(s);
                                mActivity.onQueryScoreSuccess(list);
                            } catch (Exception e) {
                                onError(new Throwable(e.toString()));
                            }
                        } else if (s.contains("您还没有登陆")) {
                            if(!reQuery){
                                mActivity.reLogin();
                            }else {
                                mActivity.hideProgress();
                                onError(new Throwable("莫名失败"));
                            }
                        } else {
                            onError(new Throwable("error"));
                        }
                    }
                }));
    }


    @Override
    public void attachView(BaseView view) {
        this.mActivity = (TeachScoreFragment) view;
        this.mDataManager = DataManager.getInstance();
        this.mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView(BaseView view) {
        this.mActivity = null;
    }
}
