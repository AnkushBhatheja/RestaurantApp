package com.zomato

import okhttp3.Interceptor
import okhttp3.Response

class ZomatoApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        request = request.newBuilder()
            .header("user-key", "b1b79d3b028f2ab4abfcd1cbe81cfcc8")
            .build()

        return chain.proceed(request)
    }
}