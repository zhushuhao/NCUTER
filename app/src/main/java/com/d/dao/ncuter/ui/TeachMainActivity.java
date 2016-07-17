package com.d.dao.ncuter.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d.dao.ncuter.R;
import com.d.dao.ncuter.api.cookie.PersistentCookieStore;
import com.d.dao.ncuter.bean.PersonInfo;
import com.d.dao.ncuter.bean.User;
import com.d.dao.ncuter.presenter.impl.TeachMainPresenter;
import com.d.dao.ncuter.ui.adapter.SimpleFragmentPagerAdapter;
import com.d.dao.ncuter.ui.manager.NavigationManager;
import com.d.dao.ncuter.ui.view.BaseView;
import com.d.dao.ncuter.utils.RxBus;
import com.d.dao.ncuter.utils.ToastUtils;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class TeachMainActivity extends BaseToolbarActivity implements BaseView {


    private String TAG = getClass().getSimpleName();
    private Context mContext;
    private TeachMainPresenter mPresenter;

    private SimpleFragmentPagerAdapter pagerAdapter;

    private ViewPager viewPager;

    private TabLayout tabLayout;

    private Observable<String> addOb;


    private static int lastSelected = 0;

    private FabSpeedDial fabSpeedDial;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_teach_main;
    }


    private int[] colors = {Color.parseColor("#ea650c"), Color.parseColor("#4ab3a6"), Color.parseColor("#" +
            "FF5DB912"), Color.parseColor("#e10d7e"), Color.DKGRAY};

    @Override
    protected void initOutData() {
        mContext = TeachMainActivity.this;
        mPresenter = new TeachMainPresenter();
        mPresenter.attachView(this);

        //注册RxBus
        registerRxBus();

    }


    private void registerRxBus() {
        addOb = RxBus.get()
                .register("reLoginTeach", String.class);
        addOb.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        reLoginTeach();//教学信息网重新但呢轮毂
                    }
                });
    }

    private void changeToolbarAndStatusBarAndFabColor(int color) {
        setStatusColor(color);
        setToolbarColor(color);
        tabLayout.setBackgroundColor(color);

        int count = fabSpeedDial.getChildCount();
        Log.e("counter", "" + count);
//        for(int i=0;i<count;i++){
        LinearLayout group = (LinearLayout) fabSpeedDial.getChildAt(0);

        FloatingActionButton fab = (FloatingActionButton) fabSpeedDial.getChildAt(1);
        fab.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN));
        fab.setBackgroundTintList(ColorStateList.valueOf(color));


        int index = group.getChildCount();
        Log.e("index", "" + index);
        if (index > 0) {
            for (int j = 0; j < index; j++) {
                ViewGroup group1 = (ViewGroup) group.getChildAt(j);

                //左边文字
                CardView cardView = (CardView) group1.getChildAt(0);
                TextView textView = (TextView) cardView.getChildAt(0);
                textView.setTextColor(colors[lastSelected]);

                //右边图片
                FloatingActionButton button = (FloatingActionButton) group1.getChildAt(1);
                button.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));

            }

        }
//        }

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        fabSpeedDial = (FabSpeedDial) findViewById(R.id.fab_speed_dial);

        changeToolbarAndStatusBarAndFabColor(colors[0]);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position != lastSelected) {
                    lastSelected = position;
                    changeToolbarAndStatusBarAndFabColor(colors[position]);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {

                return true;
            }

            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
//                int index = navigationMenu.getVisibleItems().size();
//                Log.e("menu index",""+index);
//                for(int i=0;i<index;i++){
//                    MenuItem item = navigationMenu.getItem(i);
//                    item.getIcon().setColorFilter(new PorterDuffColorFilter(colors[lastSelected], PorterDuff.Mode.SRC_IN));
//                }
//                changeToolbarAndStatusBarAndFabColor(colors[lastSelected]);

                return true;
            }

            @Override
            public void onMenuClosed() {
                super.onMenuClosed();
            }

            @Override
            public void onMenuItemAdded() {
                super.onMenuItemAdded();
                changeToolbarAndStatusBarAndFabColor(colors[lastSelected]);
            }
        });
    }

    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    //重新登陆
    private void reLoginTeach() {
        User user = getUserInfo(1);
        if (user == null) {//登陆的用户为空
            super.gotoLogin(1);//转到教学信息网登陆界面
            finish();
        } else {
            mPresenter.reLogin(user.getUser(), user.getPass());
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListeners() {

    }

    @Override
    public void showProgress() {
        super.showProgressDialog("wait", "loading");
    }

    @Override
    public void hideProgress() {
        super.hiddenProgressDialog();
    }


    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        mPresenter.detachView(this);
        PersistentCookieStore store = new PersistentCookieStore(mContext);
        store.removeAll();
        RxBus.get().unregister("TeachMainTag", addOb);
    }

    String[] tags = {"reLoadPersonInfo", "reLoadTimetable", "reLoadScore", "reQueryGpa", "reLoadExamSchedule"};

    //重新登陆成功
    public void onReLoginSuccess() {
        RxBus.get().post(tags[lastSelected], "");
    }

    //重新登陆失败
    public void onReLoginFailure() {
        ToastUtils.showToast(mContext, "登陆失败,网站也许正在维护,若更改了密码请注销后重新登陆");
    }
}
