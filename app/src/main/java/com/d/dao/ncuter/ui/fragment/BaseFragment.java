package com.d.dao.ncuter.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;

import com.d.dao.ncuter.utils.RxBus;

/**
 * Created by dao on 7/14/16.
 */
public abstract class BaseFragment extends Fragment{

    private ProgressDialog pb;
    protected boolean isVisible;

    private LinearLayout ll_error_root;//错误界面

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //设置fragment可见状态


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
//        Log.e("BaseFragment","setUserVisibleHint");
        super.setUserVisibleHint(isVisibleToUser);
        //读取fragment可见状态
        if(getUserVisibleHint()){
            isVisible = true;
            onVisible();
        }else{
            isVisible = false;
            onInvisible();
        }
    }

    protected void showProgressDialog(){
        pb = ProgressDialog.show(getContext(),"加载中...","",false,false);
    }

    protected void hideProgressDialog(){
        if(pb!=null && pb.isShowing()){
            pb.dismiss();
        }
    }
    protected void onVisible(){
        lazyLoad();
    }
    protected void onInvisible(){

    }
    protected abstract void lazyLoad();


    protected void reLogin(){
        //发送重新登陆
        RxBus.get().post("reLoginTeach", "");
    }
}
