package com.elmirov.course.chat.presentation

import com.elmirov.course.chat.domain.entity.ChatInfo
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

        is ChatEvent.Internal.ChatLoadingError -> {
            state { copy(loading = false) }
            effects { +ChatEffect.ShowError }
        }

        is ChatEvent.Internal.ChatLoadingSuccess -> {
            state { copy(loading = false, content = event.data) }
        }
    }

    override fun Result.ui(event: ChatEvent.Ui): Any = when (event) {
        is ChatEvent.Ui.Init -> {
            state {
                copy(
                    loading = true,
                    chatInfo = ChatInfo(
                        channelName = event.channelName,
                        topicName = event.topicName
                    ),
                )
            }
            commands { +ChatCommand.Load }
        }

        ChatEvent.Ui.OnBackClick -> globalRouter.back()

        is ChatEvent.Ui.OnReactionClick -> {
            commands {
                +ChatCommand.AddReaction(
                    messageId = event.messageId,
                    reaction = event.reaction,
                    selected = event.selected,
                )
            }
        }

        is ChatEvent.Ui.OnSendMessageClick -> {
            commands { +ChatCommand.Send(event.text) }
        }
    }
}