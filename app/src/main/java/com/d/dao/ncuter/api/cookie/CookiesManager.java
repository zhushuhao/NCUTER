package com.d.dao.ncuter.api.cookie;

import android.util.Log;

import com.d.dao.ncuter.AppApplication;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 自动管理 cookie
 * Created by dao on 7/6/16.
 */
public class CookiesManager implements CookieJar {
    private final PersistentCookieStore cookieStore =
            new PersistentCookieStore(AppApplication.getContext());
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
//                Log.e("cookies",item.value());
            }
        }else{
            Log.e("cookies","no cookie");
//
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
//        Log.e("cookies",cookies.get(0).value());
        return cookies;
    }
}
