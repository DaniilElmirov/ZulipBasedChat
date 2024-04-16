package com.elmirov.course.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmirov.course.chat.domain.entity.Reaction
import com.elmirov.course.chat.domain.usecase.AddReactionToMessageUseCase
import com.elmirov.course.chat.domain.usecase.GetChannelTopicMessagesUseCase
import com.elmirov.course.chat.domain.usecase.RemoveReactionUseCase
import com.elmirov.course.chat.domain.usecase.SendMessageToChannelTopicUseCase
import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.navigation.router.GlobalRouter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val globalRouter: GlobalRouter,
    private val getChannelTopicMessagesUseCase: GetChannelTopicMessagesUseCase,
    private val sendMessageToChannelTopicUseCase: SendMessageToChannelTopicUseCase,
    private val addReactionToMessageUseCase: AddReactionToMessageUseCase,
    private val removeReactionUseCase: RemoveReactionUseCase,
) : ViewModel() {

    private val _messages = MutableStateFlow<ChatState>(ChatState.Loading)
    val messages = _messages.asStateFlow()

    private lateinit var channelName: String //TODO такое себе
    private lateinit var topicName: String

    fun loadMessages(topicChannelName: String, topicName: String) {
        channelName = topicChannelName
        this.topicName = topicName

        viewModelScope.launch {
            when (val result = getChannelTopicMessagesUseCase(topicChannelName, topicName)) {
                is Result.Error -> Unit
                is Result.Success -> _messages.value = ChatState.Content(result.data)
            }
        }
    }

    //TODO переделать, нужен стейт подгрузки сообщения, посмотреть в сторону обновления данных в апи
    fun sendMessage(channelName: String, topicName: String, text: String) {
        viewModelScope.launch {
            when (sendMessageToChannelTopicUseCase(channelName, topicName, text)) {
                is Result.Error -> _messages.value = ChatState.Error
                is Result.Success -> loadMessages(channelName, topicName)
            }
        }
    }

    //TODO по хорошему vm не должна знать о состоянии view
    fun addReactionToMessage(messageId: Int, reaction: Reaction, selected: Boolean) {
        viewModelScope.launch {
            val result = if (selected)
                removeReactionUseCase(messageId, reaction.emojiName, reaction.emojiCode)
            else
                addReactionToMessageUseCase(messageId, reaction.emojiName, reaction.emojiCode)

            when (result) {
                is Result.Error -> _messages.value = ChatState.Error
                is Result.Success -> loadMessages(channelName, topicName)
            }
        }
    }

    fun back() {
        globalRouter.back()
    }
}