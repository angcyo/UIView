package com.angcyo.uidemo.layout

import com.angcyo.library.utils.L
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock

/**
 * Created by angcyo on 2018/01/25 08:02
 */
object KtDemo {

    fun main() {
        threadDemo1()
        threadDemo2()
    }

    private fun threadDemo2() {
        val status = AtomicInteger(1)
        var logs = mutableListOf<String>()

        val runnable1 = {
            var i = 0
            while (i < 10) {
                if (status.get() == 1) {
                    logs.add(Thread.currentThread().name + " -> A")
                    i++
                    status.set(2)
                }
            }
        }
        val runnable2 = {
            var i = 0
            while (i < 10) {
                if (status.get() == 2) {
                    logs.add(Thread.currentThread().name + " -> B")
                    i++
                    status.set(3)
                }
            }
        }
        val runnable3 = {
            var i = 0
            while (i < 10) {
                if (status.get() == 3) {
                    logs.add(Thread.currentThread().name + " -> C")
                    i++
                    status.set(1)
                }
            }
        }
        val thread1 = Thread(runnable1, "线程1")
        val thread2 = Thread(runnable2, "线程2")
        val thread3 = Thread(runnable3, "线程3")
        val startTime = System.currentTimeMillis()
        thread1.start()
        thread2.start()
        thread3.start()
        thread1.join()
        thread2.join()
        thread3.join()
        L.e("----------方法2 $logs 耗时:${System.currentTimeMillis() - startTime}ms")
    }

    private fun threadDemo1() {
//        val object1 = Object()
//        val object2 = Object()
//        val object3 = Object()

//        val runnable1 = {
//            for (i in 0 until 10) {
//                synchronized(object1) { object1.wait() }
//                L.e(Thread.currentThread().name + " -> A")
//                synchronized(object2) { object2.notify() }
//            }
//        }
//        val runnable2 = {
//            for (i in 0 until 10) {
//                synchronized(object2) { object2.wait() }
//                L.e(Thread.currentThread().name + " -> B")
//                synchronized(object3) { object3.notify() }
//            }
//        }
//        val runnable3 = {
//            for (i in 0 until 10) {
//                synchronized(object3) { object3.wait() }
//                L.e(Thread.currentThread().name + " -> C")
//                synchronized(object1) { object1.notify() }
//            }
//        }

        val lock = ReentrantLock()
        var status = 1
        var logs = mutableListOf<String>()

        val runnable1 = {
            var i = 0
            while (i < 10) {
                lock.lock()
                if (status == 1) {
                    logs.add(Thread.currentThread().name + " -> A")
                    i++
                    status = 2
                    lock.unlock()
                } else {
                    lock.unlock()
                }
            }
        }
        val runnable2 = {
            var i = 0
            while (i < 10) {
                lock.lock()
                if (status == 2) {
                    logs.add(Thread.currentThread().name + " -> B")
                    i++
                    status = 3
                    lock.unlock()
                } else {
                    lock.unlock()
                }
            }
        }
        val runnable3 = {
            var i = 0
            while (i < 10) {
                lock.lock()
                if (status == 3) {
                    logs.add(Thread.currentThread().name + " -> C")
                    i++
                    status = 1
                    lock.unlock()
                } else {
                    lock.unlock()
                }
            }
        }
        val thread1 = Thread(runnable1, "线程1")
        val thread2 = Thread(runnable2, "线程2")
        val thread3 = Thread(runnable3, "线程3")
        val startTime = System.currentTimeMillis()
        thread1.start()
        thread2.start()
        thread3.start()
        thread1.join()
        thread2.join()
        thread3.join()
        L.e("----------方法1 $logs 耗时:${System.currentTimeMillis() - startTime}ms")


//        synchronized(object1) {
//            object1.notify()
//        }
    }

    suspend fun doSomething(foo: String): String {
        L.e("call: doSomething -> $foo")
        return "out"
    }

    fun <T> async(block: suspend () -> T) {
    }
}