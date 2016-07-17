package com.d.dao.ncuter.presenter.impl;

import android.util.Log;

import com.d.dao.ncuter.bean.Course;
import com.d.dao.ncuter.presenter.ILoginPresenter;
import com.d.dao.ncuter.ui.LoginActivity;
import com.d.dao.ncuter.ui.SplashActivity;
import com.d.dao.ncuter.ui.manager.DataManager;
import com.d.dao.ncuter.ui.view.BaseView;
import com.d.dao.ncuter.utils.ReservoirUtils;
import com.d.dao.ncuter.utils.StringUtils;

import java.util.List;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * 登陆
 * Created by dao on 7/3/16.
 */
public class LoginInSplashPresenter implements ILoginPresenter {

    private DataManager mDataManager;

    private CompositeSubscription mCompositeSubscription;

    private SplashActivity mActivity;

    @Override
    public void login(String user, String pass, int flag) {
        mActivity.showProgress();
        if (flag == 0) {//多模式教学网登陆
            this.mCompositeSubscription.add(this.mDataManager.multi_login(user, pass).subscribe(new Subscriber<String>() {
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
                    mActivity.onMultiLoginFailure();
                }

                @Override
                public void onNext(String s) {
//                    Log.e("success result", s);
                    //　加入判断是否登陆成功
                    if (s.equals("failure")) {
                        onError(new Throwable("用户名密码不匹配"));
                    } else {//登陆成功
                        //截取有用的信息
                        List<Course> list = StringUtils.getCourseMsg(s);
                        //储存课程信息
                        ReservoirUtils.getInstance().refresh("course", list);
                        mActivity.hideProgress();
                        mActivity.onMultiLoginSuccess(list);
                    }
                }
            }));
        }else {//教学信息网登陆
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
                            mActivity.hideProgress();
                            mActivity.onTeachLoginFailure();
                        }

                        @Override
                        public void onNext(String s) {
//                            mActivity.hideProgress();
                            if (("yes").equals(s)) {//第一次登陆成功
                                //success
//                                queryInfo();
                                mActivity.onTeachLoginSuccess();
                            }else if("mid".equals(s)){//已经登陆了一个用户
//                                queryInfo();
                                mActivity.onTeachLoginSuccess();
                            }else {//失败
                                //failure
                                Log.e("teach","login teach failure");
                                onError(new Throwable("用户名与密码不匹配"));
                            }
                        }
                    }));
        }
    }

    @Override
    public void attachView(BaseView view) {
        this.mActivity = (SplashActivity) view;
        this.mDataManager = DataManager.getInstance();
        this.mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView(BaseView view) {
        this.mActivity = null;
    }


}
