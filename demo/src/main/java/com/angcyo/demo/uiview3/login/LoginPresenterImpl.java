package com.angcyo.demo.uiview3.login;

import com.angcyo.demo.mvp.presenter.BasePresenter;
import com.angcyo.demo.uiview3.bean.Bean;
import com.angcyo.demo.uiview3.service.UserService;
import com.angcyo.uiview.net.RRetrofit;
import com.angcyo.uiview.net.TransformUtils;
import com.angcyo.uiview.net.base.BaseSubscriber;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/05 11:08
 * 修改人员：Robi
 * 修改时间：2016/12/05 11:08
 * 修改备注：
 * Version: 1.0.0
 */
public class LoginPresenterImpl extends BasePresenter<Login.ILoginView> implements Login.ILoginPresenter {


    @Override
    public void startLogin(String authtoken, String authsecret, String authtype, String uniqueid) {
        RRetrofit.create(UserService.class)
                .login(authtoken, authsecret, authtype, uniqueid)
                .compose(TransformUtils.<Bean<String>>defaultSchedulers())
                .subscribe(new BaseSubscriber<Bean<String>>() {
                    @Override
                    public void onStart() {
                        mBaseView.onStartLoad();
                    }

                    @Override
                    public void onResult(boolean success, Bean<String> bean, String msg) {
                        if (success) {
                            if (bean.getC() == 1) {
                                mBaseView.onSuccess();
                            } else {
                                mBaseView.onError(bean.getC(), bean.getM());
                            }
                        } else {
                            mBaseView.onError(1, msg);
                        }
                    }
                });
    }
}
