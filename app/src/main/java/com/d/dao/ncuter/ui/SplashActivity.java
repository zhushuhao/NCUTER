package com.d.dao.ncuter.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.d.dao.ncuter.R;
import com.d.dao.ncuter.bean.Course;
import com.d.dao.ncuter.bean.User;
import com.d.dao.ncuter.presenter.impl.LoginInSplashPresenter;
import com.d.dao.ncuter.ui.manager.NavigationManager;
import com.d.dao.ncuter.ui.view.BaseView;
import com.d.dao.ncuter.utils.ACache;

import java.io.Serializable;
import java.util.List;

public class SplashActivity extends BaseAppCompatActivity implements BaseView {

    private Context mContext;//上下文对象

    private ImageView iv_icon;


    private LoginInSplashPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initOutData() {
        mContext = SplashActivity.this;
        mPresenter = new LoginInSplashPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        iv_icon = (ImageView) findViewById(R.id.iv_icon);

        AlphaAnimation alphaAnimation1 = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation1.setDuration(3000);


        final AlphaAnimation alphaAnimation2 = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation2.setDuration(3000);
        alphaAnimation2.setRepeatCount(10);

        iv_icon.startAnimation(alphaAnimation1);


        alphaAnimation1.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_icon.startAnimation(alphaAnimation2);
            }
        });
        alphaAnimation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                gotoLoginOrMain();
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //转到登陆界面或者主界面
    private void gotoLoginOrMain() {
        //判断是否已经登陆,和上次推出时登陆的类型
        String logined = ACache.get(mContext).getAsString("logined");
        if ("yes".equals(logined)) {
            String type = ACache.get(mContext).getAsString("logintype");
            if (("0").equals(type)) {//判断是多模式登陆了,还是教学信息网登陆了,1:多模式,
                User user = super.getUserInfo();
                if (user != null) {
                    //模拟登陆,获取cookie值
                    mPresenter.login(user.getUser(), user.getPass(), user.getType());
                } else {
                    //  转到多模式登陆
                    gotoMultiLogin();
                }

            } else{//2:教学信息网
                NavigationManager.getInstance().gotoActivity(mContext, MultiMainActivity.class);
            }
        } else {
            gotoMultiLogin();
        }
        finish();
    }

    //  转到多模式登陆
    private void gotoMultiLogin() {
        Bundle bundle = new Bundle();
        bundle.putInt("flag", 0);
        NavigationManager.getInstance().gotoActivity(mContext, LoginActivity.class, bundle);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListeners() {

    }

    public void onMultiLoginFailure() {
        gotoMultiLogin();
    }

    //多模式教学网登陆成功,获取到cookie
    public void onMultiLoginSuccess(List<Course> list) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("course", (Serializable) list);
        Log.e("SplashActivity--list",list.toString());
        NavigationManager.getInstance().gotoActivity(mContext, MultiMainActivity.class, bundle);
        finish();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView(this);
        super.onDestroy();
    }
}
