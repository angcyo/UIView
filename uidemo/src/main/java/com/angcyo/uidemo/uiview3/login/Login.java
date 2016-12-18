package com.angcyo.uidemo.uiview3.login;

import com.angcyo.uidemo.mvp.presenter.IBasePresenter;
import com.angcyo.uidemo.mvp.view.IBaseView;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/05 10:58
 * 修改人员：Robi
 * 修改时间：2016/12/05 10:58
 * 修改备注：
 * Version: 1.0.0
 */
public interface Login {
    interface ILoginView extends IBaseView {
    }

    interface ILoginPresenter extends IBasePresenter<ILoginView> {
        void startLogin(String authtoken,
                        String authsecret,
                        String authtype,
                        String uniqueid);
    }
}
