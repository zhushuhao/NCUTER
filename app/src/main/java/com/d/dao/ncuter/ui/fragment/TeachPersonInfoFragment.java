package com.d.dao.ncuter.ui.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d.dao.ncuter.R;
import com.d.dao.ncuter.bean.PersonInfo;
import com.d.dao.ncuter.presenter.impl.TeachFragmentPersonInfoPresenter;
import com.d.dao.ncuter.ui.view.BaseView;
import com.d.dao.ncuter.utils.RxBus;
import com.d.dao.ncuter.utils.ToastUtils;
import com.zhy.autolayout.AutoRelativeLayout;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

//个人信息
public class TeachPersonInfoFragment extends BaseFragment implements BaseView {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;//实例化时传递的参数

    private String TAG = getClass().getSimpleName();

    private boolean isPrepared = false;//标记已经初始化完成

    private boolean hasLoadOnce = false;//标记已经在进入界面时加载过一次


    private View view;

    private TeachFragmentPersonInfoPresenter mPresenter;

    //错误界面
    private LinearLayout ll_error;
    private TextView tv_error;
    private TextView tv_load_once_more;

    private AutoRelativeLayout rl_main;

    private TextView tv_student_id, tv_name, tv_id_number, tv_entry_class, tv_sex,
            tv_entry_year, tv_season, tv_default_class, tv_major, tv_major_field;

    private TextView tv_id_number_tag;
    private Context mContext;

    private Observable<String> addOb;


    public static TeachPersonInfoFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        TeachPersonInfoFragment pageFragment = new TeachPersonInfoFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        initOutData();//页面加载前初始化
        registerRxBus();//RxBus注册

    }

    private void registerRxBus() {
        addOb = RxBus.get()
                .register("reLoadPersonInfo", String.class);
        addOb.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        //重新加载个人信息
                        reLoadPersonInfo();
                    }
                });
    }

    //第一此进入时加载
    @Override
    protected void lazyLoad() {
        if (isPrepared && isVisible && !hasLoadOnce) {
            loadPersonInfo();
        }
    }

    private void loadPersonInfo() {
        mPresenter.queryInfo(false);
    }

    private void reLoadPersonInfo() {
        mPresenter.queryInfo(true);
    }

    private void initOutData() {
        mContext = getContext();
        mPresenter = new TeachFragmentPersonInfoPresenter();
        mPresenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        if (view == null) {
            Log.e(TAG, "view null");
            view = inflater.inflate(R.layout.fragment_person_info, container, false);

            ll_error = (LinearLayout) view.findViewById(R.id.error_root);
            tv_error = (TextView) view.findViewById(R.id.tv_error);
            tv_load_once_more = (TextView) view.findViewById(R.id.tv_load_once_more);

            rl_main = (AutoRelativeLayout) view.findViewById(R.id.rl_main);

            rl_main.setVisibility(View.GONE);
            ll_error.setVisibility(View.GONE);

            tv_student_id = (TextView) view.findViewById(R.id.tv_student_id);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_id_number = (TextView) view.findViewById(R.id.tv_id_number);
            tv_entry_class = (TextView) view.findViewById(R.id.tv_entry_class);
            tv_sex = (TextView) view.findViewById(R.id.tv_sex);
            tv_entry_year = (TextView) view.findViewById(R.id.tv_entry_year);
            tv_season = (TextView) view.findViewById(R.id.tv_season);
            tv_default_class = (TextView) view.findViewById(R.id.tv_default_class);
            tv_major = (TextView) view.findViewById(R.id.tv_major);
            tv_major_field = (TextView) view.findViewById(R.id.tv_major_field);

            tv_id_number_tag = (TextView) view.findViewById(R.id.tv_id_number_tag);

            tv_load_once_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ll_error.getVisibility() == View.VISIBLE) {
                        loadPersonInfo();
                    }
                }
            });

            isPrepared = true;
            lazyLoad();
        } else {
            Log.e(TAG, "view not null");
        }
        return view;
    }


    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        mPresenter.detachView(this);
        RxBus.get().unregister("reLoadPersonInfo", addOb);

    }

    //    @Override
