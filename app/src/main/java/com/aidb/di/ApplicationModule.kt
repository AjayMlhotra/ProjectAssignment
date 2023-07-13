package com.aidb.di

import com.aidb.AppAdib
import com.aidb.BuildConfig
import com.aidb.api.ApiService
import com.aidb.utils.network.InternetInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun getApplicationContext() = AppAdib()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        internetInterceptor: InternetInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.MINUTES)
            .writeTimeout(30, TimeUnit.MINUTES)
            .connectionPool(ConnectionPool(0, 30, TimeUnit.MINUTES))
            .protocols(listOf(Protocol.HTTP_1_1))
            .followRedirects(true)
            .followSslRedirects(true)
            .addInterceptor(internetInterceptor)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .build()
                chain.proceed(newRequest)
            }
        return if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(loggingInterceptor)
                .build()
        } else
            builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().excludeFieldsWithModifiers().create()
                )
            )
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}