package com.d.dao.ncuter.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.d.dao.ncuter.ui.fragment.TeachExamScheduleFragment;
import com.d.dao.ncuter.ui.fragment.TeachGpaFragment;
import com.d.dao.ncuter.ui.fragment.TeachScoreFragment;
import com.d.dao.ncuter.ui.fragment.TeachTimetableFragment;
import com.d.dao.ncuter.ui.fragment.TeachPersonInfoFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dao on 5/31/16.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {


    private List<Fragment> mList = new ArrayList<>();
    private String tabTitles[] = new String[]{"个人信息", "个人课表", "查询成绩", "成绩绩点", "考试安排"};
    private Context context;

    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        mList.add(new TeachPersonInfoFragment());
        mList.add(new TeachTimetableFragment());
        mList.add(new TeachScoreFragment());
        mList.add(new TeachGpaFragment());
        mList.add(new TeachExamScheduleFragment());

    }

    @Override
    public Fragment getItem(int position) {
//        return TeachPersonInfoFragment.newInstance(position + 1); 传参写法
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }


}
