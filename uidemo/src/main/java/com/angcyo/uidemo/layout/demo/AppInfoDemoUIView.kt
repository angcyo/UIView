package com.angcyo.uidemo.layout.demo

import com.angcyo.github.utilcode.utils.ClipboardUtils
import com.angcyo.github.utilcode.utils.CmdUtil
import com.angcyo.github.utilcode.utils.CmdUtil.AppInfo
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseRecyclerUIView
import com.angcyo.uiview.design.StickLayout2
import com.angcyo.uiview.dialog.UIFileSelectorDialog
import com.angcyo.uiview.net.RFunc
import com.angcyo.uiview.net.RSubscriber
import com.angcyo.uiview.net.Rx
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.recycler.adapter.RModelAdapter
import com.angcyo.uiview.utils.RUtils
import com.angcyo.uiview.utils.Tip

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/01/23 17:09
 * 修改人员：Robi
 * 修改时间：2018/01/23 17:09
 * 修改备注：
 * Version: 1.0.0
 */
class AppInfoDemoUIView : BaseRecyclerUIView<AppInfo>() {

    override fun getTitleString(): String {
        return "包名获取"
    }

    override fun getRecyclerRootLayoutId(): Int {
        return R.layout.view_app_info_layout
    }

    override fun createAdapter(): RExBaseAdapter<String, AppInfo, String> {
        return object : RExBaseAdapter<String, AppInfo, String>(mActivity) {
            override fun getItemLayoutId(viewType: Int): Int {
                return R.layout.item_app_info
            }

            override fun onBindCommonView(holder: RBaseViewHolder, position: Int, bean: AppInfo) {
                super.onBindCommonView(holder, position, bean)
                holder.gIV(R.id.image_view).apply {
                    reset()
                    setImageDrawable(bean.appIcon)
                }

                val packageName = bean.packageName

                holder.tv(R.id.app_name_view).text = "${bean.appName}_n:${bean.versionName}_c:${bean.versionCode}"
                holder.tv(R.id.app_pka_name_view).text = packageName

                holder.click(R.id.open_button) {
                    val error = RUtils.startApp(packageName)
                    if (error.isNotEmpty()) {
                        Tip.tip("启动失败\n$error")
                    }
                }

                holder.click(R.id.copy_button) {
                    ClipboardUtils.copyText(packageName)
                    Tip.ok("包名\n$packageName\n已复制")
                }

                holder.click(R.id.attr_button) {
                    RUtils.openAppDetailView(mActivity, packageName)
                }

                holder.clickItem {
                    //holder.clickView(R.id.copy_button)
                    setSelectorPosition(position)
                }
            }

            override fun onBindModelView(model: Int, isSelector: Boolean, holder: RBaseViewHolder, position: Int, bean: AppInfo?) {
                super.onBindModelView(model, isSelector, holder, position, bean)
                if (isSelector) {
                    holder.visible(R.id.control_layout)
                } else {
                    holder.gone(R.id.control_layout)
                }
            }

        }.apply {
            model = RModelAdapter.MODEL_MULTI
        }
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        v<StickLayout2>(R.id.stick_layout).setScrollTarget(mRecyclerView)

        mViewHolder.gone(R.id.item_root_layout)

        click(R.id.select_button) {
            //选择APK文件
            startIView(UIFileSelectorDialog { file ->
                //CmdUtil.get
            })
        }
    }

    override fun onUILoadData(page: Int) {
        super.onUILoadData(page)
        add(Rx.base(object : RFunc<List<AppInfo>>() {
            override fun onFuncCall(): List<AppInfo> {
                return CmdUtil.getAllAppInfo(mActivity)
            }

        }, object : RSubscriber<List<AppInfo>>() {
            override fun onSucceed(bean: List<AppInfo>) {
                super.onSucceed(bean)
                onUILoadFinish(bean)
            }
        }))
    }

}