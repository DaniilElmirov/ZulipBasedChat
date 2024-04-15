package com.elmirov.course.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmirov.course.chat.domain.entity.Reaction
import com.elmirov.course.chat.domain.usecase.AddReactionToMessageUseCase
import com.elmirov.course.chat.domain.usecase.GetChannelTopicMessagesUseCase
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

    fun addReactionToMessage(messageId: Int, reaction: Reaction) {
        viewModelScope.launch {
            when (addReactionToMessageUseCase(messageId, reaction.emojiName, reaction.emojiCode)) {
                is Result.Error -> _messages.value = ChatState.Error
                is Result.Success -> loadMessages(channelName, topicName)
            }
        }
    }

    fun back() {
        globalRouter.back()
    }
}