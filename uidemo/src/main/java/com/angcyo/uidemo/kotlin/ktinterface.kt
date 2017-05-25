package com.angcyo.uidemo.kotlin

import com.angcyo.uidemo.layout.DemoListUIView2

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/05/24 11:24
 * 修改人员：Robi
 * 修改时间：2017/05/24 11:24
 * 修改备注：
 * Version: 1.0.0
 */

val i = 1;

interface ktinterface {
    var i: Int
    val l: Int
        get() = 3
    val k: Int
        get() = 3
}

fun ktinterface_fun() {
    println("ktinterface_fun")
}

//函数扩展
fun DemoListUIView2.fun_test() {
    println("expand test")
}