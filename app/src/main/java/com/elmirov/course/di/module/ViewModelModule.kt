package com.elmirov.course.di.module

import androidx.lifecycle.ViewModel
import com.elmirov.course.channels.presentation.all.AllChannelsViewModel
import com.elmirov.course.channels.presentation.subscribed.SubscribedChannelsViewModel
import com.elmirov.course.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AllChannelsViewModel::class)
    fun bindAllChannelsViewModel(viewModel: AllChannelsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SubscribedChannelsViewModel::class)
    fun bindSubscribedChannelsViewModel(viewModel: SubscribedChannelsViewModel): ViewModel
}