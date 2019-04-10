package com.nabil.nahla.porter.ui.login.model

import com.nabil.nahla.porter.data.models.ProductsItem

interface PieChartModel {
    interface OnDataLoadedListener {
        fun onFailed(stringResourceId: Int)

        fun onFailed(errorMsg: String)

        fun onSuccess(response: MutableList<ProductsItem>)
    }

    fun getTestingDataViaFireBase(token: String, listener: OnDataLoadedListener)

}