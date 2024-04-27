package com.elmirov.course.di.module

import com.elmirov.course.core.network.AuthorizationInterceptor
import com.elmirov.course.core.user.data.network.OnlineStatusesApi
import com.elmirov.course.di.annotation.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
class DataModule {

    private companion object {

        private const val BASE_URL = "https://tinkoff-android-spring-2024.zulipchat.com/api/v1/"
    }

    @Provides
    @ApplicationScope
    fun provideHttpClient(authorizationInterceptor: AuthorizationInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(authorizationInterceptor)
            .build()

    @Provides
    @ApplicationScope
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @ApplicationScope
    fun provideOnlineStatusesApi(retrofit: Retrofit): OnlineStatusesApi = retrofit.create()
}