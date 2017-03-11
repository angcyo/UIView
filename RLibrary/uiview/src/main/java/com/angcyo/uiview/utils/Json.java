package com.angcyo.uiview.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/01 14:42
 * 修改人员：Robi
 * 修改时间：2016/12/01 14:42
 * 修改备注：
 * Version: 1.0.0
 */
public class Json {
    public static <T> T from(String json, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    public static <T> T from(String json, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    public static String to(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
