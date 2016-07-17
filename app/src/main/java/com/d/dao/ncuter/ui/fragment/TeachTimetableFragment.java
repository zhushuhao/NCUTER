package com.d.dao.ncuter.ui.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.camnter.easyrecyclerview.widget.decorator.EasyBorderDividerItemDecoration;
import com.d.dao.ncuter.R;
import com.d.dao.ncuter.bean.PersonInfo;
import com.d.dao.ncuter.bean.TeachGpa;
import com.d.dao.ncuter.presenter.impl.TeachFragmentPersonInfoPresenter;
import com.d.dao.ncuter.presenter.impl.TeachFragmentTimetablePresenter;
import com.d.dao.ncuter.ui.adapter.TeachGpaAdapter;
import com.d.dao.ncuter.ui.adapter.TeachTimetableAdapter;
import com.d.dao.ncuter.ui.view.BaseView;
import com.d.dao.ncuter.utils.RxBus;
import com.d.dao.ncuter.utils.ToastUtils;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

//个人课表
public class TeachTimetableFragment extends BaseFragment implements BaseView {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;//实例化时传递的参数

    private String TAG = getClass().getSimpleName();

    private boolean isPrepared = false;//标记已经初始化完成

    private boolean hasLoadOnce = false;//标记已经在进入界面时加载过一次


    private View view;

    private TeachFragmentTimetablePresenter mPresenter;

    private AutoRelativeLayout rl_main;


    private Context mContext;

    private RecyclerView mRecyclerView;
    private EasyBorderDividerItemDecoration dataDecoration;//

    private List<String> mList = new ArrayList<>();
    private TeachTimetableAdapter mAdapter;

    private Observable<String> addOb;

    //错误界面
    private LinearLayout ll_error;
    private TextView tv_error;
    private TextView tv_load_once_more;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        initOutData();
        registerRxBus();
    }

    private void registerRxBus() {
        addOb = RxBus.get()
                .register("reLoadTimetable", String.class);
        addOb.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        reLoadTimetable();//教学信息网重新但呢轮毂
                    }
                });
    }

    //第一此进入时加载
    @Override
    protected void lazyLoad() {
        if (isPrepared && isVisible && !hasLoadOnce) {
            loadTimetable();
        }
    }

    private void loadTimetable() {
        mPresenter.queryTimetable(false);
    }

    private void reLoadTimetable() {
        mPresenter.queryTimetable(true);
    }

    private void initOutData() {
        mContext = getContext();
        mPresenter = new TeachFragmentTimetablePresenter();
        mPresenter.attachView(this);

        mList.add("周一的课");
        mList.add("周一的课");
        mList.add("周一的课");
        mList.add("周一的课");
        mList.add("周一的课");
        mList.add("周一的课");

        mList.add("周二的课");
        mList.add("周二的课");
        mList.add("周二的课");
        mList.add("周二的课");
        mList.add("周二的课");
        mList.add("周二的课");

        mList.add("周三的课");
        mList.add("周三的课");
        mList.add("周三的课");
        mList.add("周三的课");
        mList.add("周三的课");
        mList.add("周三的课");

        mList.add("周四的课");
        mList.add("周四的课");
        mList.add("周四的课");
        mList.add("周四的课");
        mList.add("周四的课");
        mList.add("周四的课");


        mList.add("周五的课");
        mList.add("周五的课");
        mList.add("周五的课");
        mList.add("周五的课");
        mList.add("周五的课");
        mList.add("周五的课");

        mList.add("周六的课");
        mList.add("周六的课");
        mList.add("周六的课");
        mList.add("周六的课");
        mList.add("周六的课");
        mList.add("周六的课");

        mList.add("周日的课");
        mList.add("周日的课");
        mList.add("周日的课");
        mList.add("周日的课");
        mList.add("周日的课");
        mList.add("周日的课");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        if (view == null) {
            Log.e(TAG, "view null");
            view = inflater.inflate(R.layout.fragment_timetable, container, false);

            ll_error = (LinearLayout) view.findViewById(R.id.error_root);
            tv_error = (TextView) view.findViewById(R.id.tv_error);

            tv_load_once_more = (TextView) view.findViewById(R.id.tv_load_once_more);
            rl_main = (AutoRelativeLayout) view.findViewById(R.id.rl_main);

            rl_main.setVisibility(View.GONE);
            ll_error.setVisibility(View.GONE);


//          isPrepared = true;
//          lazyLoad();

            mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
            mAdapter = new TeachTimetableAdapter(mContext, mList);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 7));
            mRecyclerView.setHasFixedSize(true);
            //EasyRecyclerView
            this.dataDecoration = new EasyBorderDividerItemDecoration(
                    2, 0);
            mRecyclerView.addItemDecoration(dataDecoration);


            tv_load_once_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ll_error.getVisibility() == View.VISIBLE) {
                        loadTimetable();
                    }
                }
            });

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
        RxBus.get().unregister("reLoadTimetable", addOb);

    }


    @Override
    public void showProgress() {
        super.showProgressDialog();
    }

    @Override
    public void hideProgress() {
        super.hideProgressDialog();
    }

    public void onGetTimetableSuccess() {
        setErrorVisible(false);
        hasLoadOnce = true;
    }

    public void onGetTimetableFailure() {
        ToastUtils.showToast(mContext, "获取课表失败");
        setErrorVisible(true);
        tv_error.setText("获取课表失败");
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
