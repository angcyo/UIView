package com.angcyo.uidemo.layout.demo

import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseContentUIView
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.recycler.adapter.RGroupAdapter
import com.angcyo.uiview.recycler.adapter.RGroupData
import com.angcyo.uiview.rsen.RefreshLayout
import com.angcyo.uiview.utils.Tip

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/12/04 11:40
 * 修改人员：Robi
 * 修改时间：2017/12/04 11:40
 * 修改备注：
 * Version: 1.0.0
 */
class RGroupAdapterUIView : BaseContentUIView() {

    override fun getTitleBar(): TitleBarPattern? {
        return super.getTitleBar()?.addRightItem(TitleBarPattern.TitleBarItem("展开") { textView ->
            clickExpandView(textView as TextView)
        })
    }

    private fun clickExpandView(textView: TextView) {
        if (TextUtils.equals(textView.text, "展开")) {
            adapter.expandAll(true)
            textView.text = "关闭"
            textView.setOnClickListener {
                clickExpandView(textView)
            }
        } else {
            adapter.expandAll(false)
            textView.text = "展开"
            textView.setOnClickListener {
                clickExpandView(textView)
            }
        }
    }

    lateinit var refreshLayout: RefreshLayout
    lateinit var recyclerView: RRecyclerView
    lateinit var adapter: RGroupAdapter<String, RDemoGroup, String>

    override fun inflateContentLayout(baseContentLayout: ContentLayout?, inflater: LayoutInflater?) {
        baseContentLayout?.let {
            refreshLayout = RefreshLayout(mActivity)
            refreshLayout.setNotifyListener(false)

            recyclerView = RRecyclerView(mActivity)

            refreshLayout.addView(recyclerView, ViewGroup.LayoutParams(-1, -1))
            it.addView(refreshLayout, ViewGroup.LayoutParams(-1, -1))
        }
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        adapter = object : RGroupAdapter<String, RDemoGroup, String>(mActivity) {
            override fun getItemLayoutId(viewType: Int): Int {
                if (viewType == RExBaseAdapter.TYPE_HEADER) {
                    return R.layout.item_single_text_view
                } else if (viewType == RExBaseAdapter.TYPE_FOOTER) {
                    return R.layout.item_single_text_view
                }
                return super.getItemLayoutId(viewType)
            }

            override fun onBindHeaderView(holder: RBaseViewHolder, posInHeader: Int, headerBean: String?) {
                super.onBindHeaderView(holder, posInHeader, headerBean)
                holder.itemView.setBackgroundColor(Color.GREEN)
                holder.tv(R.id.text_view).text = "头部数据$headerBean $posInHeader"
            }

            override fun onBindFooterView(holder: RBaseViewHolder, posInFooter: Int, footerBean: String?) {
                super.onBindFooterView(holder, posInFooter, footerBean)
                holder.itemView.setBackgroundColor(Color.YELLOW)
                holder.tv(R.id.text_view).text = "尾部数据$footerBean $posInFooter"
            }
        }

        recyclerView.adapter = adapter
        adapter.appendHeaderData("_H1")
        adapter.appendHeaderData("_H2")
        adapter.appendFooterData("_F1")
        adapter.appendFooterData("_F2")

        val groups = mutableListOf<RDemoGroup>()
        groups.add(RDemoGroup("分组标题1", false))
        groups.add(RDemoGroup("分组标题2", false))
        groups.add(RDemoGroup("分组标题(展开关闭其他)3", true))
        groups.add(RDemoGroup("分组标题(展开关闭其他)4", true))
        adapter.resetAllData(groups)
    }

    inner class RDemoGroup(private val groupTitle: String, private val closeOther: Boolean) : RGroupData<String>() {

        init {
            isExpand = false
        }

        override fun getGroupCount(): Int {
            return 2
        }

        override fun getGroupLayoutId(indexInGroup: Int): Int {
            return R.layout.item_single_text_view
        }

        override fun onBindGroupView(holder: RBaseViewHolder, position: Int, indexInGroup: Int) {
            super.onBindGroupView(holder, position, indexInGroup)
            holder.itemView.setBackgroundResource(R.drawable.base_bg_selector)
            holder.tv(R.id.text_view).text = "${groupTitle}_$indexInGroup $position:$indexInGroup"

            holder.clickItem {
                setExpand(adapter, !isExpand, closeOther)
            }
        }

        override fun getDataCount(): Int {
            return if (isExpand) 4 else 0
        }

        override fun getDataLayoutId(indexInData: Int): Int {
            return R.layout.item_image_text_view_little
        }

        override fun onBindDataView(holder: RBaseViewHolder, position: Int, indexInData: Int) {
            super.onBindDataView(holder, position, indexInData)
            holder.tv(R.id.text_view).text = "数据测试$position:$indexInData"
            holder.clickItem {
                Tip.show(holder.tv(R.id.text_view).text, R.drawable.base_ok)
            }
        }
    }
}