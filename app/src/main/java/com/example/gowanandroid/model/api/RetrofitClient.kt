package com.example.gowanandroid.model.api

import com.example.gowanandroid.App
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {
    private val cookieJar = PersistentCookieJar(
        SetCookieCache(),
        SharedPrefsCookiePersistor(App.instance)
    )

    private fun getLogInterceptor(): Interceptor{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level=HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private val okHttpClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .cookieJar(cookieJar)
        .addInterceptor(getLogInterceptor())
        .build()
    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(ApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService: ApiService = retrofit.create(ApiService::class.java)

    fun clearCookie() = cookieJar.clear()
}