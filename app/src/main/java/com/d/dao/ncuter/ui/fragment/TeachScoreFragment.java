package com.d.dao.ncuter.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.d.dao.ncuter.bean.TeachScore;
import com.d.dao.ncuter.presenter.impl.TeachFragmentQueryScorePresenter;
import com.d.dao.ncuter.ui.adapter.TeachScoreAdapter;
import com.d.dao.ncuter.ui.view.BaseView;
import com.d.dao.ncuter.utils.RxBus;
import com.d.dao.ncuter.utils.ToastUtils;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

//查询成绩
public class TeachScoreFragment extends BaseFragment implements BaseView {

    private String TAG = getClass().getSimpleName();
    private boolean isPrepared = false;//标记已经初始化完成

    private boolean hasLoadOnce = false;//标记已经在进入界面时加载过一次

    private View view;

    private Context mContext;

    private TeachFragmentQueryScorePresenter mPresenter;

    private RecyclerView mRecyclerView;
    private EasyBorderDividerItemDecoration dataDecoration;//

    private List<TeachScore> mList = new ArrayList<>();
    private TeachScoreAdapter mAdapter;

    private Observable<String> addOb;


    private AutoRelativeLayout rl_main;
    //错误界面
    private LinearLayout ll_error;
    private TextView tv_error;
    private TextView tv_load_once_more;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initOutData();
        registerRxBus();
    }

    private void registerRxBus() {
        addOb = RxBus.get()
                .register("reLoadScore", String.class);
        addOb.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        reQueryScore();//教学信息网重新但呢轮毂
                    }
                });
    }

    private void initOutData() {
        mContext = getContext();
        mPresenter = new TeachFragmentQueryScorePresenter();
        mPresenter.attachView(this);

    }

    @Override
    protected void lazyLoad() {
        if (isPrepared && isVisible && !hasLoadOnce) {
            queryScore();
        }
    }

    private void reQueryScore() {
        mPresenter.queryScore(true);
    }

    private void queryScore() {
        Log.e(TAG, "查询成绩");
        mPresenter.queryScore(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_query_score, container, false);

            ll_error = (LinearLayout) view.findViewById(R.id.error_root);
            tv_error = (TextView) view.findViewById(R.id.tv_error);
            tv_load_once_more = (TextView) view.findViewById(R.id.tv_load_once_more);

            rl_main = (AutoRelativeLayout) view.findViewById(R.id.rl_main);

            rl_main.setVisibility(View.GONE);
            ll_error.setVisibility(View.GONE);

            mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
            mAdapter = new TeachScoreAdapter(mContext, mList);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            //EasyRecyclerView
//            this.dataDecoration = new EasyBorderDividerItemDecoration(
//                    20,20);
//            mRecyclerView.addItemDecoration(dataDecoration);
            isPrepared = true;
            lazyLoad();


            tv_load_once_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ll_error.getVisibility() == View.VISIBLE) {
                        queryScore();
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
        RxBus.get().unregister("reLoadScore", addOb);

    }

    //获取考试成绩成功
    public void onQueryScoreSuccess(List<TeachScore> list) {
        if (list != null) {
            if (list.size() > 0) {
                setErrorVisible(false);
                hasLoadOnce = true;
                this.mList = list;
                mAdapter.setList(mList);
                mAdapter.notifyDataSetChanged();
            } else {
                setErrorVisible(true);
                tv_error.setText("当前没有成绩");
                ToastUtils.showToast(mContext, "当前没有成绩");
            }
        } else {
            setErrorVisible(true);
            tv_error.setText("获取成绩失败");
            ToastUtils.showToast(mContext, "获取失败");
        }

    }

    //获取考试成绩失败
    public void onQueryScoreFailure() {
        setErrorVisible(true);
        tv_error.setText("获取成绩失败");
        ToastUtils.showToast(mContext, "获取失败");
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

    //重新登陆
    public void reLogin() {
        super.reLogin();
    }
}
