package com.d.dao.ncuter.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.d.dao.ncuter.R;
import com.d.dao.ncuter.bean.TeachScore;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dao on 6/1/16.
 */
public class TeachScoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<TeachScore> mList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public TeachScoreAdapter(Context context, List<TeachScore> list) {
        this.mList = list;
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_fragment_teach_score, parent, false);
        return new TeachScoreHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TeachScore teachScore = mList.get(position);

        ((TeachScoreHolder)holder).tv_class_name.setText(teachScore.getClass_name());
        ((TeachScoreHolder)holder).tv_teacher_name.setText(teachScore.getTeacher_name());
        ((TeachScoreHolder)holder).tv_score_normal.setText(teachScore.getScore_normal());
        ((TeachScoreHolder)holder).tv_score_final.setText(teachScore.getScore_final());
        ((TeachScoreHolder)holder).tv_score_overall.setText(teachScore.getScore_overall());
        ((TeachScoreHolder)holder).tv_percent_normal.setText(teachScore.getPercent_normal());
        ((TeachScoreHolder)holder).tv_exam_nature.setText(teachScore.getExam_nature());
        ((TeachScoreHolder)holder).tv_credit.setText(teachScore.getCredit()+" 学分");
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void setList(List<TeachScore> list) {
        this.mList.clear();
        this.mList.addAll(list);
    }


    class TeachScoreHolder extends RecyclerView.ViewHolder {
        TextView tv_class_name;//课程名称
        TextView tv_teacher_name;//教师名称
        TextView tv_score_normal;
        TextView tv_score_final;
        TextView tv_percent_normal;
        TextView tv_score_overall;
        TextView tv_exam_nature;
        TextView tv_credit;

        public TeachScoreHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            tv_class_name = (TextView) itemView.findViewById(R.id.tv_course_name);
            tv_teacher_name = (TextView) itemView.findViewById(R.id.tv_teacher_name);
            tv_score_normal = (TextView)itemView.findViewById(R.id.tv_score_normal);
            tv_score_final = (TextView) itemView.findViewById(R.id.tv_score_final);
            tv_percent_normal = (TextView) itemView.findViewById(R.id.tv_percent_normal);
            tv_score_overall = (TextView)itemView.findViewById(R.id.tv_score_overall);
            tv_exam_nature = (TextView) itemView.findViewById(R.id.tv_exam_nature);
            tv_credit = (TextView) itemView.findViewById(R.id.tv_credit);
        }
    }
}
