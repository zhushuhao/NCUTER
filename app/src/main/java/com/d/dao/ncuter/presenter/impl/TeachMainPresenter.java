package com.d.dao.ncuter.presenter.impl;

import android.util.Log;

import com.d.dao.ncuter.bean.PersonInfo;
import com.d.dao.ncuter.presenter.BasePresenter;
import com.d.dao.ncuter.ui.MultiMainActivity;
import com.d.dao.ncuter.ui.TeachMainActivity;
import com.d.dao.ncuter.ui.manager.DataManager;
import com.d.dao.ncuter.ui.view.BaseView;
import com.d.dao.ncuter.utils.ReservoirUtils;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dao on 7/6/16.
 */
public class TeachMainPresenter implements BasePresenter {

    private CompositeSubscription mCompositeSubscription;
    private DataManager mDataManager;

    private TeachMainActivity mActivity;

    //重新登陆教学信息网
    public void reLogin(String user,String pass){
        this.mCompositeSubscription.add(this.mDataManager.teach_login(user, pass).
                subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        if (mCompositeSubscription != null) {
                            mCompositeSubscription.remove(this);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                        mActivity.onReLoginFailure();
                    }

                    @Override
                    public void onNext(String s) {
                        if (("yes").equals(s)) {//第一次登陆成功
                            mActivity.onReLoginSuccess();
                        }else if("mid".equals(s)){//已经登陆了一个用户
                            mActivity.onReLoginSuccess();
                        }
                        else {//失败
                            Log.e("teach","login teach failure");
                            onError(new Throwable("用户名与密码不匹配"));
                        }
                    }
                }));
    }


    @Override
    public void attachView(BaseView view) {
        this.mActivity = (TeachMainActivity) view;
        this.mDataManager = DataManager.getInstance();
        this.mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView(BaseView view) {
        this.mActivity = null;
    }
}
