package com.d.dao.ncuter.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d.dao.ncuter.R;
import com.d.dao.ncuter.bean.CourseSummary;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dao on 7/6/16.
 */
public class CourseSummaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<CourseSummary> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener mOnItemClickListener;


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public CourseSummaryAdapter(Context context, List<CourseSummary> list) {
        this.mContext = context;
        this.mList = list;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_course_summary, parent, false);
        return new CourseSummaryHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
        ((CourseSummaryHolder) holder).tv_title.setText(mList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class CourseSummaryHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;

        public CourseSummaryHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

    public void setList(List<CourseSummary> list) {
        this.mList = list;
    }

}
