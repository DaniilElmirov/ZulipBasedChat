package com.elmirov.course.core.network

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor() : Interceptor {
    private companion object {
        const val AUTHORIZATION_HEADER = "Authorization"

        const val EMAIL = "elmirovwork@gmail.com"
        const val API_KEY = "VLjKKdeMV79KLlWeMQzDAv533dxtMCVL"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val headerData = Credentials.basic(EMAIL, API_KEY)

        requestBuilder.addHeader(AUTHORIZATION_HEADER, headerData)

        return chain.proceed(requestBuilder.build())
    }
}