package com.example.ecomkt.app.di

import com.example.ecomkt.data_remote.api.ApiInterface
import com.example.ecomkt.data_remote.interceptors.TokenAuthenticator
import com.example.ecomkt.app.util.Session
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = provideRetrofitConfiguration(client)

    private fun provideRetrofitConfiguration(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://41.139.29.11/CropDoctorWebAPI/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideTokenAuthenticator(session: Session,apiInterface: Provider<ApiInterface>):TokenAuthenticator = TokenAuthenticator(session,apiInterface)

    @Provides
    @Singleton
    fun provideOkHttpClient(tokenAuthenticator: TokenAuthenticator): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addNetworkInterceptor(StethoInterceptor())
        httpClient.authenticator(tokenAuthenticator)
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.writeTimeout(10, TimeUnit.SECONDS)
        httpClient.addInterceptor(loggingInterceptor())
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit):ApiInterface = retrofit.create(ApiInterface::class.java)
}