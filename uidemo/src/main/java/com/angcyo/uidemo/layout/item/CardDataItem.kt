package com.angcyo.uidemo.layout.item

import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.bean.DiceCardBean
import com.angcyo.uidemo.layout.demo.view.CardView
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.adapter.RBaseDataItem
import com.angcyo.uiview.recycler.adapter.RDataAdapter

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/08/10 15:34
 * 修改人员：Robi
 * 修改时间：2017/08/10 15:34
 * 修改备注：
 * Version: 1.0.0
 */
class CardDataItem(bean: DiceCardBean) : RBaseDataItem<DiceCardBean>(bean) {
    override fun getItemLayoutId(): Int {
        return R.layout.item_card_view
    }

    override fun getDataItemType(): Int {
        return super.getDataItemType() + 2
    }

    override fun onBindDataView(dataAdapter: RDataAdapter?, holder: RBaseViewHolder, posInData: Int) {
        super.onBindDataView(dataAdapter, holder, posInData)
        val cardView: CardView = holder.v(R.id.card_view)

        holder.tv(R.id.text_view).text = "纸牌数量:${mBaseData.targets.size}"

        cardView.setTargetCards(mBaseData.targets)
    }

}