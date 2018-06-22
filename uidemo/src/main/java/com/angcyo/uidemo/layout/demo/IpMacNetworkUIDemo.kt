package com.angcyo.uidemo.layout.demo

import android.content.Context
import android.net.wifi.WifiManager
import com.angcyo.github.utilcode.utils.NetworkUtils
import com.angcyo.github.utilcode.utils.SpannableStringUtils
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.container.UIParam
import com.angcyo.uiview.kotlin.setTextSizeDp
import com.angcyo.uiview.net.base.Network
import com.angcyo.uiview.receiver.NetworkStateReceiver
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.NetworkUtil
import com.angcyo.uiview.utils.Reflect
import java.net.NetworkInterface
import java.net.SocketException


/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/05/11 15:28
 * 修改人员：Robi
 * 修改时间：2018/05/11 15:28
 * 修改备注：
 * Version: 1.0.0
 */
class IpMacNetworkUIDemo : BaseItemUIView() {
    private val networkStateListener = NetworkStateReceiver.NetworkStateListener { from, to ->
        dataBean.from = from
        dataBean.to = to
        notifyItemChanged(0)
    }

    val dataBean = DataBean()

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemBean: Item?) {
//                holder.tv(R.id.state_text_view).text = "from:${dataBean.from} to:${dataBean.to}"
//                holder.tv(R.id.ip_text_view).text = "MobileIP:${Network.getMobileIP()} \nWifiIp:${Network.getWifiIp(mActivity)}"
//                holder.tv(R.id.mac_text_view).text = "${NetworkUtils.getNetworkOperatorName()} \n${NetworkUtil.getActiveMacAddress(mActivity)}"
                val builder = SpannableStringUtils.getBuilder("")
                builder.append("当前网络状态:\n")
                builder.append("from:")
                builder.append(dataBean.from.toString())
                builder.append(" to:")
                builder.append(dataBean.to.toString())
                builder.append("\n")

                builder.append("\nIP:\n")
                builder.append("MobileIP:")
                builder.append(Network.getMobileIP())
                builder.append("\nWifiIp:")
                builder.append(Network.getWifiIp(mActivity))

                builder.append("\nGsmIP:")
                builder.append(getGsmIp())
                builder.append("\nWifiIp:")
                builder.append(getWifiIp())

                builder.append("\n\nMAC:\n")
                builder.append(NetworkUtils.getNetworkOperatorName())
                builder.append("\n")
                builder.append(NetworkUtil.getActiveMacAddress(mActivity))
                builder.append("\n")

//                if (dataBean.to == NetworkUtils.NetworkType.NETWORK_UNKNOWN) {
                    builder.append(Reflect.logObject(NetworkStateReceiver.sNetworkWrapper.network, false))
                    builder.append(Reflect.logObject(NetworkStateReceiver.sNetworkWrapper.networkCapabilities, false))
                    builder.append(Reflect.logObject(NetworkStateReceiver.sNetworkWrapper.linkProperties, false))
//                }

                holder.tv(R.id.text_view).setTextSizeDp(12f)
                holder.tv(R.id.text_view).text = builder.create()
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_ip_mac_network
            }
        })
    }

    override fun onViewLoad() {
        super.onViewLoad()
        NetworkStateReceiver.addNetworkStateListener(networkStateListener)
    }

    override fun onViewUnload(uiParam: UIParam) {
        super.onViewUnload(uiParam)
        NetworkStateReceiver.removeNetworkStateListener(networkStateListener)
    }

    data class DataBean(var from: NetworkUtils.NetworkType = NetworkUtils.NetworkType.NETWORK_UNKNOWN,
                        var to: NetworkUtils.NetworkType = NetworkStateReceiver.getNetType(),
                        var ip: String = "",
                        var mac: String = ""
    )

    fun getWifiIp(): String {
        val wifiManager = mActivity.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (wifiManager.isWifiEnabled && wifiManager.wifiState == WifiManager.WIFI_STATE_ENABLED) {
            val wifiInfo = wifiManager.connectionInfo
            if (wifiInfo != null) {
                val ipAddress = wifiInfo.ipAddress
                return if (ipAddress == 0) "" else
                    (ipAddress and 0xff).toString() + "." + (ipAddress shr 8 and 0xff) + "." + (ipAddress shr 16 and 0xff) + "." + (ipAddress shr 24 and 0xff)
            }
        }
        return "unknown"
    }

    fun getEthernetIp(): String {
//        //获取 以太网ip 的方法, 需要源码环境编译, 文章后面有下载地址;
//        val connectivityManager = mActivity
//                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val properties = connectivityManager
//                .getLinkProperties(ConnectivityManager.TYPE_ETHERNET)
//        if (properties != null) {
//            val ipString = properties.getAddresses().toString()
//
//            val pattern = Pattern.compile("\\d+.\\d+.\\d+.\\d+")
//            val matcher = pattern.matcher(ipString)
//            if (matcher.find()) {
//                return matcher.group()
//            }
//        }
        return "unknown"
    }

    fun getGsmIp(): String {
        //获取 3G网络 ip的方法
        try {
            val en = NetworkInterface
                    .getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddr = intf
                        .getInetAddresses()
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString()
                    }
                }
            }
        } catch (ex: SocketException) {
            ex.printStackTrace()
        }

        return "unknown"
    }
}