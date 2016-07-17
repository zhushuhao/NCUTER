package com.d.dao.ncuter.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.d.dao.ncuter.bean.TeachExam;
import com.d.dao.ncuter.bean.TeachScore;
import com.d.dao.ncuter.presenter.impl.TeachFragmentExamSchedulePresenter;
import com.d.dao.ncuter.presenter.impl.TeachFragmentQueryScorePresenter;
import com.d.dao.ncuter.ui.BaseToolbarActivity;
import com.d.dao.ncuter.ui.adapter.TeachExamScheduleAdapter;
import com.d.dao.ncuter.ui.adapter.TeachScoreAdapter;
import com.d.dao.ncuter.ui.view.BaseView;
import com.d.dao.ncuter.utils.CommonUtil;
import com.d.dao.ncuter.utils.RxBus;
import com.d.dao.ncuter.utils.ToastUtils;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

//考试安排
public class TeachExamScheduleFragment extends BaseFragment implements BaseView{

    private String TAG = getClass().getSimpleName();
    private boolean isPrepared = false;//标记已经初始化完成

    private boolean hasLoadOnce = false;//标记已经在进入界面时加载过一次

    private View view;

    private Context mContext;

    private TeachFragmentExamSchedulePresenter mPresenter;

    private RecyclerView mRecyclerView;
    private EasyBorderDividerItemDecoration dataDecoration;//

    private List<TeachExam> mList = new ArrayList<>();
    private TeachExamScheduleAdapter mAdapter;


    private Observable<String> addOb;

    //错误界面
    private LinearLayout ll_error;
    private TextView tv_error;
    private TextView tv_load_once_more;

    private AutoRelativeLayout rl_main;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initOutData();
        registerRxBus();
    }

    private void registerRxBus(){
        addOb = RxBus.get()
                .register("reLoadExamSchedule", String.class);
        addOb.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        reQueryExamSchedule();
                    }
                });
    }
    private void initOutData() {
        mContext = getContext();
        mPresenter = new TeachFragmentExamSchedulePresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void lazyLoad() {
        if (isPrepared && isVisible && !hasLoadOnce) {
            queryExamSchedule();
        }
    }

    private void reQueryExamSchedule(){
        mPresenter.queryExamSchedule(true);

    }
    private void queryExamSchedule() {
        Log.e(TAG,"查询成绩");
        mPresenter.queryExamSchedule(false);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_exam_schedule, container, false);

            ll_error = (LinearLayout) view.findViewById(R.id.error_root);
            tv_error = (TextView) view.findViewById(R.id.tv_error);
            tv_load_once_more = (TextView) view.findViewById(R.id.tv_load_once_more);

            rl_main = (AutoRelativeLayout) view.findViewById(R.id.rl_main);

            rl_main.setVisibility(View.GONE);
            ll_error.setVisibility(View.GONE);

            mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
            mAdapter = new TeachExamScheduleAdapter(mContext, mList);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            //EasyRecyclerView
            this.dataDecoration = new EasyBorderDividerItemDecoration(
                    20,20);
            mRecyclerView.addItemDecoration(dataDecoration);
            isPrepared = true;
            lazyLoad();


            tv_load_once_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(CommonUtil.isVisible(ll_error)){
                        queryExamSchedule();
                    }
                }
            });
        }
        return view;
    }


    @Override
    public void showProgress() {
        super.showProgressDialog();
    }

    @Override
    public void hideProgress() {
        super.hideProgressDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView(this);
        RxBus.get().unregister("reLoadTimetable", addOb);
    }

    //获取考试成绩成功
    public void onQueryExamScheduleSuccess(List<TeachExam> list){
        if(list!=null){
            if(list.size()>0){
                setErrorVisible(false);
                hasLoadOnce = true;
                this.mList = list;
                mAdapter.setList(mList);
                mAdapter.notifyDataSetChanged();
            }else{
                setErrorVisible(true);
                ToastUtils.showToast(mContext,"当前没有考试安排");
            }
        }else{
            setErrorVisible(true);
            ToastUtils.showToast(mContext,"获取考试安排失败");
        }

    }
    //获取考试成绩失败
    public void onQueryExamScheduleFailure(){
        setErrorVisible(true);
        tv_error.setText("获取考试安排失败");
        ToastUtils.showToast(mContext,"获取考试安排失败");
    }

    //设置错误界面可见
    private void setErrorVisible(boolean b){
        if(b){
            rl_main.setVisibility(View.GONE);
            ll_error.setVisibility(View.VISIBLE);
        }else{
            rl_main.setVisibility(View.VISIBLE);
            ll_error.setVisibility(View.GONE);
        }
    }

    //重新登陆
    public void reLogin(){
        super.reLogin();
    }


}
