package com.elmirov.course.channels.presentation

sealed interface ChannelsEffect {
    data object ShowError : ChannelsEffect
}