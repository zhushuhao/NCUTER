package com.d.dao.ncuter.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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


    AlphaAnimation alphaAnimation1, alphaAnimation2;

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

        alphaAnimation1 = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation1.setDuration(3000);


        alphaAnimation2 = new AlphaAnimation(0.1f, 1.0f);
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
        //判断是否已经登陆
        if (super.isLogined()) {//上次登陆过了
            //获取登陆类型
            int type = super.getLoginType();//上次登陆的类型
            User user = super.getUserInfo(type);//获取保存的用户信息
            if (user != null) {//不为空
                //模拟登陆,获取cookie值
                mPresenter.login(user.getUser(), user.getPass(), user.getType());
            } else {//为空
                //转到登陆界面,默认为上次登陆的
                gotoLogin(type);
                finish();
            }
        } else {//默认多模式
            gotoLogin(0);
            finish();
        }
    }



    @Override
    protected void initData() {

    }

    @Override
    protected void initListeners() {

    }

    public void onMultiLoginFailure() {
        gotoLogin(0);
        finish();
    }

    //多模式教学网登陆成功,获取到cookie
    public void onMultiLoginSuccess(List<Course> list) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("course", (Serializable) list);
        NavigationManager.getInstance().gotoActivity(mContext, MultiMainActivity.class, bundle);
        SplashActivity.this.finish();
    }

    public void onTeachLoginFailure(){
        gotoLogin(1);
        finish();
    }
    public void onTeachLoginSuccess(){
        NavigationManager.getInstance().gotoActivity(mContext, TeachMainActivity.class);
        SplashActivity.this.finish();
    }
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView(this);
        iv_icon.clearAnimation();
    }
}
