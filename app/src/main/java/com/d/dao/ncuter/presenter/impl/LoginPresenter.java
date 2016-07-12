package com.d.dao.ncuter.presenter.impl;

import android.util.Log;

import com.d.dao.ncuter.bean.Course;
import com.d.dao.ncuter.presenter.ILoginPresenter;
import com.d.dao.ncuter.ui.LoginActivity;
import com.d.dao.ncuter.ui.manager.DataManager;
import com.d.dao.ncuter.ui.view.BaseView;
import com.d.dao.ncuter.utils.ReservoirUtils;
import com.d.dao.ncuter.utils.StringUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * 登陆
 * Created by dao on 7/3/16.
 */
public class LoginPresenter implements ILoginPresenter {

    private DataManager mDataManager;

    private CompositeSubscription mCompositeSubscription;

    private LoginActivity mActivity;

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
                    mActivity.onLoginFailure();
                    mActivity.hideProgress();
                }

                @Override
                public void onNext(String s) {
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
        } else {//教学信息网登陆
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
                            mActivity.onLoginFailure();
                        }

                        @Override
                        public void onNext(String s) {
                            mActivity.hideProgress();
                            if (("yes").equals(s)) {
                                //success
                                queryInfo();
                            } else {
                                //failure
                            }


                        }
                    }));
        }
    }

    private void queryInfo() {
        this.mCompositeSubscription.add(this.mDataManager.teach_query_person_info()
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
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("s.length",""+s.length());
                        Log.e("s", s);
                        String sa = "";
                        try {
                            sa=new String(s.getBytes("UTF-8"),"gb2312");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        Pattern p = Pattern.compile("bgcolor=(.*)</td>");
                        Matcher m = p.matcher(sa);
                        while (m.find()) {
                            String str = m.group(0);
                            Log.e("str",str);
                        }
                    }
                }));
    }

    @Override
    public void attachView(BaseView view) {
        this.mActivity = (LoginActivity) view;
        this.mDataManager = DataManager.getInstance();
        this.mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView(BaseView view) {
        this.mActivity = null;
    }


}
