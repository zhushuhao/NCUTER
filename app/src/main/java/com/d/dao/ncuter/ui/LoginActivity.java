package com.d.dao.ncuter.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d.dao.ncuter.R;
import com.d.dao.ncuter.bean.Course;
import com.d.dao.ncuter.bean.User;
import com.d.dao.ncuter.presenter.impl.LoginPresenter;
import com.d.dao.ncuter.ui.manager.NavigationManager;
import com.d.dao.ncuter.utils.ACache;
import com.d.dao.ncuter.utils.ToastUtils;
import com.d.dao.ncuter.ui.view.BaseView;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;

public class LoginActivity extends BaseAppCompatActivity implements View.OnClickListener, BaseView {

    private LinearLayout ll_root;//登陆界面根部局,用来控制背景色
    private Button btn_login;//登陆按钮
    private EditText et_user, et_pass;//用户名,密码编辑框
    private TextView tv_change;//切换到教学信息网

    private Context mContext;//上下文对象

    private LoginPresenter mPresenter;

    private int flag = 0;//0多模式(默认) 1教学信息网

    private String mUser;
    private String mPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initOutData() {
        mContext = LoginActivity.this;

        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
        //获取传递的flag值来决定显示哪一个登陆界面
        //0多模式
        //1教学信息网
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                flag = bundle.getInt("flag", 0);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        //没有toolbar, 不处理
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        //初始化view
        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_user = (EditText) findViewById(R.id.et_user);
        et_pass = (EditText) findViewById(R.id.et_password);
        tv_change = (TextView) findViewById(R.id.tv_change);

        setBackgroundMode(flag);

        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("clicked", "clicked");
                if (flag == 0) {
                    flag = 1;
                    setBackgroundMode(1);
                } else {
                    flag = 0;
                    setBackgroundMode(0);
                }
            }
        });
    }

    private void setBackgroundMode(int mode) {
        if (mode == 0) {
            //改变背景颜色
            ll_root.setBackgroundColor(Color.parseColor("#0ee4c0"));
            btn_login.setText("多模式教学网登陆");
            tv_change.setText("切换到教学信息网登陆");
        } else {
            ll_root.setBackgroundColor(Color.parseColor("#FF02DCF4"));
            btn_login.setText("教学信息网登陆");
            tv_change.setText("切换到多模式教学网登陆");
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListeners() {
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //登陆
            case R.id.btn_login:
                //1.简单判断用户名密码是否合法
                mUser = et_user.getText().toString().trim();
                mPass = et_pass.getText().toString().trim();
                if (validate(mUser, mPass)) {//如果用户名密码合法
                    Log.e("flag", "" + flag);
                    mPresenter.login(mUser, mPass, flag);
                } else {
                    ToastUtils.showToast(mContext, "用户名或者密码不合法,检查后重试");
                }
                break;
        }
    }

    //简单判断用户名密码是否合法
    private boolean validate(String user, String pass) {
        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass)) {
            return true;
        } else {
            return false;
        }
    }

    //显示加载框
    @Override
    public void showProgress() {
        super.showProgressDialog("登陆中", "请稍后");
    }

    //隐藏加载框
    @Override
    public void hideProgress() {
        super.hiddenProgressDialog();
    }

    //登陆失败
    public void onLoginFailure() {
        ToastUtils.showToast(mContext, "登录失败,检查用户名密码重试");
    }


    //多模式教学网登陆成功
    public void onMultiLoginSuccess(List<Course> list) {
        User user = getCurrentUser();
        super.setLoginMode(flag, user);
        Bundle bundle = new Bundle();
        bundle.putSerializable("course", (Serializable) list);
        NavigationManager.getInstance().gotoActivity(mContext, MultiMainActivity.class, bundle);
        LoginActivity.this.finish();
    }

    private User getCurrentUser() {
        User user = new User(mUser, mPass, flag);
        return user;
    }

    //教学信息网登陆成功
    public void onTeachLoginSuccess() {
        User user = getCurrentUser();
        super.setLoginMode(flag, user);//登陆状态为true,登陆模式为教学信息网
        NavigationManager.getInstance().gotoActivity(mContext, TeachMainActivity.class);
        LoginActivity.this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView(this);
    }


}
