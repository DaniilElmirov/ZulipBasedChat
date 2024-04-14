package com.elmirov.course.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.domain.entity.Reaction
import com.elmirov.course.chat.domain.usecase.GetChannelTopicMessagesUseCase
import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.navigation.router.GlobalRouter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val globalRouter: GlobalRouter,
    private val getChannelTopicMessagesUseCase: GetChannelTopicMessagesUseCase
) : ViewModel() {

    private val _messages = MutableStateFlow<ChatState>(ChatState.Loading)
    val messages = _messages.asStateFlow()

    fun loadMessages(topicChannelName: String, topicName: String) {
        viewModelScope.launch {
            when (val result = getChannelTopicMessagesUseCase(topicChannelName, topicName)) {
                is Result.Error -> Unit
                is Result.Success -> _messages.value = ChatState.Content(result.data)
            }
        }
    }

    fun sendMessage(message: Message) {
        viewModelScope.launch {
            handleMessage(message)
        }
    }

    //TODO добавить реализацию методов
    private suspend fun handleMessage(message: Message) {
        Unit
    }

    fun addReactionToMessage(reaction: Reaction, messageId: Int) {
        Unit
    }

    fun back() {
        globalRouter.back()
    }
}