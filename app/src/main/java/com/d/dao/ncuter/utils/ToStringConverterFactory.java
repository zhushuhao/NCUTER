package com.d.dao.ncuter.utils;

//import com.squareup.okhttp.MediaType;
//import com.squareup.okhttp.RequestBody;
//import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
//
//import retrofit.Converter;
//import retrofit.Retrofit;


/**
 * Created by Kamil on 2016-01-13.
Corrected version, It works properly with the latest retrofit
retrofit:2.0.0-beta3'

 */
public class ToStringConverterFactory extends Converter.Factory {
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain");


    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (String.class.equals(type)) {
            return new Converter<ResponseBody, String>() {
                @Override
                public String convert(ResponseBody value) throws IOException {
                    return value.string();
                }
            };
        }
        return null;
    }

    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

        if (String.class.equals(type)) {
            return new Converter<String, RequestBody>() {
                @Override
                public RequestBody convert(String value) throws IOException {
                    return RequestBody.create(MEDIA_TYPE, value);
                }
            };
        }
        return null;
    }
}