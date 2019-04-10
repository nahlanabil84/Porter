package com.nabil.nahla.porter.ui.pieChart.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.nabil.nahla.porter.data.models.ProductsItem
import kotlinx.android.synthetic.main.item_data_row.view.*


class DataVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun clear() {
        itemView.countTV.text = ""
        itemView.dateTV.text = ""
        itemView.numberTV.text = ""
    }

    fun onBind(dataList: MutableList<ProductsItem>) {
        itemView.countTV.text = dataList.get(adapterPosition).index.toString()
        itemView.dateTV.text = dataList.get(adapterPosition).date
        itemView.numberTV.text = dataList.get(adapterPosition).count.toString()
    }
}
