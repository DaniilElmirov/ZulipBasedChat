package com.elmirov.course.chat.presentation

import com.elmirov.course.chat.domain.usecase.AddReactionToMessageUseCase
import com.elmirov.course.chat.domain.usecase.GetChannelTopicMessagesUseCase
import com.elmirov.course.chat.domain.usecase.RemoveReactionUseCase
import com.elmirov.course.chat.domain.usecase.SendMessageToChannelTopicUseCase
import com.elmirov.course.core.result.domain.entity.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.core.store.Actor
import javax.inject.Inject

class ChatActor @Inject constructor(
    private val getChannelTopicMessagesUseCase: GetChannelTopicMessagesUseCase,
    private val sendMessageToChannelTopicUseCase: SendMessageToChannelTopicUseCase,
    private val addReactionToMessageUseCase: AddReactionToMessageUseCase,
    private val removeReactionUseCase: RemoveReactionUseCase,
) : Actor<ChatCommand, ChatEvent>() {

    override fun execute(command: ChatCommand): Flow<ChatEvent> = flow {
        when (command) {
            is ChatCommand.Load -> {
                when (val result =
                    getChannelTopicMessagesUseCase(command.channelName, command.topicName)
                ) {
                    is Result.Error -> emit(ChatEvent.Internal.ChatLoadingError)
                    is Result.Success -> emit(ChatEvent.Internal.ChatLoadingSuccess(result.data))
                }
            }

            is ChatCommand.Send -> {
                when (sendMessageToChannelTopicUseCase(
                    command.channelName,
                    command.topicName,
                    command.text
                )) {
                    is Result.Error -> emit(ChatEvent.Internal.ChatLoadingError)
                    is Result.Success -> { //TODO переделать
                        when (val result =
                            getChannelTopicMessagesUseCase(command.channelName, command.topicName)
                        ) {
                            is Result.Error -> emit(ChatEvent.Internal.ChatLoadingError)
                            is Result.Success -> emit(ChatEvent.Internal.ChatLoadingSuccess(result.data))
                        }
                    }
                }
            }

            is ChatCommand.AddReaction -> {
                val result = if (command.selected)
                    removeReactionUseCase(
                        command.messageId,
                        command.reaction.emojiName,
                        command.reaction.emojiCode
                    )
                else
                    addReactionToMessageUseCase(
                        command.messageId,
                        command.reaction.emojiName,
                        command.reaction.emojiCode
                    )

                when (result) {
                    is Result.Error -> emit(ChatEvent.Internal.ChatLoadingError)
                    is Result.Success -> when (val loadResult =
                        getChannelTopicMessagesUseCase(command.channelName, command.topicName)
                    ) {
                        is Result.Error -> emit(ChatEvent.Internal.ChatLoadingError)
                        is Result.Success -> emit(ChatEvent.Internal.ChatLoadingSuccess(loadResult.data))
                    } //TODO переделать, хотябы разбить на отдельные методы
                }
            }
        }
    }
}