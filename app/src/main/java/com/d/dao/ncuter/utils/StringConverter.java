package com.d.dao.ncuter.utils;


import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by dao on 7/3/16.
 */
public class StringConverter {
    public static final StringConverter INSTANCE = new StringConverter();

    public String convert(ResponseBody value) throws IOException {
        return value.string();
    }
}
