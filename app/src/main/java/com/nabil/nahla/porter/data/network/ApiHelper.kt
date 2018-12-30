package com.nabil.nahla.porter.data.network

import com.nabil.nahla.porter.data.models.ResponseLogin
import com.nabil.nahla.porter.data.network.ApiEndPoints.CUSTOMER_LOGIN
import com.nabil.nahla.porter.data.network.ApiEndPoints.CUSTOMER_TESTING
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiHelper {

    @FormUrlEncoded
    @POST(CUSTOMER_LOGIN)
    fun postCustomerLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseLogin>

    @GET(CUSTOMER_TESTING)
    fun getCustomerTesting(): Call<MutableList<Any>>

}