//    public void onResume() {
//        Log.e(TAG, "onResume");
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        Log.e(TAG, "onPause");
//        super.onPause();
//    }
//
//    @Override
//    public void onDestroyView() {
//        Log.e(TAG, "onDestroyView");
//
//        super.onDestroyView();
//    }
//
//    @Override
//    public void onDetach() {
//        Log.e(TAG, "onDetach");
//        super.onDetach();
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        Log.e(TAG, "onAttach");
//        super.onAttach(context);
//    }
//
//    @Override
//    protected void onInvisible() {
//        Log.e(TAG, "onInvisible");
//        super.onInvisible();
//    }
//
    @Override
    protected void onVisible() {
        Log.e(TAG, "onvisible");
        super.onVisible();
    }


    @Override
    public void showProgress() {
        super.showProgressDialog();
    }

    @Override
    public void hideProgress() {
        super.hideProgressDialog();
    }

    /**
     * 　　* onConfigurationChanged
     * 　　* the package:android.content.res.Configuration.
     * 　　* @param newConfig, The new device configuration.
     * 　　* 当设备配置信息有改动（比如屏幕方向的改变，实体键盘的推开或合上等）时，
     * 　　* 并且如果此时有activity正在运行，系统会调用这个函数。
     * 　　* 注意：onConfigurationChanged只会监测应用程序在AnroidMainifest.xml中通过
     * 　　* android:configChanges="xxxx"指定的配置类型的改动；
     * 　　* 而对于其他配置的更改，则系统会onDestroy()当前Activity，然后重启一个新的Activity实例。
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 检测屏幕的方向：纵向或横向
        if (this.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            //当前为横屏， 在此处添加额外的处理代码
        } else if (this.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT) {
            //当前为竖屏， 在此处添加额外的处理代码
        }
        //检测实体键盘的状态：推出或者合上
        if (newConfig.hardKeyboardHidden
                == Configuration.HARDKEYBOARDHIDDEN_NO) {
            //实体键盘处于推出状态，在此处添加额外的处理代码
        } else if (newConfig.hardKeyboardHidden
                == Configuration.HARDKEYBOARDHIDDEN_YES) {
            //实体键盘处于合上状态，在此处添加额外的处理代码
        }
    }

    public void onGetPersonInfoSuccess(PersonInfo personInfo) {
        Log.e(TAG, "onGetPersonInfoSuccess:" + "获取个人信息成功");
        Log.e(TAG, "getPersonInfoSuccess:" + personInfo.toString());
        if (personInfo != null) {
            setErrorVisible(false);
            hasLoadOnce = true;
            tv_student_id.setText(personInfo.getStudent_id());
            tv_id_number.setText(personInfo.getId_number());
            tv_major_field.setText(personInfo.getMajor_field());
            tv_major.setText(personInfo.getMajor());
            tv_default_class.setText(personInfo.getDefault_class());
            tv_name.setText(personInfo.getName());
            tv_season.setText(personInfo.getSeason());
            tv_sex.setText(personInfo.getSex());
            tv_entry_class.setText(personInfo.getEntry_class());
            tv_entry_year.setText(personInfo.getEntry_year());
        } else {
            setErrorVisible(true);
            ToastUtils.showToast(mContext, "获取个人信息失败");
            tv_error.setText("获取个人信息失败");
        }
    }
    public void onGetPersonInfoFailure() {
        Log.e(TAG, "onGetPersonInfoFailure:" + "获取个人信息失败");
        ToastUtils.showToast(mContext, "获取个人信息失败");
        setErrorVisible(true);
        tv_error.setText("获取个人信息失败");
    }

    public void reLogin() {
        super.reLogin();
    }
    //设置错误界面可见
    private void setErrorVisible(boolean b) {
        if (b) {
            rl_main.setVisibility(View.GONE);
            ll_error.setVisibility(View.VISIBLE);
        } else {
            rl_main.setVisibility(View.VISIBLE);
            ll_error.setVisibility(View.GONE);
        }
    }
}
