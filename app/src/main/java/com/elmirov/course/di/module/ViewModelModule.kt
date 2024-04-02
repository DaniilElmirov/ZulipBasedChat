package com.elmirov.course.di.module

import androidx.lifecycle.ViewModel
import com.elmirov.course.di.annotation.ViewModelKey
import com.elmirov.course.presentation.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}