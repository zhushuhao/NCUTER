package com.d.dao.ncuter.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.camnter.easyrecyclerview.widget.decorator.EasyBorderDividerItemDecoration;
import com.d.dao.ncuter.R;
import com.d.dao.ncuter.bean.CourseSummary;
import com.d.dao.ncuter.presenter.impl.MultiCourseSummaryPresenter;
import com.d.dao.ncuter.ui.adapter.CourseSummaryAdapter;
import com.d.dao.ncuter.ui.manager.NavigationManager;
import com.d.dao.ncuter.ui.view.BaseView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程简介
 */
public class MultiCourseSummaryActivity extends BaseToolbarActivity implements BaseView {
    private RecyclerView mRecyclerView;
    private CourseSummaryAdapter mAdapter;
    private List<CourseSummary> mList = new ArrayList<>();
    private static String mAddress = "";//课程地址
    private static String mCode = "";//课程编号

    private Context mContext;
    private MultiCourseSummaryPresenter mPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_multi_course_summary;
    }

    @Override
    protected void initOutData() {
        mContext = MultiCourseSummaryActivity.this;

        mPresenter = new MultiCourseSummaryPresenter();
        mPresenter.attachView(this);

        //获取传递的需要请求的页面地址
        Bundle bundle = getIntent().getExtras();
        mAddress = bundle.getString("course_address");
        mCode = bundle.getString("course_code");

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.setHomeTrue();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.addItemDecoration(new EasyBorderDividerItemDecoration(10, 10));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CourseSummaryAdapter(mContext, mList);
        mAdapter.setOnItemClickListener(new CourseSummaryAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //去下一级页面,共有12个
                gotoNextActivity(position);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        //请求数据
        //判断数据是否为空,不为空去请求数据
        if (!TextUtils.isEmpty(mAddress) && !TextUtils.isEmpty("mCode")) {
            String temp = mAddress.substring(mAddress.indexOf("eclass"), mAddress.lastIndexOf("/")).trim();
            Log.e("address", temp);
            String tem = temp.substring(temp.indexOf("/") + 1);
            mPresenter.getCurseSummaryMessage(tem, mCode);
        }
    }

    @Override
    protected void initListeners() {

    }

    @Override
    public void showProgress() {
        //
    }

    @Override
    public void hideProgress() {

    }

    //获取到课程简介信息
    public void onGetMessageSuccess(List<CourseSummary> list) {
        if(list==null||list.size()<=0){

        }else{
            this.mList = list;
            //更新界面
            mAdapter.setList(mList);
            mAdapter.notifyDataSetChanged();
        }

    }

    //去下一级页面
    private void gotoNextActivity(int position) {
     switch (position){
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 5://前往课件页面
             Bundle bundle = new Bundle();
             bundle.putSerializable("msg",mList.get(position));
             NavigationManager.getInstance().gotoActivity(mContext,MultiCourseWareActivity.class,bundle);
             break;

     }
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView(this);
        super.onDestroy();
    }
}
