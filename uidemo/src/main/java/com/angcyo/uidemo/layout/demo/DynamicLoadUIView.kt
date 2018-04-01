package com.angcyo.uidemo.layout.demo

import android.view.Gravity
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.dialog.UIFileSelectorDialog
import com.angcyo.uiview.dialog.UILoading
import com.angcyo.uiview.dynamicload.ProxyStartActivity
import com.angcyo.uiview.dynamicload.RPlugin
import com.angcyo.uiview.dynamicload.internal.DLPluginPackage
import com.angcyo.uiview.kotlin.setInputText
import com.angcyo.uiview.kotlin.string
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.net.RException
import com.angcyo.uiview.net.RSubscriber
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.T_

/**
 * Created by angcyo on 2018/04/01 16:09
 */
class DynamicLoadUIView : BaseItemUIView() {

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString("动态加载演示")
    }


    companion object {
        private var apkPath: String? = null
        private var className: String? = null
    }

    override fun createItems(items: MutableList<SingleItem>) {

        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {

                //开始加载
                holder.click(R.id.start_apk_button) {
                    className = holder.tv(R.id.class_input_view).string()
                    startClass(false) {
                        holder.tv(R.id.result_tip_view).text = "插件启动失败"
                    }
                }
                holder.click(R.id.start_apk_button2) {
                    className = holder.tv(R.id.class_input_view).string()
                    startClass(true) {
                        holder.tv(R.id.result_tip_view).text = "插件启动失败"
                    }
                }

                apkPath?.let {
                    holder.tv(R.id.load_apk_view).text = it
                }

                //选择APK
                holder.click(R.id.selector_apk_button) {
                    startIView(UIFileSelectorDialog(apkPath ?: "") {
                        apkPath = it.absolutePath
                        holder.tv(R.id.load_apk_view).text = apkPath
                    })
                }

                //需要加载的类
                holder.eV(R.id.class_input_view).setInputText("com.cls.manager.iview.MainUIView")

                //预设
                holder.click(R.id.button1) {
                    holder.eV(R.id.class_input_view).setInputText("com.cls.manager.iview.MainUIView")
                }
                holder.click(R.id.button2) {
                    holder.eV(R.id.class_input_view).setInputText("com.angcyo.plugindemo.MainUIView")
                }
                holder.click(R.id.button3) {
                    holder.eV(R.id.class_input_view).setInputText("com.angcyo.plugindemo.MainUIViewId")
                }
                holder.click(R.id.button4) {
                    holder.eV(R.id.class_input_view).setInputText("com.angcyo.plugindemo.ItemTestUIView")
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_dynamic_load_layout
            }

        })
    }

    private fun startClass(proxy: Boolean, onError: () -> Unit) {
        if (apkPath.isNullOrEmpty()) {
            T_.error("请选择APK", Gravity.LEFT)
            return
        }

        if (className.isNullOrEmpty()) {
            T_.error("请输入需要加载的类", Gravity.LEFT)
            return
        }

        RPlugin.loadPlugin(apkPath!!, className!!)
                .subscribe(object : RSubscriber<DLPluginPackage>() {
                    override fun onStart() {
                        super.onStart()
                        UILoading.flow(mParentILayout).setLoadingTipText("插件加载中...")
                    }

                    override fun onSucceed(bean: DLPluginPackage?) {
                        super.onSucceed(bean)
                        if (bean == null) {
                            //holder.tv(R.id.result_tip_view).text = "插件启动失败"
                            onError.invoke()
                        } else {
                            if (proxy) {
                                ProxyStartActivity.start(mActivity, bean.packageName, className)
                            } else {
                                RPlugin.getIView(bean, className!!)?.let {
                                    startIView(it)
                                }
                            }
                        }
                    }

                    override fun onEnd(isError: Boolean, isNoNetwork: Boolean, e: RException?) {
                        super.onEnd(isError, isNoNetwork, e)
                        UILoading.hide()
                    }
                })

    }

}