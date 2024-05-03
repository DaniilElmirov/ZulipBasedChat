package com.elmirov.course.di.channels.module

import com.elmirov.course.channels.presentation.ChannelsActor
import com.elmirov.course.channels.presentation.ChannelsCommand
import com.elmirov.course.channels.presentation.ChannelsEffect
import com.elmirov.course.channels.presentation.ChannelsEvent
import com.elmirov.course.channels.presentation.ChannelsReducer
import com.elmirov.course.channels.presentation.ChannelsState
import com.elmirov.course.di.channels.annotation.ChannelsScope
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.core.store.ElmStore

@Module
class ChannelsPresentationModule {

    @Provides
    @ChannelsScope
    fun provideChannelsState(): ChannelsState = ChannelsState()

    @Provides
    @ChannelsScope
    fun provideChannelsStore(
        channelsState: ChannelsState,
        channelsReducer: ChannelsReducer,
        channelsActor: ChannelsActor
    ): ElmStore<ChannelsEvent, ChannelsState, ChannelsEffect, ChannelsCommand> =
        ElmStore(
            initialState = channelsState,
            reducer = channelsReducer,
            actor = channelsActor,
        )
}