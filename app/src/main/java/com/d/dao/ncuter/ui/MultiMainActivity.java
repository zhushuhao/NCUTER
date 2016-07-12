package com.d.dao.ncuter.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anupcowkur.reservoir.ReservoirGetCallback;
import com.camnter.easyrecyclerview.widget.decorator.EasyBorderDividerItemDecoration;
import com.d.dao.ncuter.R;
import com.d.dao.ncuter.bean.Course;
import com.d.dao.ncuter.bean.User;
import com.d.dao.ncuter.ui.adapter.CourseAdapter;
import com.d.dao.ncuter.ui.manager.NavigationManager;
import com.d.dao.ncuter.ui.view.BaseView;
import com.d.dao.ncuter.utils.CommonUtil;
import com.d.dao.ncuter.utils.ReservoirUtils;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//多模式教学网主页面
public class MultiMainActivity extends BaseToolbarActivity implements BaseView {


    private List<Course> mList = new ArrayList<>();//传递的课程信息　
    private Context mContext;

    private SwipeRefreshLayout mSwipe;
    private RecyclerView mRecyclerView;
    private EasyBorderDividerItemDecoration dataDecoration;//
    private CourseAdapter mAdapter;

    private LinearLayout ll_error_root;//错误界面
    private TextView tv_loadOnceMore;//重新加载
    private TextView tv_error;//课程为空或者加载出错提示文字

    @Override
    protected int getLayoutId() {
        return R.layout.activity_multi_main;
    }

    @Override
    protected void initOutData() {
        mContext = MultiMainActivity.this;

        Intent intent = getIntent();
        if(intent!=null){
            Bundle bundle = intent.getExtras();
            if (bundle != null) {//不为空
                mList = (List<Course>) bundle.getSerializable("course");
                Log.e("initOutData----mList",mList.toString());
            }
        }

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        ll_error_root = (LinearLayout) findViewById(R.id.error_root);
        tv_loadOnceMore = (TextView) findViewById(R.id.tv_load_once_more);
        tv_error = (TextView) findViewById(R.id.tv_error);

        tv_loadOnceMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonUtil.isVisible(ll_error_root)) {//判断是否可见
                    //重新加载
                    loadOnceMore();
                }
            }
        });

        mSwipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mSwipe.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新时加载
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //设置adapter
        if (mList == null || mList.size() <= 0) {
            mList = new ArrayList<>();
            getMultiCourse();//获取课程列表
        }
        mAdapter = new CourseAdapter(mContext, mList);

        mAdapter.setOnItemClickListener(new CourseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //点击事件
                //进入课程简介页面
                gotoCourseSummaryActivity(position);
//                Log.e("position","position"+position);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //EasyRecyclerView
        this.dataDecoration = new EasyBorderDividerItemDecoration(20, 20);
        //添加分割线
        mRecyclerView.addItemDecoration(dataDecoration);


    }

    //进入课程简介界面
    private void gotoCourseSummaryActivity(int position) {

        //得到点击的条目信息
        Course course = mList.get(position);
        Bundle bundle = new Bundle();

        if (course != null) {
            String address = course.getCourse_address();//获取请求地址
            String course_code=  course.getCourse_code();
            bundle.putString("course_address",address);
            bundle.putString("course_code",course_code);
        }else{
            bundle.putString("course_address","");
            bundle.putString("course_code","");
        }
        NavigationManager.getInstance().gotoActivity(mContext,MultiCourseSummaryActivity.class,bundle);


    }

    //重新加载
    private void loadOnceMore() {

    }

    @Override
    protected void initData() {
        if (mList == null || mList.size() == 0) {
            ll_error_root.setVisibility(View.VISIBLE);
            tv_error.setText("没有查询到课程");
        } else {
            ll_error_root.setVisibility(View.GONE);
        }
    }
    @Override
    protected void initListeners() {

    }

    //异步本地获取课程名称
    private void getMultiCourse() {
        Type resultType = new TypeToken<List<Course>>() {
        }.getType();
        ReservoirUtils.getInstance().get("course", resultType, new ReservoirGetCallback<List<Course>>() {
            @Override
            public void onSuccess(List<Course> list) {
                mList = list;
                Log.e("list", mList.toString());
                if (mList.size() > 0) {
                    ll_error_root.setVisibility(View.GONE);
                    Log.e("更新数据","本地更新数据");
                    mAdapter.setList(mList);
                    mAdapter.notifyDataSetChanged();

                }
            }
            @Override
            public void onFailure(Exception e) {
                if (mSwipe.isRefreshing()) {
                    mSwipe.setRefreshing(false);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_change_login) {// 切换登陆
            if(super.isLogined()){
                User user = super.getUserInfo();
                if(user!=null){
                    int type = user.getType();
                    if(type==0){
                        gotoLoginActivity(1);
                    }else{
                        gotoLoginActivity(0);
                    }
                }else{
                    gotoLoginActivity(0);
                }
            }else{
                gotoLoginActivity(0);
            }
        }else if(id==R.id.action_download){//查看下载

        }

        return true;
    }
    //转到 LoginActivity
    private void gotoLoginActivity(int type){
        Bundle bundle = new Bundle();
        bundle.putInt("flag",type);
        NavigationManager.getInstance().gotoActivity(mContext,LoginActivity.class,bundle);

    }

    @Override
    public void showProgress() {
        super.showProgressDialog("登陆中", "请稍后");
    }

    @Override
    public void hideProgress() {
        super.hiddenProgressDialog();
    }
}
