package com.d.dao.ncuter.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.d.dao.ncuter.bean.User;
import com.d.dao.ncuter.ui.manager.NavigationManager;
import com.d.dao.ncuter.utils.ACache;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by dao on 5/29/16.
 */
public abstract class BaseAppCompatActivity extends AutoLayoutActivity {

    private ProgressDialog pd;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = BaseAppCompatActivity.this;

        initOutData();
        setContentView(this.getLayoutId());
        this.initToolbar(savedInstanceState);
        this.initViews(savedInstanceState);
        this.initData();
        this.initListeners();
    }

    //  转到登陆
    protected void gotoLogin(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("flag", type);//flag值
        NavigationManager.getInstance().gotoActivity(mContext, LoginActivity.class, bundle);
    }

    //登陆成功,设置登陆模式
    protected void setLoginMode(int flag, User user) {
        setLogin(true, flag);//登陆状态为true,登陆模式为多模式教学网
        saveUserAndPass(user);
    }

    //设置登陆状态
    protected void setLogin(boolean b, int type) {
        if (b) {// 登陆状态为true,
            ACache.get(mContext).put("logined", "yes");
            if (type == 0) {//登陆的是多模式教学网,
                ACache.get(mContext).put("logintype", "0");
            } else {//登陆的是教学信息网
                ACache.get(mContext).put("logintype", "1");
            }
        } else {//登陆状态为false
            ACache.get(mContext).put("logined", "no");
        }
    }

    //是否登陆
    protected boolean isLogined() {
        String str = ACache.get(mContext).getAsString("logined");
        if (str != null && str.equals("yes")) {
            return true;
        } else {
            return false;
        }
    }

    //登陆类型,0:多模式还是1:教学信息网
    protected int getLoginType() {
        String type = ACache.get(mContext).getAsString("logintype");
        if (type != null) {
            if (type.equals("1")) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

    /**
     * 储存用户信息
     *
     * @param user
     */
    protected void saveUserAndPass(User user) {
        int type = user.getType();
        ACache.get(mContext).put(String.valueOf(type), user);
    }

    protected User getUserInfo(int type) {
        User user = (User) ACache.get(mContext).getAsObject(String.valueOf(type));
        return user;
    }

    //布局ID
    protected abstract int getLayoutId();

    //　初始化外部传来的数据
    protected abstract void initOutData();

    protected abstract void initToolbar(Bundle savedInstanceState);

    protected abstract void initViews(Bundle savedInstanceState);

    //准备数据
    protected abstract void initData();

    protected abstract void initListeners();


    //显示进度框
    protected void showProgressDialog(String title, String content) {
        pd = ProgressDialog.show(this, title, content, false, false);
        pd.show();
    }

    //隐藏进度框
    protected void hiddenProgressDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
            pd = null;
        }
    }


}
