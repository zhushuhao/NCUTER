package com.d.dao.ncuter.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.camnter.easyrecyclerview.widget.decorator.EasyBorderDividerItemDecoration;
import com.d.dao.ncuter.R;
import com.d.dao.ncuter.bean.CourseSummary;
import com.d.dao.ncuter.bean.CourseWare;
import com.d.dao.ncuter.presenter.impl.MultiCourseSummaryPresenter;
import com.d.dao.ncuter.presenter.impl.MultiCourseWarePresenter;
import com.d.dao.ncuter.ui.adapter.CourseSummaryAdapter;
import com.d.dao.ncuter.ui.adapter.CourseWareAdapter;
import com.d.dao.ncuter.ui.view.BaseView;
import com.d.dao.ncuter.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

//课件页面
public class MultiCourseWareActivity extends BaseToolbarActivity implements BaseView {

    private RecyclerView mRecyclerView;
    private CourseWareAdapter mAdapter;
    private List<CourseWare> mList = new ArrayList<>();

    private Context mContext;

    private CourseSummary mCourseSummary;

    private MultiCourseWarePresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_multi_course_ware;
    }

    @Override
    protected void initOutData() {
        mContext = MultiCourseWareActivity.this;
        mPresenter = new MultiCourseWarePresenter();
        mPresenter.attachView(this);

        //获取从MultiCourseSummaryActivity传递的数据
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mCourseSummary = (CourseSummary) bundle.getSerializable("msg");
            }
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        super.setHomeTrue();
        if (mCourseSummary != null) {
            super.setTitles(mCourseSummary.getTitle());
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new EasyBorderDividerItemDecoration(10, 10));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CourseWareAdapter(mContext, mList);
        mAdapter.setOnItemClickListener(new CourseWareAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //  下载课件
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        if (mCourseSummary != null) {
            Log.e("address", mCourseSummary.getAddress());
            mPresenter.getCurseWareList(mCourseSummary.getAddress());
        }
    }

    @Override
    protected void initListeners() {

    }

    @Override
    public void showProgress() {
        super.showProgressDialog("稍后", "正在加载课件列表...");
    }

    @Override
    public void hideProgress() {
        super.hiddenProgressDialog();
    }

    public void onGetCourseWare(List<CourseWare> list) {
        if (list == null || list.size() <= 0) {

        } else {
            this.mList = list;
            //更新界面
            mAdapter.setList(mList);
            mAdapter.notifyDataSetChanged();
        }
    }
    public void onError(){
        ToastUtils.showToast(mContext,"获取课件列表失败");
    }
    @Override
    protected void onDestroy() {
        mPresenter.detachView(this);
        super.onDestroy();
    }
}
