package com.elmirov.course.presentation.channels.all

import androidx.lifecycle.ViewModel
import com.elmirov.course.domain.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AllChannelsViewModel: ViewModel() {

    private val testData = listOf(
        Channel(
            id = 1,
            name = "#general all"
        ),
        Channel(
            id = 2,
            name = "#computing all"
        ),
        Channel(
            id = 3,
            name = "#PR all"
        ),
    )

    private val _allChannels = MutableStateFlow(AllChannelsState.Content(testData))
    val allChannels = _allChannels.asStateFlow()
}