package com.d.dao.ncuter.utils;

//import com.squareup.okhttp.MediaType;
//import com.squareup.okhttp.RequestBody;
//import com.squareup.okhttp.ResponseBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
//
//import retrofit.Converter;
//import retrofit.Retrofit;

/**
 * Created by dao on 7/3/16.
 */
    public class StringConverterFactory extends Converter.Factory {
//        private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain");
//        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
//            if (String.class.equals(type)) {
//                return new Converter<ResponseBody, String>() {
//                    @Override
//                    public String convert(ResponseBody value) throws IOException {
//                        return value.string();
//                    }
//                };
//            }
//            return null;
//        }
//
//
//        public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
//
//            if (String.class.equals(type)) {
//                return new Converter<String, RequestBody>() {
//                    @Override
//                    public RequestBody convert(String value) throws IOException {
//                        return RequestBody.create(MEDIA_TYPE, value);
//                    }
//                };
//            }
//            return null;
//        }

    public static final StringConverterFactory INSTANCE = new StringConverterFactory();
    public static StringConverterFactory create() {
        return INSTANCE;
    }
    // 我们只关实现从ResponseBody 到 String 的转换，所以其它方法可不覆盖
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == String.class) {
            return (Converter<ResponseBody, ?>) StringConverter.INSTANCE;
        }
        //其它类型我们不处理，返回null就行
        return null;
    }
    }

