package com.angcyo.uidemo.uiview3.login;

import com.angcyo.uidemo.mvp.presenter.BasePresenter;
import com.angcyo.uidemo.uiview3.bean.Bean;
import com.angcyo.uidemo.uiview3.service.UserService;
import com.angcyo.uiview.net.RException;
import com.angcyo.uiview.net.RRetrofit;
import com.angcyo.uiview.net.RSubscriber;
import com.angcyo.uiview.net.TransformUtils;

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
                .subscribe(new RSubscriber<Bean<String>>() {
                    @Override
                    public void onStart() {
                        mBaseView.onStartLoad();
                    }

                    @Override
                    public void onSucceed(Bean<String> bean) {
                        super.onSucceed(bean);
                        if (bean.getC() == 1) {
                            mBaseView.onSuccess();
                        } else {
                            mBaseView.onError(bean.getC(), bean.getM());
                        }
                    }

                    @Override
                    public void onEnd(boolean isError, boolean isNoNetwork, RException e) {
                        super.onEnd(isError, isNoNetwork, e);
                        if (isError) {
                            mBaseView.onError(1, e.getMsg());
                        }
                    }
                });
    }
}
