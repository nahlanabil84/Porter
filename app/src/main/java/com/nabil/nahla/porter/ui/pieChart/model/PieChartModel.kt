package com.nabil.nahla.porter.ui.login.model

interface PieChartModel {
    interface OnDataLoadedListener {
        fun onFailed(stringResourceId: Int)

        fun onFailed(errorMsg: String)

        fun onSuccess(response: MutableList<Any>)
    }

    fun getTestingData(token: String, listener: OnDataLoadedListener)

}