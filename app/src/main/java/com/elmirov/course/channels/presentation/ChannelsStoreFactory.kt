package com.elmirov.course.channels.presentation

import com.elmirov.course.navigation.router.GlobalRouter
import vivid.money.elmslie.core.store.ElmStore
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class ChannelsStoreFactory @Inject constructor(
    private val channelsActor: ChannelsActor,
    private val globalRouter: GlobalRouter,
) {

    fun create(): Store<ChannelsEvent, ChannelsEffect, ChannelsState> =
        ElmStore(
            initialState = ChannelsState(),
            reducer = ChannelsReducer(globalRouter),
            actor = channelsActor
        )
}