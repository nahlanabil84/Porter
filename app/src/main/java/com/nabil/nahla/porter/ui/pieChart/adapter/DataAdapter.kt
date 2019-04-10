package com.nabil.nahla.porter.ui.pieChart.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nabil.nahla.porter.data.models.ProductsItem

class DataAdapter(private val dataList: MutableList<ProductsItem>) : RecyclerView.Adapter<DataVH>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DataVH {
        return DataVH(LayoutInflater.from(p0.context).inflate(R.layout.item_data_row, p0, false))
    }

    override fun onBindViewHolder(p0: DataVH, p1: Int) {
        p0.clear()
        p0.onBind(dataList)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(data: MutableList<ProductsItem>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

}
