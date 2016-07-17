package com.d.dao.ncuter.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d.dao.ncuter.R;
import com.d.dao.ncuter.bean.TeachExam;
import com.d.dao.ncuter.bean.TeachScore;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dao on 6/1/16.
 */
public class TeachExamScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<TeachExam> mList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;


    public TeachExamScheduleAdapter(Context context, List<TeachExam> list) {
        this.mList = list;
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_fragment_teach_exam_schedule, parent, false);
        return new TeachExamScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TeachExam teachExam = mList.get(position);

        ((TeachExamScheduleHolder)holder).tv_exam_name.setText(teachExam.getCourse_name());
        ((TeachExamScheduleHolder)holder).tv_exam_site.setText(teachExam.getExam_site());
        ((TeachExamScheduleHolder)holder).tv_exam_date.setText(teachExam.getExam_date());
        ((TeachExamScheduleHolder)holder).tv_exam_time.setText(teachExam.getExam_time());
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void setList(List<TeachExam> list) {
        this.mList.clear();
        this.mList.addAll(list);
    }


    class TeachExamScheduleHolder extends RecyclerView.ViewHolder {
        TextView tv_exam_name;//考试课程名称
        TextView tv_exam_site;//考试地点
        TextView tv_exam_date;//考试日期
        TextView tv_exam_time;//考试时间


        public TeachExamScheduleHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            tv_exam_name = (TextView) itemView.findViewById(R.id.tv_exam_name);
            tv_exam_site = (TextView) itemView.findViewById(R.id.tv_exam_site);
            tv_exam_date = (TextView)itemView.findViewById(R.id.tv_exam_date);
            tv_exam_time = (TextView) itemView.findViewById(R.id.tv_exam_time);

        }
    }
}
