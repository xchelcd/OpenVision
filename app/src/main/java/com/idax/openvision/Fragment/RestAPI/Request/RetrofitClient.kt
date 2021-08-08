package com.idax.openvision.Fragment.RestAPI.Request

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://dog.ceo/api/breed/"

    private val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val original = chain.request()

        val requestBuilder = original.newBuilder()
            //.addHeader("", "")
            .method(original.method, original.body)

        val request = requestBuilder.build()
        chain.proceed(request)
    }.build()

    val instance: APIService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        retrofit.create(APIService::class.java)
    }
}