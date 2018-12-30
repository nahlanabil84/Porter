package com.nabil.nahla.porter.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppApiHelper(token: String) {
    private var api: ApiHelper? = null

    init {
        val client: OkHttpClient

        val authInterceptor = AuthInterceptor(token)

        client = OkHttpClient.Builder()
            .readTimeout(1000, TimeUnit.SECONDS)
            .connectTimeout(1000, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(ApiEndPoints.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiHelper::class.java)
    }

    fun api(): ApiHelper? {
        return api
    }

}
