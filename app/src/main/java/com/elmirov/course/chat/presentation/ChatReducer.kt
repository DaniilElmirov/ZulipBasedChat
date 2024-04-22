package com.elmirov.course.chat.presentation

import com.elmirov.course.navigation.router.GlobalRouter
import vivid.money.elmslie.core.store.dsl.ScreenDslReducer
import javax.inject.Inject

class ChatReducer @Inject constructor(
    private val globalRouter: GlobalRouter,
) : ScreenDslReducer<
        ChatEvent,
        ChatEvent.Ui,
        ChatEvent.Internal,
        ChatState,
        ChatEffect,
        ChatCommand>(
    ChatEvent.Ui::class, ChatEvent.Internal::class
) {
    override fun Result.internal(event: ChatEvent.Internal): Any = when (event) {
        ChatEvent.Internal.ChatLoadingError -> {
            state { copy(loading = false) }
            effects { +ChatEffect.ShowError }
        }

        is ChatEvent.Internal.ChatLoadingSuccess -> {
            state { copy(loading = false, content = event.data) }
        }
    }

    override fun Result.ui(event: ChatEvent.Ui): Any = when (event) {
        is ChatEvent.Ui.Init -> {
            state { copy(loading = true) }
            commands { +ChatCommand.Load(event.channelName, event.topicName) }
        }

        ChatEvent.Ui.OnBackClick -> globalRouter.back()

        is ChatEvent.Ui.OnReactionClick -> {
            commands {
                +ChatCommand.AddReaction(
                    event.channelName,
                    event.topicName,
                    event.messageId,
                    event.reaction,
                    event.selected
                )
            }
        }

        is ChatEvent.Ui.OnSendMessageClick -> {
            commands { +ChatCommand.Send(event.channelName, event.topicName, event.text) }
        }
    }
}