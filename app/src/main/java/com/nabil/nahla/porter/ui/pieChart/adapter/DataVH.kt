package com.nabil.nahla.porter.ui.pieChart.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_data_row.view.*


class DataVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun clear() {
        itemView.countTV.text = ""
        itemView.dateTV.text = ""
        itemView.numberTV.text = ""
    }

    fun onBind(dataList: MutableList<Any>) {
        val dataRow: MutableList<*> = dataList[adapterPosition] as MutableList<*>
        itemView.countTV.text = (adapterPosition + 1).toString()
        itemView.dateTV.text = dataRow.first() as String
        itemView.numberTV.text = ((dataRow.last() as Double).toInt()).toString()
    }
}
