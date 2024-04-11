package com.elmirov.course.di.module

import androidx.lifecycle.ViewModel
import com.elmirov.course.di.annotation.ViewModelKey
import com.elmirov.course.presentation.channels.all.AllChannelsViewModel
import com.elmirov.course.presentation.channels.subscribed.SubscribedChannelsViewModel
import com.elmirov.course.presentation.chat.ChatViewModel
import com.elmirov.course.presentation.profile.ProfileViewModel
import com.elmirov.course.users.presentation.UsersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UsersViewModel::class)
    fun bindUsersViewModel(viewModel: UsersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AllChannelsViewModel::class)
    fun bindAllChannelsViewModel(viewModel: AllChannelsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SubscribedChannelsViewModel::class)
    fun bindSubscribedChannelsViewModel(viewModel: SubscribedChannelsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    fun bindChatViewModel(viewModel: ChatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel
}