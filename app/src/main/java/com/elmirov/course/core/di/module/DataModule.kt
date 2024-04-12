package com.elmirov.course.core.di.module

import com.elmirov.course.channels.data.network.AllChannelsApi
import com.elmirov.course.channels.data.network.SubscribedChannelsApi
import com.elmirov.course.channels.data.repository.AllChannelsRepositoryImpl
import com.elmirov.course.channels.data.repository.SubscribedChannelsRepositoryImpl
import com.elmirov.course.channels.domain.repository.AllChannelsRepository
import com.elmirov.course.channels.domain.repository.SubscribedChannelsRepository
import com.elmirov.course.core.di.annotation.ApplicationScope
import com.elmirov.course.core.network.AuthorizationInterceptor
import com.elmirov.course.users.data.network.UsersApi
import com.elmirov.course.users.data.repository.UsersRepositoryImpl
import com.elmirov.course.users.domain.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module(includes = [BindDataModule::class])
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
    fun provideAllChannelsApi(retrofit: Retrofit): AllChannelsApi =
        retrofit.create()

    @Provides
    @ApplicationScope
    fun provideSubscribedChannelsApi(retrofit: Retrofit): SubscribedChannelsApi =
        retrofit.create()

    @Provides
    @ApplicationScope
    fun provideUsersApi(retrofit: Retrofit): UsersApi =
        retrofit.create()
}

@Module
interface BindDataModule {

    @Binds
    @ApplicationScope
    fun bindUsersRepository(impl: UsersRepositoryImpl): UsersRepository

    @Binds
    @ApplicationScope
    fun bindAllChannelsRepository(impl: AllChannelsRepositoryImpl): AllChannelsRepository

    @Binds
    @ApplicationScope
    fun bindSubscribedChannelsRepository(impl: SubscribedChannelsRepositoryImpl): SubscribedChannelsRepository
}