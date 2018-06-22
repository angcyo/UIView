package com.angcyo.uidemo.layout.demo

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import android.text.SpannableStringBuilder
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.dialog.UILoading
import com.angcyo.uiview.kotlin.setTextSizeDp
import com.angcyo.uiview.net.Rx
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.Reflect

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/06/22 09:28
 * 修改人员：Robi
 * 修改时间：2018/06/22 09:28
 * 修改备注：
 * Version: 1.0.0
 */
class TelephonyManagerUIDemo : BaseItemUIView() {

    var builder: SpannableStringBuilder? = null

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            @SuppressLint("MissingPermission")
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                if (builder == null) {
                    builder = SpannableStringBuilder().apply {
                        UILoading.flow(mParentILayout).setLoadingTipText("加载中...")

                        Rx.base({
                            val telephonyManager: TelephonyManager = mActivity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

                            val simCount: Int = Reflect.invokeMethod(telephonyManager, "getSimCount") as Int

                            this.append("\nsimSerialNumber:")
                            try {
                                this.append(telephonyManager.simSerialNumber ?: "null")
                            } catch (e: Exception) {
                                this.append(Reflect.logException(e))
                            }

                            this.append("\nsimOperatorName:")
                            this.append(telephonyManager.simOperatorName ?: "null")

                            this.append("\nsimOperator:")
                            this.append(telephonyManager.simOperator ?: "null")

                            this.append("\nsubscriberId:")
                            this.append(telephonyManager.subscriberId ?: "null")

                            this.append("\ngetLine1Number:")
                            this.append(telephonyManager.line1Number ?: "null")

                            this.append("\ngetSimState:")
                            this.append("${telephonyManager.simState}")

                            this.append("\ngetSimCountryIso:")
                            this.append("${telephonyManager.simCountryIso}")

                            this.append("\nnetworkType:")
                            this.append("${telephonyManager.networkType}")

                            this.append("\nnetworkOperator:")
                            this.append("${telephonyManager.networkOperator}")

                            this.append("\nnetworkOperatorName:")
                            this.append("${telephonyManager.networkOperatorName}")

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                this.append("\ndataNetworkType:")
                                this.append("${telephonyManager.dataNetworkType}")

                                this.append("\nvoiceNetworkType:")
                                this.append("${telephonyManager.voiceNetworkType}")
                            }

                            this.append("\nphoneType:")
                            this.append("${telephonyManager.phoneType}")



                            this.append("\n\n")

                            for (i in 0..simCount + 1) {
                                this.append("$i->")
                                this.append("\nsimSerialNumber:")
                                try {
                                    this.append(Reflect.invokeMethod(telephonyManager, "getSimSerialNumber", i)?.toString()
                                            ?: "null")
                                } catch (e: Exception) {
                                    this.append(Reflect.logException(e))
                                }

                                this.append("\nsimOperatorName:")
                                this.append(Reflect.invokeMethod(telephonyManager, "getSimOperatorName", i)?.toString()
                                        ?: "null")

                                this.append("\nsimOperator:")
                                this.append(Reflect.invokeMethod(telephonyManager, "getSimOperator", i)?.toString()
                                        ?: "null")

                                this.append("\ngetSubscriberId:")
                                this.append(Reflect.invokeMethod(telephonyManager, "getSubscriberId", i)?.toString()
                                        ?: "null")

                                this.append("\ngetLine1Number:")
                                this.append(Reflect.invokeMethod(telephonyManager, "getLine1Number", i)?.toString()
                                        ?: "null")

                                this.append("\ngetSimState:")
                                this.append(Reflect.invokeMethod(telephonyManager, "getSimState", i)?.toString()
                                        ?: "null")

                                this.append("\ngetSimCountryIso:")
                                this.append(Reflect.invokeMethod(telephonyManager, "getSimCountryIso", i)?.toString()
                                        ?: "null")

                                this.append("\n")
                            }

                            this.append(Reflect.logObject(telephonyManager, true))
                        }, {
                            UILoading.hide()
                            notifyItemChanged(thisItem)
                        })
                    }
                } else {
                    holder.tv(R.id.text_view).setTextSizeDp(12f)
                    holder.tv(R.id.text_view).text = builder
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_text_view
            }
        })
    }

}