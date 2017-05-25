package com.angcyo.uidemo.kotlin.cls

import com.angcyo.library.utils.L
import com.angcyo.uidemo.kotlin.ktinterface_fun
import com.angcyo.uidemo.layout.DemoListUIView2
import kotlin.properties.Delegates

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/05/24 11:23
 * 修改人员：Robi
 * 修改时间：2017/05/24 11:23
 * 修改备注：
 * Version: 1.0.0
 */
class ktclass {

    val a = "TAG"

    var b: Int? = 2
        set(value) {
            if (value != null)
                field = value + 100
            else
                field = value
        }
    val c: Int
        get() = 11

    var d = false

    fun test() {
//        fun1()
//        fun1_1(1)
//
//        fun2("1")
//        fun3(2, "2")
//        fun4(3L, 3.0, "3")
//
//        val strings = arrayOf("1", "2", "3")
//        println(fun2_1_1(strings))
//
//        val ints = arrayOf(1, "2", "3")
//        println(fun2_1_1(ints))
//
//        for (i in 1..5) {
//            println(i)
//        }
//
//        //
//        val datacls = dataclass("na", 1)
//        datacls.name = ""

        //fun_test_ranges()

        fun_test_list()

        for (c in "angcyo\"robi") {
            p("$c  ${c.toInt()} \$c")
        }

        val str = """
for (c in "angcyo\"robi") {
    p("${'$'}${'\\'}${'"'}c")
}"""
        p(str)


        ktinterface_fun()

        fun_test_when()

        p(b as Int)
        b = 3
        p(b as Int)
        p(c)

        val testClass = DemoListUIView2.TestClass()
    }

    fun fun_test_when() {
        val i = 1
        when (i) {
            1 -> println(1)
            2 -> println(2)
        }
    }

    fun fun_test_list() {
        val list1 = listOf(1, 2, 3, 4)
        //list1.filter { it > 2 }.map { it * 2 }.forEach { p(it) }
        //等价于
        list1.filter { i -> i > 2 }.map { i -> i * 2 }.forEach { i -> p(i) }
    }

    fun fun_test_ranges() {
        //
        for (i in 1..10) {
            p(i)
        }  // 闭区间: 包括100
        p("----------------------")
        for (i in 1 until 10) {
            p(i)
        } // 半开区间: 不包括100
        p("----------------------")
        for (x in 2..10 step 2) {
            p(x)
        }
        p("----------------------")
        for (x in 10 downTo 1) {
            p(x)
        }
        p("----------------------")
        for (i in 1..10) {
            p(i)
        }
        p("----------------------")
        for (i in 2..10) {
            p(i)
        }
        p("----------------------")
    }

    fun p(s: Any) = println(s)

    fun fun1(): Unit {

    }

    fun fun1_1(i: Int): Unit {

    }

    fun fun2(x: Any): Unit {
        L.e(x.javaClass.simpleName)
        if (x is Int) {

        }
    }

    fun fun2_1_1(x: Any): String {
        if (x is Array<*>) {
            for (v in x) {
                println(v)
            }
            return "is array"
        }
        return "not array"
    }

    fun fun2_1_2(x: Any = "") {

    }

    fun fun2_1(x: Any) = if (x is String) "Yes" else "No"

//    fun fun2_2(x: Any): String =
//            when (x) {
//                1 -> ""
//                else -> ""
//            }
//    //return ""

    fun fun2_2(x: Any, vararg y: Any) = when (x) { 1 -> ""
        else -> ""
    }

    fun fun3(x: Any, y: Any): Unit {
        L.e("${x.javaClass.simpleName} ${y.javaClass.simpleName} ")
    }

    fun fun4(x: Any, y: Any, z: Any): Unit {
        L.e("${x.javaClass.simpleName} ${y.javaClass.simpleName} ${z.javaClass.simpleName} $a - ${a} end")
    }

    inner class ktsubclass {
        var bar: Int by Delegates.notNull<Int>()
        fun test() {
            println("sub class test")

        }
    }

    fun fun4(x: Any) = println()
    fun fun4_1(x: Any): Int = x as Int
}