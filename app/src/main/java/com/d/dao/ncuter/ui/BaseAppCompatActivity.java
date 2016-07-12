package com.d.dao.ncuter.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.d.dao.ncuter.bean.User;
import com.d.dao.ncuter.utils.ACache;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.FileNotFoundException;
import java.util.concurrent.CopyOnWriteArrayList;

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


    //设置登陆状态
    protected void setLogin(boolean b, int type) {
        if (b) {// 登陆状态为true,
            ACache.get(mContext).put("logined", "yes");
            if (type == 0) {//登陆的是多模式教学网,
                ACache.get(mContext).put("logintype", "0");
            } else if (type == 1) {//登陆的是教学信息网
                ACache.get(mContext).put("logintype", "1");
            }
        } else {//登陆状态为false
            ACache.get(mContext).put("logined", "no");
        }
    }
    //是否登陆
    protected boolean isLogined(){
        String str = ACache.get(mContext).getAsString("logined");
        if(str.equals("yes")){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 储存用户信息
     *
     * @param user
     */
    protected void saveUserAndPass(User user) {
        ACache.get(mContext).put("user", user);
    }

    protected User getUserInfo() {
        User user = (User) ACache.get(mContext).getAsObject("user");
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
