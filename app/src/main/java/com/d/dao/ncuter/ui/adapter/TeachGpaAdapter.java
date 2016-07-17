package com.d.dao.ncuter.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d.dao.ncuter.R;
import com.d.dao.ncuter.bean.TeachGpa;
import com.d.dao.ncuter.bean.TeachScore;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dao on 6/1/16.
 */
public class TeachGpaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<TeachGpa> mList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public TeachGpaAdapter(Context context, List<TeachGpa> list) {
        this.mList = list;
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_fragment_teach_gpa, parent, false);
        AutoUtils.autoSize(view);
        return new TeachGpaHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TeachGpa teachScore = mList.get(position);
        ((TeachGpaHolder)holder).tv_course_type.setText(teachScore.getCourse_type());
        ((TeachGpaHolder)holder).tv_course_name.setText(teachScore.getCourse_name());
        ((TeachGpaHolder)holder).tv_credit.setText(teachScore.getCourse_credit());
        ((TeachGpaHolder)holder).tv_score.setText(teachScore.getScore());
        ((TeachGpaHolder)holder).tv_gpa.setText(teachScore.getGpa());
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<TeachGpa> list) {
        this.mList.clear();
        this.mList.addAll(list);
    }


    class TeachGpaHolder extends RecyclerView.ViewHolder {
        TextView tv_course_type;//课程大类
        TextView tv_course_name;//课程名称
        TextView tv_credit;//学分
        TextView tv_score;//成绩
        TextView tv_gpa;//绩点


        public TeachGpaHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            tv_course_type = (TextView) itemView.findViewById(R.id.tv_course_type);
            tv_course_name = (TextView) itemView.findViewById(R.id.tv_course_name);
            tv_credit = (TextView)itemView.findViewById(R.id.tv_credit);
            tv_score = (TextView) itemView.findViewById(R.id.tv_score);
            tv_gpa = (TextView) itemView.findViewById(R.id.tv_gpa);

        }
    }
}
