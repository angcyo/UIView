package com.angcyo.library.utils;

import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/12 18:46
 * 修改人员：Robi
 * 修改时间：2016/12/12 18:46
 * 修改备注：
 * Version: 1.0.0
 */
public class Http {
    public static final String BASE_IMAGE_URL = "";

    /**
     * 安全的去掉字符串的最后一个字符
     */
    public static String safe(StringBuilder stringBuilder) {
        return stringBuilder.substring(0, Math.max(0, stringBuilder.length() - 1));
    }

    public static <T> String safe(List<T> list) {
        if (list == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (T bean : list) {
            builder.append(bean);
            builder.append(",");
        }

        return safe(builder);
    }
}
