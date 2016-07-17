package com.d.dao.ncuter.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d.dao.ncuter.R;
import com.d.dao.ncuter.bean.TeachScore;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by dao on 6/1/16.
 * 课表
 */
public class TeachTimetableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<String> mList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    private int[] ids = new int[5];

    public TeachTimetableAdapter(Context context, List<String> list) {
        this.mList = list;
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        int drawableId1 = mContext.getResources().
                getIdentifier("bg_timetable","drawable", mContext.getPackageName());
        int drawableId2 = mContext.getResources().
                getIdentifier("bg_timetable2","drawable", mContext.getPackageName());
        int drawableId3 = mContext.getResources().
                getIdentifier("bg_timetable3","drawable", mContext.getPackageName());
        int drawableId4 = mContext.getResources().
                getIdentifier("bg_timetable4","drawable", mContext.getPackageName());
        int drawableId5 = mContext.getResources().
                getIdentifier("bg_timetable5","drawable", mContext.getPackageName());
        ids[0] = drawableId1;
        ids[1] = drawableId2;
        ids[2] = drawableId3;
        ids[3] = drawableId4;
        ids[4] = drawableId5;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_fragment_teach_timetable, parent, false);
        return new TeachScoreHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((TeachScoreHolder)holder).tv_course.setText(mList.get(position));
        Random random = new Random();
        int index = random.nextInt(5);
        ((TeachScoreHolder)holder).root.setBackgroundResource(ids[index]);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void setList(List<String> list) {
        this.mList.clear();
        this.mList.addAll(list);
    }


    class TeachScoreHolder extends RecyclerView.ViewHolder {
        TextView tv_course;//课程名称
        AutoRelativeLayout root;

        public TeachScoreHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            tv_course = (TextView) itemView.findViewById(R.id.tv_course);
            root = (AutoRelativeLayout) itemView.findViewById(R.id.root);

        }
    }
}
