package com.angcyo.uidemo.layout.recycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/07/26 10:11
 * 修改人员：Robi
 * 修改时间：2018/07/26 10:11
 * 修改备注：
 * Version: 1.0.0
 */
class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

    init {

    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        L.e("创建布局 -> $viewType")
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_text_view_test, parent, false))
    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
        super.onViewAttachedToWindow(holder)
        L.e("View: onViewAttachedToWindow -> ${holder.adapterPosition} ${holder.layoutPosition}")
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        super.onViewDetachedFromWindow(holder)
        L.e("View: onViewDetachedFromWindow -> ${holder.adapterPosition} ${holder.layoutPosition}")
    }

    var itemWidth = 0
    var itemHeight = 0

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        L.e("绑定布局 -> $position")
        holder.itemView.findViewById<TextView>(R.id.text_view).apply {
            text = "$itemWidth $itemHeight 绑定布局绑定布局绑定布局绑定布局 位置:$position 一二三四五六七八九十abcdefghijklmnopqresuvwxyz"
        }

        holder.itemView.post {
            holder.itemView.apply {
                itemWidth = measuredWidth
                itemHeight = measuredHeight
            }
        }
    }

}