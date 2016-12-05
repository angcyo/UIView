package com.angcyo.demo.uiview3.service;

import com.angcyo.demo.uiview3.bean.Bean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/01 16:04
 * 修改人员：Robi
 * 修改时间：2016/12/01 16:04
 * 修改备注：
 * Version: 1.0.0
 */
public interface UserService {
    @POST("1/login")
    Observable<Bean<String>> login(@Query("authtoken") String authtoken,
                                   @Query("authsecret") String authsecret,
                                   @Query("authtype") String authtype,
                                   @Query("uniqueid") String uniqueid);
}
