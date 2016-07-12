package com.d.dao.ncuter.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.d.dao.ncuter.R;


/**
 * Created by dao on 5/29/16.
 */
public abstract class BaseToolbarActivity extends BaseAppCompatActivity {

    protected Toolbar mToolbar;
    protected AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        this.setSupportActionBar(this.mToolbar);
    }

    protected void setHomeTrue() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void setTitles(String title){
        setTitle(title);
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
