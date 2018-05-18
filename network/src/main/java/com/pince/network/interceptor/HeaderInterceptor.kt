package com.pince.network.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor(headerParams: Map<String, String>): Interceptor {
    val mHeadParams: Map<String, String>

    init {
        mHeadParams = headerParams
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = if (null == mHeadParams) chain.request() else addHeader(chain.request(), mHeadParams)
        return chain.proceed(request)
    }

    private fun addHeader(original: Request, headerParams: Map<String, String>): Request {
        val builder = original.newBuilder()
        val items = headerParams.entries
        val iterator = items.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next() as java.util.Map.Entry<String, String>
            builder.header(item.key, item.value)
        }
        return builder.method(original.method(), original.body())
                .build()
    }
}