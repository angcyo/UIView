package com.angcyo.uidemo.layout

import com.angcyo.library.utils.L

/**
 * Created by angcyo on 2018/01/25 08:02
 */
object KtDemo {
    fun main() {
        async {
            doSomething("in")
        }

        async{
            //computation.awa
        }
    }

    suspend fun doSomething(foo: String): String {
        L.e("call: doSomething -> $foo")
        return "out"
    }

    fun <T> async(block: suspend () -> T) {
    }
}