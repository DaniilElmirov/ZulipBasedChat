package com.elmirov.course.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmirov.course.di.annotation.DispatcherIo
import com.elmirov.course.navigation.router.GlobalRouter
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val globalRouter: GlobalRouter,
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher,
) : ViewModel() {

    init {
        loadProfile()
    }

    private val _profile = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val profile = _profile.asStateFlow()

    fun back() {
        globalRouter.back()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            try {
                withContext(dispatcherIo) {
                    delay(1000)
                    _profile.value = ProfileState.Content
                }
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (exception: Exception) {
                _profile.value = ProfileState.Content //TODO добавить стейт ошибки и его обработку
            }
        }
    }
}