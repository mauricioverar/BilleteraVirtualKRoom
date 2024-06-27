package com.mauriciovera.billeteravirtualkroom.model

import com.mauriciovera.billeteravirtualkroom.model.UserApplication.Companion.prefs
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = prefs.getToken()
        val request = chain.request().newBuilder()
            .addHeader(
                "Content-Type:", "application/json"
            )
            .addHeader(
                "Authorization", "Bearer $token"
            )
            .build()

        return chain.proceed(request)
    }
}