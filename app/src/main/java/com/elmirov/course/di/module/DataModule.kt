package com.elmirov.course.di.module

import com.elmirov.course.channels.data.network.AllChannelsApi
import com.elmirov.course.channels.data.network.SubscribedChannelsApi
import com.elmirov.course.channels.data.network.TopicsApi
import com.elmirov.course.channels.data.repository.AllChannelsRepositoryImpl
import com.elmirov.course.channels.data.repository.ChannelTopicsRepositoryImpl
import com.elmirov.course.channels.data.repository.SubscribedChannelsRepositoryImpl
import com.elmirov.course.channels.domain.repository.AllChannelsRepository
import com.elmirov.course.channels.domain.repository.ChannelTopicsRepository
import com.elmirov.course.channels.domain.repository.SubscribedChannelsRepository
import com.elmirov.course.chat.data.network.MessagesApi
import com.elmirov.course.chat.data.repository.MessagesRepositoryImpl
import com.elmirov.course.chat.domain.repository.MessagesRepository
import com.elmirov.course.core.network.AuthorizationInterceptor
import com.elmirov.course.di.annotation.ApplicationScope
import com.elmirov.course.profile.data.network.ProfileApi
import com.elmirov.course.profile.data.repository.OtherProfileRepositoryImpl
import com.elmirov.course.profile.data.repository.OwnProfileRepositoryImpl
import com.elmirov.course.profile.domain.repository.OtherProfileRepository
import com.elmirov.course.profile.domain.repository.OwnProfileRepository
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
    fun provideAllChannelsApi(retrofit: Retrofit): AllChannelsApi = retrofit.create()

    @Provides
    @ApplicationScope
    fun provideSubscribedChannelsApi(retrofit: Retrofit): SubscribedChannelsApi = retrofit.create()

    @Provides
    @ApplicationScope
    fun provideUsersApi(retrofit: Retrofit): UsersApi = retrofit.create()

    @Provides
    @ApplicationScope
    fun provideProfileApi(retrofit: Retrofit): ProfileApi = retrofit.create()

    @Provides
    @ApplicationScope
    fun provideTopicsApi(retrofit: Retrofit): TopicsApi = retrofit.create()

    @Provides
    @ApplicationScope
    fun provideMessagesApi(retrofit: Retrofit): MessagesApi = retrofit.create()
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

    @Binds
    @ApplicationScope
    fun bindOwnProfileRepository(impl: OwnProfileRepositoryImpl): OwnProfileRepository

    @Binds
    @ApplicationScope
    fun bindOtherProfileRepository(impl: OtherProfileRepositoryImpl): OtherProfileRepository

    @Binds
    @ApplicationScope
    fun bindChannelTopicsRepository(impl: ChannelTopicsRepositoryImpl): ChannelTopicsRepository

    @Binds
    @ApplicationScope
    fun bindMessagesRepository(impl: MessagesRepositoryImpl): MessagesRepository
}