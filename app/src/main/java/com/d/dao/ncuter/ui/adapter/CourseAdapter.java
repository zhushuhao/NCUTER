package com.d.dao.ncuter.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d.dao.ncuter.R;
import com.d.dao.ncuter.bean.Course;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dao on 7/6/16.
 * 课程
 */
public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private List<Course> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener mOnItemClickListener;


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public CourseAdapter(Context context, List<Course> list) {
        this.mContext = context;
        this.mList = list;
        this.mInflater = LayoutInflater.from(mContext);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_course, parent, false);
        AutoUtils.autoSize(view);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,final int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
        ((NewsHolder) holder).tv_title.setText(mList.get(position).getCourse_name());
        ((NewsHolder) holder).tv_source.setText(mList.get(position).getCourse_code());
        ((NewsHolder) holder).tv_time.setText(mList.get(position).getTeacher_name());
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        TextView tv_source;
        TextView tv_time;

        public NewsHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_source = (TextView) itemView.findViewById(R.id.tv_source);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }

    public void setList(List<Course> list){this.mList = list;}
}
