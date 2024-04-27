package com.elmirov.course.di.chat.module

import com.elmirov.course.chat.data.network.MessagesApi
import com.elmirov.course.chat.data.network.ReactionsApi
import com.elmirov.course.chat.data.repository.MessagesRepositoryImpl
import com.elmirov.course.chat.data.repository.ReactionsRepositoryImpl
import com.elmirov.course.chat.domain.repository.MessagesRepository
import com.elmirov.course.chat.domain.repository.ReactionsRepository
import com.elmirov.course.di.chat.annotation.ChatScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [BindChatDataModule::class])
class ChatDataModule {

    @Provides
    @ChatScope
    fun provideMessagesApi(retrofit: Retrofit): MessagesApi = retrofit.create()

    @Provides
    @ChatScope
    fun provideReactionsApi(retrofit: Retrofit): ReactionsApi = retrofit.create()
}

@Module
interface BindChatDataModule {
    @Binds
    @ChatScope
    fun bindMessagesRepository(impl: MessagesRepositoryImpl): MessagesRepository

    @Binds
    @ChatScope
    fun bindReactionsRepository(impl: ReactionsRepositoryImpl): ReactionsRepository
}