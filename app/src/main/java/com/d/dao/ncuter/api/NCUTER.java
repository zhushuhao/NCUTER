package com.d.dao.ncuter.api;

import android.util.Log;

import com.d.dao.ncuter.api.cookie.CookiesManager;
import com.d.dao.ncuter.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.squareup.okhttp.Interceptor;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.Response;
//import com.squareup.okhttp.ResponseBody;

import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//import retrofit.Converter;
//import retrofit.GsonConverterFactory;
//import retrofit.Retrofit;
//import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by dao on 7/3/16.
 */
public class NCUTER {
    private static NCUTER mInstance;
    private ApiService mApiService;
    private OkHttpClient okHttpClient;
    private Gson gson;

    private NCUTER() {
        gson = new GsonBuilder().setDateFormat(BaseUrl.DATA_FORMAT).create();

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        okHttpClient = httpBuilder.readTimeout(8, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                .header("mon_icampus", "yes")
                                .method(original.method(), original.body())
                                .build();
                        Response response = chain.proceed(request);
                        return response;
                    }
                })
                .cookieJar(new CookiesManager())
                .followRedirects(true)
                .connectTimeout(15, TimeUnit.SECONDS).writeTimeout(15, TimeUnit.SECONDS) //设置超时
                .build();
    }

    //单例
    public static NCUTER getInstance() {
        if (mInstance == null) {
            mInstance = new NCUTER();
        }
        return mInstance;
    }

    //由于每一次的baseUrl都不一样,所以要放在这里处理retrofit,
    //如果是同一个 baseUrl,直接放在上面和okHttpClient一起初始化就可以了
    //
    public ApiService getApiService(String baseUrl) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addCallAdapterFactory(
                        RxJavaCallAdapterFactory.create())
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        this.mApiService = retrofit.create(ApiService.class);
        return mApiService;
    }

    /**
     * 自定义Converter实现RequestBody到String的转换
     */
    public static class StringConverter implements Converter<ResponseBody, String> {

        public static final StringConverter INSTANCE = new StringConverter();



        @Override
        public String convert(ResponseBody value) throws IOException {
            InputStream in = value.byteStream();
            String result = StringUtils.inputStream2String(in,"gbk");
            return result;
        }
    }

    /**
     * 用于向Retrofit提供StringConverter
     */
    public static class StringConverterFactory extends Converter.Factory {

        public static final StringConverterFactory INSTANCE = new StringConverterFactory();

        public static StringConverterFactory create() {
            return INSTANCE;
        }

        // 我们只关实现从ResponseBody 到 String 的转换，所以其它方法可不覆盖
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            if (type == String.class) {
                return StringConverter.INSTANCE;
            }
            //其它类型我们不处理，返回null就行
            return null;
        }
    }


}
