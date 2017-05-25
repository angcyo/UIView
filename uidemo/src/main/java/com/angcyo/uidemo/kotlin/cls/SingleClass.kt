package com.angcyo.uidemo.kotlin.cls

import kotlin.reflect.KProperty

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/05/25 17:24
 * 修改人员：Robi
 * 修改时间：2017/05/25 17:24
 * 修改备注：
 * Version: 1.0.0
 */
class SingleClass {
    var num: String? by PDelegate()
//        get() {
//            println("num is : $field")
//
//            return field
//        }

    companion object {
        var instance: SingleClass? = null
            get() {
                if (field == null) {
                    field = SingleClass()
                }
                return field
            }

        fun instance() = instance ?: SingleClass()
    }
}

object SingleClass2 {
    var num = 1

    var instance: SingleClass? = null
        get() {
            if (field == null) {
                field = SingleClass()
            }
            return field
        }

//    var instance2: SingleClass
//        get() {
//            if (false) {
//                field = SingleClass()
//            }
//            return field
//        }
//        set(value) {
//            field = value
//        }
}

class PDelegate<T> {
    var value: T? = null

    operator fun getValue(singleClass: Any?, property: KProperty<*>): T? {
        println("get the value is:${value}")

        SingleClass.instance
        SingleClass2.num
        return value
    }

    operator fun setValue(singleClass: Any?, property: KProperty<*>, s: T) {
        println("set the value is:${s}")
        value = s
    }
}