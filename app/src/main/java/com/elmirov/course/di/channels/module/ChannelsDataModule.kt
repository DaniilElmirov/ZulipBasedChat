package com.elmirov.course.di.channels.module

import com.elmirov.course.channels.data.network.AllChannelsApi
import com.elmirov.course.channels.data.network.SubscribedChannelsApi
import com.elmirov.course.channels.data.network.TopicsApi
import com.elmirov.course.channels.data.repository.AllChannelsRepositoryImpl
import com.elmirov.course.channels.data.repository.ChannelTopicsRepositoryImpl
import com.elmirov.course.channels.data.repository.SubscribedChannelsRepositoryImpl
import com.elmirov.course.channels.domain.repository.AllChannelsRepository
import com.elmirov.course.channels.domain.repository.ChannelTopicsRepository
import com.elmirov.course.channels.domain.repository.SubscribedChannelsRepository
import com.elmirov.course.di.channels.annotation.ChannelsScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [BindChannelsDataModule::class])
class ChannelsDataModule {
    @Provides
    @ChannelsScope
    fun provideAllChannelsApi(retrofit: Retrofit): AllChannelsApi = retrofit.create()

    @Provides
    @ChannelsScope
    fun provideSubscribedChannelsApi(retrofit: Retrofit): SubscribedChannelsApi = retrofit.create()

    @Provides
    @ChannelsScope
    fun provideTopicsApi(retrofit: Retrofit): TopicsApi = retrofit.create()
}

@Module
interface BindChannelsDataModule {
    @Binds
    @ChannelsScope
    fun bindAllChannelsRepository(impl: AllChannelsRepositoryImpl): AllChannelsRepository

    @Binds
    @ChannelsScope
    fun bindSubscribedChannelsRepository(impl: SubscribedChannelsRepositoryImpl): SubscribedChannelsRepository

    @Binds
    @ChannelsScope
    fun bindChannelTopicsRepository(impl: ChannelTopicsRepositoryImpl): ChannelTopicsRepository
}