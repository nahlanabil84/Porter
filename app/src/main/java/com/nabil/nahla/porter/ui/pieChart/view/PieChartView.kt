package com.nabil.nahla.porter.ui.pieChart.view

import com.nabil.nahla.porter.data.models.ProductsItem

interface PieChartView {
    fun showLoading()

    fun hideLoading()

    fun showMessage(stringResourceId: Int)

    fun showMessage(errorMsg: String)

    fun loadProducts(products: MutableList<ProductsItem>)

}