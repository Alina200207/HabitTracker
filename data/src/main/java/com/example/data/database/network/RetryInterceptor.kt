package com.example.data.database.network

import android.util.Log
import com.example.domain.constants.Constants
import okhttp3.Interceptor
import okhttp3.Response


class RetryInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestWithAuthorization =
            request.newBuilder().header(Constants.headerAuthorization, Constants.authorization).build()
        return try {
            chain.proceed(requestWithAuthorization)
        } catch (e: Throwable) {
            Log.i(Constants.error, e.toString())
            Thread.sleep(Constants.responseTimeRetry)
            repeatRequest(chain)
        }
    }

    private fun repeatRequest(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestWithAuthorization =
            request.newBuilder().header(Constants.headerAuthorization, Constants.authorization).build()
        return try {
            chain.proceed(requestWithAuthorization)
        } catch (e: Throwable) {
            Thread.sleep(Constants.responseTimeRetry)
            Log.i(Constants.error, e.toString())
            repeatRequest(chain)
        }
    }
}