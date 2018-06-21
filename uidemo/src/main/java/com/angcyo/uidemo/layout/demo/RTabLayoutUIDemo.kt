package com.angcyo.uidemo.layout.demo

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.viewgroup.RTabLayout
import com.angcyo.uiview.widget.viewpager.RPagerAdapter

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/06/20 15:59
 * 修改人员：Robi
 * 修改时间：2018/06/20 15:59
 * 修改备注：
 * Version: 1.0.0
 */
class RTabLayoutUIDemo : BaseItemUIView() {
    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {

                val tabLayout: RTabLayout = holder.v(R.id.base_tab_layout)
                val tabLayout2: RTabLayout = holder.v(R.id.base_tab_layout2)
                val tabLayout3: RTabLayout = holder.v(R.id.base_tab_layout3)

                val viewPager = holder.pager(R.id.view_pager)

                tabLayout.onTabLayoutListener = object : RTabLayout.OnTabLayoutListener() {
                    override fun onPageScrolled(tabLayout: RTabLayout, currentView: View?, nextView: View?, positionOffset: Float) {
                        super.onPageScrolled(tabLayout, currentView, nextView, positionOffset)
                    }

                    override fun onSelectorItemView(tabLayout: RTabLayout, itemView: View, index: Int) {
                        super.onSelectorItemView(tabLayout, itemView, index)
                    }

                    override fun onUnSelectorItemView(tabLayout: RTabLayout, itemView: View, index: Int) {
                        super.onUnSelectorItemView(tabLayout, itemView, index)
                    }

                    override fun onTabSelector(tabLayout: RTabLayout, fromIndex: Int, toIndex: Int) {
                        super.onTabSelector(tabLayout, fromIndex, toIndex)
                        L.e("RTabLayout: onTabSelector -> fo:$fromIndex to:$toIndex")
                        viewPager.currentItem = toIndex
                    }

                    override fun onTabReSelector(tabLayout: RTabLayout, itemView: View, index: Int) {
                        super.onTabReSelector(tabLayout, itemView, index)
                        L.e("RTabLayout: onTabReSelector -> $index")
                    }
                }

                viewPager.apply {
                    adapter = object : RPagerAdapter() {
                        override fun getCount(): Int {
                            return 40
                        }

                        override fun getItemType(position: Int): Int {
                            return position % 4
                        }

                        override fun getLayoutId(position: Int, itemType: Int): Int {
                            return when (itemType) {
                                0 -> R.layout.item_text_view
                                1 -> R.layout.item_image_view
                                2 -> R.layout.item_single_image
                                else -> R.layout.item_card_view
                            }
                        }

                        override fun initItemView(rootView: View, position: Int, itemType: Int) {
                            super.initItemView(rootView, position, itemType)
                            if (itemType == 0) {
                                rootView.setBackgroundColor(Color.DKGRAY)
                                rootView.findViewById<TextView>(R.id.text_view).text = "测试数据 第$position 页"
                            }
                        }
                    }

                    tabLayout.setupViewPager(this)
                    tabLayout2.setupViewPager(this)
                }

                tabLayout.setCurrentItem(1)
                tabLayout3.setCurrentItem(2)
            }

            override fun getItemLayoutId(): Int {
                return R.layout.view_tab_layout_demo
            }

        })
    }
}