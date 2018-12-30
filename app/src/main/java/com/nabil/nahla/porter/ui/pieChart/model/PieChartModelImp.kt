package com.nabil.nahla.porter.ui.login.model

import com.nabil.nahla.porter.data.network.AppApiHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class PieChartModelImp : PieChartModel {
    override fun getTestingData(token: String, listener: PieChartModel.OnDataLoadedListener) {
        AppApiHelper("").api()?.getCustomerTesting()?.enqueue(object :
            Callback<MutableList<Any>> {
            override fun onResponse(call: Call<MutableList<Any>>, response: Response<MutableList<Any>>) {
                if (response.isSuccessful && response.body() != null) {
                    listener.onSuccess(response.body()!!)
                } else {
                    listener.onFailed(response.message().toString())
                }
            }

            override fun onFailure(call: Call<MutableList<Any>>, t: Throwable) {
                listener.onFailed(t.localizedMessage)
            }
        })
    }
}