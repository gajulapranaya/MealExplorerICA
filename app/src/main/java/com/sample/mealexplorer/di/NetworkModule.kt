package com.sample.mealexplorer.di

import com.sample.mealexplorer.data.network.ApiConstants
import com.sample.mealexplorer.data.network.MealsApi
import com.sample.mealzapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logLevel = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return HttpLoggingInterceptor().setLevel(logLevel)
    }

    @Provides
    @Singleton
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideMealsApi(httpClient: OkHttpClient): MealsApi {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.API_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MealsApi::class.java)
    }

}