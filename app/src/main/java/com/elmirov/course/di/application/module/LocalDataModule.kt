package com.elmirov.course.di.application.module

import android.content.Context
import androidx.room.Room
import com.elmirov.course.channels.data.local.dao.ChannelsDao
import com.elmirov.course.channels.data.local.dao.TopicsDao
import com.elmirov.course.chat.data.local.dao.ChatDao
import com.elmirov.course.core.database.AppDatabase
import com.elmirov.course.di.application.annotation.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class LocalDataModule {
    @Provides
    @ApplicationScope
    fun provideAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()

    @Provides
    @ApplicationScope
    fun provideChannelsDao(database: AppDatabase): ChannelsDao =
        database.channelsDao()

    @Provides
    @ApplicationScope
    fun provideTopicsDao(database: AppDatabase): TopicsDao =
        database.topicsDao()

    @Provides
    @ApplicationScope
    fun provideChatDao(database: AppDatabase): ChatDao =
        database.chatDao()
}