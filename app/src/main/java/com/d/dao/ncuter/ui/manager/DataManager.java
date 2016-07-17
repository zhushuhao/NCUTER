package com.d.dao.ncuter.ui.manager;

import android.text.TextUtils;
import android.util.Log;

import com.d.dao.ncuter.model.LoginModel;
import com.d.dao.ncuter.model.MultiCourseSummaryModel;
import com.d.dao.ncuter.model.MultiCourseWareModel;
import com.d.dao.ncuter.ui.MultiCourseSummaryActivity;
import com.d.dao.ncuter.utils.RxUtils;

import java.io.Serializable;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by dao on 7/3/16.
 */
public class DataManager {
    private static DataManager mInstance;

    private LoginModel mLoginModel;
    private MultiCourseSummaryModel mMultiCourseSummaryModel;
    private MultiCourseWareModel mMultiCourseWareModel;


    //单例
    public synchronized static DataManager getInstance() {
        if (mInstance == null) {
            mInstance = new DataManager();
        }
        return mInstance;
    }

    private DataManager() {
        this.mLoginModel = LoginModel.getInstance();
        this.mMultiCourseSummaryModel = MultiCourseSummaryModel.getInstance();
        this.mMultiCourseWareModel = MultiCourseWareModel.getInstance();
    }

    //多模式登陆
    public Observable<String> multi_login(String user, String pass) {
        return this.mLoginModel.multi_login("", user, pass, "")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        if (s.contains("登录失败")) {
                            return "failure";
                        } else {
                            return s.substring(s.length() / 4);
                        }
                    }
                })
                .compose(RxUtils.<String>applyIOToMainThreadSchedulers());
    }

    //教学信息网登陆
    public Observable<String> teach_login(String user, String pass) {
        return this.mLoginModel.teach_login(user, pass)
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        if (TextUtils.isEmpty(s)) {
                            s = "world";
                        }
                        Log.e("s",""+s.length());
                        Log.e("s",s);
                        if (s.contains("累加成绩查询") || s.contains("cjkb.asp") || s.contains("/xs/yzcsq.asp?id=1")) {
                            Log.e("contain", "yes");
                            return "yes";
                        }else if(s.contains("<font size=\"2\">")){
                            Log.e("已经登陆了一个用户","已经登陆了一个用户");
                            return "mid";
                        }
                        else{
                            Log.e("contain", "no");
                            return "no";
                        }
                    }
                })
                .compose(RxUtils.<String>applyIOToMainThreadSchedulers());
    }

    //教学信息网个人信息查询
    public Observable<String> teach_query_person_info(){
        return this.mLoginModel.teach_query_person_info().map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s;
            }
        })
                .compose(RxUtils.<String>applyIOToMainThreadSchedulers());
    }

    //教学信息网考试成绩查询
    public Observable<String> teach_query_score(){
        return this.mLoginModel.teach_query_person_score().map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s;
            }
        }).compose(RxUtils.<String>applyIOToMainThreadSchedulers());
    }
    //教学信息网考试成绩查询
    public Observable<String> teach_query_gpa(){
        return this.mLoginModel.teach_query_gpa().map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s;
            }
        }).compose(RxUtils.<String>applyIOToMainThreadSchedulers());
    }
    //查询课表
    public Observable<String> teach_query_timetable(){
        return this.mLoginModel.teach_query_timetable().map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s;
            }
        }).compose(RxUtils.<String>applyIOToMainThreadSchedulers());
    }
    //查询考试安排
    public Observable<String> teach_query_exam_schedule(){
        return this.mLoginModel.teach_query_exam_schedule().map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                Log.e("datamanager","hello"+s);
                return s;
            }
        }).compose(RxUtils.<String>applyIOToMainThreadSchedulers());
    }

    //获取多模式课程简介信息
    public Observable<String> multi_getCourseSummaryMsg(String address) {
        return this.mMultiCourseSummaryModel.getCourseSummaryMsg(address)
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        String temp = s.substring(s.indexOf("课程简介"));
                        return temp;
                    }
                })
                .compose(RxUtils.<String>applyIOToMainThreadSchedulers());
    }

    //获取课件列表信息

    public Observable<String> multi_getCourseWareList(String addrsss) {
        return this.mMultiCourseWareModel.getCourseWareMsg(addrsss).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                if (s.contains("日期")) {
                    String temp = s.substring(s.indexOf("日期"));
                    return temp;
                } else {
                    return s;
                }
            }
        })
                .compose(RxUtils.<String>applyIOToMainThreadSchedulers());
    }
}
