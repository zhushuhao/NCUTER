package com.d.dao.ncuter.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.d.dao.ncuter.R;
import com.d.dao.ncuter.bean.CourseSummary;
import com.d.dao.ncuter.bean.CourseWare;
import com.d.dao.ncuter.utils.ToastUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dao on 7/6/16.
 */
public class CourseWareAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<CourseWare> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener mOnItemClickListener;


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public CourseWareAdapter(Context context, List<CourseWare> list) {
        this.mContext = context;
        this.mList = list;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_course_ware, parent, false);
        return new CourseWareHolder(view);
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
        ((CourseWareHolder) holder).tv_name.setText(mList.get(position).getName());
        ((CourseWareHolder) holder).tv_size.setText(mList.get(position).getSize());
        ((CourseWareHolder) holder).tv_time.setText(mList.get(position).getTime());
        ((CourseWareHolder) holder).iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(mContext,"开始下载");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class CourseWareHolder extends RecyclerView.ViewHolder {

        private TextView tv_name,tv_size,tv_time;
        private ImageView iv_download;

        public CourseWareHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_size = (TextView) itemView.findViewById(R.id.tv_size);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            iv_download = (ImageView) itemView.findViewById(R.id.iv_download);
        }
    }

    public void setList(List<CourseWare> list) {
        this.mList = list;
    }

}
