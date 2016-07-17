package com.d.dao.ncuter.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.d.dao.ncuter.R;


/**
 * Created by dao on 5/29/16.
 */
public abstract class BaseToolbarActivity extends BaseAppCompatActivity {

    protected Toolbar mToolbar;
    protected AppBarLayout mAppBarLayout;
    protected TextView tv_left_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        tv_left_title = (TextView) findViewById(R.id.tv_left_title);
        this.setSupportActionBar(this.mToolbar);
    }
     //设置状态栏颜色
    protected void setStatusColor(int color){
        //大于等于5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){//大于等于4.4
           mAppBarLayout.setBackgroundColor(color);
        }else{
            //do nothing
        }
    }
    protected void hideToolbar(){
        if(mToolbar!=null){
            mToolbar.setVisibility(View.GONE);
        }
    }

    protected void setToolbarColor(int color){
        mAppBarLayout.setBackgroundColor(color);
        mToolbar.setBackgroundColor(color);
    }

    protected void showToolbar(){
        if(mToolbar!=null){
            mToolbar.setVisibility(View.VISIBLE);
        }
    }
    protected void setHomeTrue() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    //设置左边标题
    protected void setTitles(String title){
        tv_left_title.setText(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            //返回
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
