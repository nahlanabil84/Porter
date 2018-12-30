package com.nabil.nahla.porter.ui.pieChart.view

interface PieChartView {
    fun showLoading()

    fun hideLoading()

    fun showMessage(stringResourceId: Int)

    fun showMessage(errorMsg: String)

    fun loadData(response: MutableList<Any>)

}