package com.anil.hse.networking

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        val builder: Headers.Builder = Headers.Builder()
        builder.add("appDevice", "IPAD")
        builder.add("locale", "de_DE")
        req = req.newBuilder().headers(builder.build()).build()
        return chain.proceed(req)
    }
}