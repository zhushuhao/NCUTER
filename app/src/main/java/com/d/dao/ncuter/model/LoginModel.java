package com.d.dao.ncuter.model;

import android.util.Log;

import com.d.dao.ncuter.api.BaseUrl;
import com.d.dao.ncuter.api.NCUTER;
import com.d.dao.ncuter.ui.LoginActivity;

import rx.Observable;

/**
 * Created by dao on 7/3/16.
 * 登陆
 */
public class LoginModel {
    private static LoginModel mInstance;

    public synchronized static LoginModel getInstance() {
        if (mInstance == null) {
            mInstance = new LoginModel();
        }
        return mInstance;
    }

    /**
     * 多模式教学网登陆
     *
     * @param campus
     * @param user
     * @param pass
     * @param submitAuth
     * @return
     */
    public Observable<String> multi_login(String campus, String user, String pass, String submitAuth) {
//       Log.e("loginmodel","loginmodel");
        return NCUTER.getInstance().getApiService(BaseUrl.BASE_MULTI_URL).
                multi_login(user, pass, "%BD%F8%C8%EB");
    }

    public Observable<String> teach_login(String user, String pass) {
        return NCUTER.getInstance().getApiService(BaseUrl.BASE_TEACH_URL).
                teach_login("xs", "13101040322", "023311",25,10);
    }

    public Observable<String> teach_query_person_info(){
        return NCUTER.getInstance().getApiService(BaseUrl.BASE_TEACH_URL).
                teach_query_person_info("1");

    }
    public Observable<String> teach_query_person_score(){
        return NCUTER.getInstance().getApiService(BaseUrl.BASE_TEACH_URL).
                teach_query_person_score("5");

    }
}
