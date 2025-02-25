package com.elmirov.course.di.application.module

import com.elmirov.course.channels.data.remote.network.AllChannelsApi
import com.elmirov.course.channels.data.remote.network.SubscribedChannelsApi
import com.elmirov.course.channels.data.remote.network.TopicsApi
import com.elmirov.course.channels.data.repository.AllChannelsRepositoryImpl
import com.elmirov.course.channels.data.repository.ChannelTopicsRepositoryImpl
import com.elmirov.course.channels.data.repository.SubscribedChannelsRepositoryImpl
import com.elmirov.course.channels.domain.repository.AllChannelsRepository
import com.elmirov.course.channels.domain.repository.ChannelTopicsRepository
import com.elmirov.course.channels.domain.repository.SubscribedChannelsRepository
import com.elmirov.course.chat.data.remote.network.MessagesApi
import com.elmirov.course.chat.data.remote.network.ReactionsApi
import com.elmirov.course.chat.data.repository.MessagesRepositoryImpl
import com.elmirov.course.chat.data.repository.ReactionsRepositoryImpl
import com.elmirov.course.chat.domain.repository.MessagesRepository
import com.elmirov.course.chat.domain.repository.ReactionsRepository
import com.elmirov.course.core.network.AuthorizationInterceptor
import com.elmirov.course.core.user.data.network.OnlineStatusesApi
import com.elmirov.course.core.user.data.network.ProfileApi
import com.elmirov.course.core.user.data.network.UsersApi
import com.elmirov.course.core.user.domain.repository.OtherProfileRepository
import com.elmirov.course.core.user.domain.repository.OwnProfileRepository
import com.elmirov.course.core.user.domain.repository.UsersRepository
import com.elmirov.course.di.application.annotation.ApplicationScope
import com.elmirov.course.di.application.annotation.BaseUrl
import com.elmirov.course.profile.data.repository.OtherProfileRepositoryImpl
import com.elmirov.course.profile.data.repository.OwnProfileRepositoryImpl
import com.elmirov.course.users.data.repository.UsersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

@Module(includes = [BindDataModule::class])
class RemoteDataModule {

    private companion object {
        private const val TIME_OUT_SECONDS = 15L
    }

    @Provides
    @ApplicationScope
    fun provideHttpClient(authorizationInterceptor: AuthorizationInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(authorizationInterceptor)
            .build()

    @Provides
    @ApplicationScope
    fun provideRetrofit(client: OkHttpClient, @BaseUrl baseUrl: String): Retrofit =
        Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @ApplicationScope
    fun provideOnlineStatusesApi(retrofit: Retrofit): OnlineStatusesApi = retrofit.create()

    @Provides
    @ApplicationScope
    fun provideAllChannelsApi(retrofit: Retrofit): AllChannelsApi = retrofit.create()

    @Provides
    @ApplicationScope
    fun provideSubscribedChannelsApi(retrofit: Retrofit): SubscribedChannelsApi = retrofit.create()

    @Provides
    @ApplicationScope
    fun provideTopicsApi(retrofit: Retrofit): TopicsApi = retrofit.create()

    @Provides
    @ApplicationScope
    fun provideMessagesApi(retrofit: Retrofit): MessagesApi = retrofit.create()

    @Provides
    @ApplicationScope
    fun provideReactionsApi(retrofit: Retrofit): ReactionsApi = retrofit.create()

    @Provides
    @ApplicationScope
    fun provideProfileApi(retrofit: Retrofit): ProfileApi = retrofit.create()

    @Provides
    @ApplicationScope
    fun provideUsersApi(retrofit: Retrofit): UsersApi = retrofit.create()
}

@Module
interface BindDataModule {
    @Binds
    @ApplicationScope
    fun bindAllChannelsRepository(impl: AllChannelsRepositoryImpl): AllChannelsRepository

    @Binds
    @ApplicationScope
    fun bindSubscribedChannelsRepository(impl: SubscribedChannelsRepositoryImpl): SubscribedChannelsRepository

    @Binds
    @ApplicationScope
    fun bindChannelTopicsRepository(impl: ChannelTopicsRepositoryImpl): ChannelTopicsRepository

    @Binds
    @ApplicationScope
    fun bindMessagesRepository(impl: MessagesRepositoryImpl): MessagesRepository

    @Binds
    @ApplicationScope
    fun bindReactionsRepository(impl: ReactionsRepositoryImpl): ReactionsRepository

    @Binds
    @ApplicationScope
    fun bindOwnProfileRepository(impl: OwnProfileRepositoryImpl): OwnProfileRepository

    @Binds
    @ApplicationScope
    fun bindOtherProfileRepository(impl: OtherProfileRepositoryImpl): OtherProfileRepository

    @Binds
    @ApplicationScope
    fun bindUsersRepository(impl: UsersRepositoryImpl): UsersRepository
}