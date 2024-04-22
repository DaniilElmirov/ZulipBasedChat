package com.elmirov.course.chat.presentation

import com.elmirov.course.navigation.router.GlobalRouter
import vivid.money.elmslie.core.store.ElmStore
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class ChatStoreFactory @Inject constructor(
    private val chatActor: ChatActor,
    private val globalRouter: GlobalRouter,
) {

    fun create(): Store<ChatEvent, ChatEffect, ChatState> =
        ElmStore(
            initialState = ChatState(),
            reducer = ChatReducer(globalRouter),
            actor = chatActor
        )
}