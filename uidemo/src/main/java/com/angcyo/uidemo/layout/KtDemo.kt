package com.angcyo.uidemo.layout

import android.graphics.Paint
import android.support.annotation.ColorInt
import android.support.v7.widget.RecyclerView
import android.view.View
import com.angcyo.library.utils.L
import com.angcyo.uiview.utils.Reflect
import com.angcyo.uiview.view.UIIViewImpl
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

    fun ensureGlow(recyclerView: RecyclerView?, color: Int) {
        if (!UIIViewImpl.isLollipop()) {
            if (recyclerView != null) {
                recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
            }
            return
        }

        try {
            Reflect.invokeMethod(RecyclerView::class.java, recyclerView, "ensureTopGlow")
            Reflect.invokeMethod(RecyclerView::class.java, recyclerView, "ensureBottomGlow")
            Reflect.invokeMethod(RecyclerView::class.java, recyclerView, "ensureRightGlow")
            Reflect.invokeMethod(RecyclerView::class.java, recyclerView, "ensureLeftGlow")

            setEdgeEffect(recyclerView, color)
        } catch (e: Exception) {
            L.e(e.message)
        }

    }

    //-----------获取 默认的adapter, 获取 RBaseAdapter, 获取 AnimationAdapter----------//

    private fun setEdgeEffect(recyclerView: RecyclerView?, color: Int) {
        var mGlow = Reflect.getMember(RecyclerView::class.java, recyclerView, "mTopGlow")
        setEdgetEffect(mGlow, color)
        mGlow = Reflect.getMember(RecyclerView::class.java, recyclerView, "mLeftGlow")
        setEdgetEffect(mGlow, color)
        mGlow = Reflect.getMember(RecyclerView::class.java, recyclerView, "mRightGlow")
        setEdgetEffect(mGlow, color)
        mGlow = Reflect.getMember(RecyclerView::class.java, recyclerView, "mBottomGlow")
        setEdgetEffect(mGlow, color)
    }

    fun setEdgetEffect(edgeEffectCompat: Any?, @ColorInt color: Int) {
        val mEdgeEffect = Reflect.getMember(edgeEffectCompat, "mEdgeEffect")
        val mPaint: Any
        if (mEdgeEffect != null) {
            mPaint = Reflect.getMember(mEdgeEffect, "mPaint")
        } else {
            mPaint = Reflect.getMember(edgeEffectCompat, "mPaint")
        }

        if (mPaint is Paint) {
            mPaint.color = color
        }
    }


    fun getMember(cls: Class<*>, target: Any, member: String): Any? {
        var result: Any? = null
        try {
            val memberField = cls.getDeclaredField(member)
            memberField.isAccessible = true
            result = memberField.get(target)
        } catch (e: Exception) {
            //L.i("错误:" + cls.getSimpleName() + " ->" + e.getMessage());
        }

        return result
    }
}