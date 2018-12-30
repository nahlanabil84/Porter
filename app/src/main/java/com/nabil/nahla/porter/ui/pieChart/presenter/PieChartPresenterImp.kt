package com.nabil.nahla.porter.ui.pieChart.presenter

import com.nabil.nahla.porter.R
import com.nabil.nahla.porter.ui.login.model.PieChartModel
import com.nabil.nahla.porter.ui.login.model.PieChartModelImp
import com.nabil.nahla.porter.ui.pieChart.view.PieChartView

class PieChartPresenterImp(private val pieChartView: PieChartView) : PieChartPresenter,
    PieChartModel.OnDataLoadedListener {
    private val pieChartModel: PieChartModel

    init {
        this.pieChartModel = object : PieChartModelImp() {}
    }

    override fun checkTokenExixts(token: String) {
        if (token.isNullOrEmpty())
            pieChartView.showMessage(R.string.error_token_does_not_exist)
        else {
            pieChartView.showLoading()
            pieChartModel.getTestingData(token, this)
        }
    }

    override fun onFailed(stringResourceId: Int) {
        pieChartView.hideLoading()
        pieChartView.showMessage(stringResourceId)
    }

    override fun onFailed(errorMsg: String) {
        pieChartView.hideLoading()
        pieChartView.showMessage(errorMsg)
    }

    override fun onSuccess(response: MutableList<Any>) {
        pieChartView.hideLoading()
        pieChartView.loadData(response)
    }
}