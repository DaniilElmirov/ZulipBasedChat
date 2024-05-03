package com.elmirov.course.di.channels.component

import com.elmirov.course.channels.ui.all.AllChannelsFragment
import com.elmirov.course.channels.ui.subscribed.SubscribedChannelsFragment
import com.elmirov.course.di.channels.annotation.ChannelsScope
import com.elmirov.course.di.channels.module.ChannelsPresentationModule
import dagger.Subcomponent

@ChannelsScope
@Subcomponent(
    modules = [
        ChannelsPresentationModule::class,
    ]
)
interface ChannelsComponent {

    fun inject(fragment: AllChannelsFragment)

    fun inject(fragment: SubscribedChannelsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ChannelsComponent
    }
}