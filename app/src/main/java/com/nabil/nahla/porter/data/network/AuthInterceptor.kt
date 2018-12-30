package com.nabil.nahla.porter.data.network

import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor : Interceptor {

    var accessToken: String = ""

    constructor(accessToken: String) {
        this.accessToken = accessToken
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val request = original.newBuilder()
            .header("Authorization", "Bearer " + this.accessToken)
            .method(original.method(), original.body())
            .build()

        return chain.proceed(request)
    }
}