package com.elmirov.course.di.chat.component

import com.elmirov.course.chat.ui.ChatFragment
import com.elmirov.course.di.chat.module.ChatDataModule
import com.elmirov.course.di.chat.annotation.ChatScope
import dagger.Subcomponent

@ChatScope
@Subcomponent(
    modules = [
        ChatDataModule::class,
    ]
)
interface ChatComponent {

    fun inject(fragment: ChatFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ChatComponent
    }
}