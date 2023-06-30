package com.example.a3trackerapplication.api

import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object RetrofitInstance {
    var mHttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private var mOkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(mHttpLoggingInterceptor)
        .addInterceptor{chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                .header("Authorization", "Bearer ${MyApplication.token}")

            val newRequest = requestBuilder.build()
            chain.proceed(newRequest)}
        .build()

    var mRetrofit: Retrofit? = null

    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    val client: Retrofit?
        get() {
            if(mRetrofit == null){
                mRetrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(mOkHttpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return mRetrofit
        }
}