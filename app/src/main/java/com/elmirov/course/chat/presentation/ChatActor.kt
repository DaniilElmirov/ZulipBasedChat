package com.elmirov.course.chat.presentation

import com.elmirov.course.channels.domain.usecase.GetCachedChannelTopicsUseCase
import com.elmirov.course.channels.domain.usecase.GetChannelTopicsUseCase
import com.elmirov.course.chat.domain.entity.ChatInfo
import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.domain.usecase.AddReactionToMessageUseCase
import com.elmirov.course.chat.domain.usecase.ChangeTopicUseCase
import com.elmirov.course.chat.domain.usecase.DeleteMessageUseCase
import com.elmirov.course.chat.domain.usecase.EditMessageUseCase
import com.elmirov.course.chat.domain.usecase.GetCachedChannelTopicMessagesUseCase
import com.elmirov.course.chat.domain.usecase.GetChannelTopicMessagesUseCase
import com.elmirov.course.chat.domain.usecase.GetLastMessagesUseCase
import com.elmirov.course.chat.domain.usecase.GetUpdatedMessagesUseCase
import com.elmirov.course.chat.domain.usecase.LoadNextMessagesPageUseCase
import com.elmirov.course.chat.domain.usecase.LoadPrevMessagesPageUseCase
import com.elmirov.course.chat.domain.usecase.RemoveReactionUseCase
import com.elmirov.course.chat.domain.usecase.SendMessageToChannelTopicUseCase
import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.util.toTopicNames
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.core.store.Actor
import javax.inject.Inject

class ChatActor @Inject constructor(
    private val chatInfo: ChatInfo,
    private val getChannelTopicMessagesUseCase: GetChannelTopicMessagesUseCase,
    private val getCachedChannelTopicMessagesUseCase: GetCachedChannelTopicMessagesUseCase,
    private val sendMessageToChannelTopicUseCase: SendMessageToChannelTopicUseCase,
    private val addReactionToMessageUseCase: AddReactionToMessageUseCase,
    private val removeReactionUseCase: RemoveReactionUseCase,
    private val loadNextMessagesPageUseCase: LoadNextMessagesPageUseCase,
    private val loadPrevMessagesPageUseCase: LoadPrevMessagesPageUseCase,
    private val getUpdatedMessagesUseCase: GetUpdatedMessagesUseCase,
    private val getLastMessagesUseCase: GetLastMessagesUseCase,
    private val getChannelTopicsUseCase: GetChannelTopicsUseCase,
    private val getCachedChannelTopicsUseCase: GetCachedChannelTopicsUseCase,
    private val deleteMessageUseCase: DeleteMessageUseCase,
    private val editMessageUseCase: EditMessageUseCase,
    private val changeTopicUseCase: ChangeTopicUseCase,
) : Actor<ChatCommand, ChatEvent>() {

    private var firstMessageId = 0
    private var lastMessageId = 0

    override fun execute(command: ChatCommand): Flow<ChatEvent> = flow {
        when (command) {

            is ChatCommand.Load -> {
                emit(
                    applyLoadMessages(
                        getChannelTopicMessagesUseCase(
                            chatInfo.channelName,
                            chatInfo.topicName
                        )
                    )
                )
            }

            is ChatCommand.LoadCached -> {
                emit(
                    applyLoadMessages(
                        getCachedChannelTopicMessagesUseCase(
                            chatInfo.channelName,
                            chatInfo.topicName
                        )
                    )
                )
            }

            is ChatCommand.LoadTopics -> {
                emit(
                    when (val result = getChannelTopicsUseCase(chatInfo.channelId)) {
                        is Result.Error -> ChatEvent.Internal.TopicsLoadingError

                        is Result.Success -> ChatEvent.Internal.TopicsLoadingSuccess(result.data.toTopicNames())
                    }
                )
            }

            is ChatCommand.LoadCachedTopics -> {
                emit(
                    when (val result = getCachedChannelTopicsUseCase(chatInfo.channelId)) {
                        is Result.Error -> ChatEvent.Internal.TopicsLoadingError

                        is Result.Success -> ChatEvent.Internal.TopicsLoadingSuccess(result.data.toTopicNames())
                    }
                )
            }

            is ChatCommand.LoadMore -> {
                val result =
                    if (command.next)
                        loadNextMessagesPageUseCase(
                            channelName = chatInfo.channelName,
                            topicName = chatInfo.topicName,
                            id = lastMessageId
                        )
                    else
                        loadPrevMessagesPageUseCase(
                            channelName = chatInfo.channelName,
                            topicName = chatInfo.topicName,
                            id = firstMessageId
                        )

                emit(applyLoadMessages(result))
            }

            is ChatCommand.Send -> {
                val topicName = chatInfo.topicName.ifEmpty { command.topicName }

                when (
                    sendMessageToChannelTopicUseCase(
                        channelName = chatInfo.channelName,
                        topicName = topicName,
                        text = command.text
                    )
                ) {
                    is Result.Error -> emit(ChatEvent.Internal.SendError)

                    is Result.Success -> emit(
                        applyLoadMessages(
                            getLastMessagesUseCase(
                                chatInfo.channelName,
                                topicName,
                            )
                        )
                    )
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
                    is Result.Error -> emit(ChatEvent.Internal.AddReactionError)

                    is Result.Success -> emit(
                        applyLoadMessages(
                            getUpdatedMessagesUseCase(
                                channelName = chatInfo.channelName,
                                topicName = chatInfo.topicName,
                                id = command.messageId,
                            )
                        )
                    )
                }
            }

            is ChatCommand.Delete -> {
                when (deleteMessageUseCase(command.messageId)) {
                    is Result.Error -> emit(ChatEvent.Internal.ChatLoadingError)
                    is Result.Success -> emit(ChatEvent.Internal.DeleteSuccess)
                }
            }

            is ChatCommand.Edit -> {
                when (editMessageUseCase(command.messageId, command.text)) {
                    is Result.Error -> emit(ChatEvent.Internal.ChatLoadingError)
                    is Result.Success -> emit(ChatEvent.Internal.EditSuccess)
                }
            }

            is ChatCommand.ChangeTopic -> {
                when (changeTopicUseCase(command.messageId, command.topicName)) {
                    is Result.Error -> emit(ChatEvent.Internal.ChatLoadingError)
                    is Result.Success -> emit(ChatEvent.Internal.ChangeTopicSuccess)
                }
            }
        }
    }

    private fun applyLoadMessages(result: Result<List<Message>>): ChatEvent.Internal =
        when (result) {
            is Result.Error -> ChatEvent.Internal.ChatLoadingError

            is Result.Success -> {
                firstMessageId = result.data.first().id
                lastMessageId = result.data.last().id

                ChatEvent.Internal.ChatLoadingSuccess(result.data)
            }
        }
}