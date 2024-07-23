package com.elmirov.course.di.chat.module

import com.elmirov.course.chat.presentation.ChatActor
import com.elmirov.course.chat.presentation.ChatCommand
import com.elmirov.course.chat.presentation.ChatEffect
import com.elmirov.course.chat.presentation.ChatEvent
import com.elmirov.course.chat.presentation.ChatReducer
import com.elmirov.course.chat.presentation.ChatState
import com.elmirov.course.di.chat.annotation.ChatScope
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.core.store.ElmStore

@Module
class ChatPresentationModule {

    @Provides
    @ChatScope
    fun provideChatState(): ChatState = ChatState()

    @Provides
    @ChatScope
    fun provideChatStore(
        chatState: ChatState,
        chatReducer: ChatReducer,
        chatActor: ChatActor
    ): ElmStore<ChatEvent, ChatState, ChatEffect, ChatCommand> =
        ElmStore(
            initialState = chatState,
            reducer = chatReducer,
            actor = chatActor,
        )
}