package com.example.yookcalc.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : UiState, E : UiEvent, F : UiEffect>(
    initialState: S
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<F>()
    val effect: SharedFlow<F> = _effect

    abstract fun onEvent(event: E)

    protected fun setState(reducer: S.() -> S) {
        val oldState = _state.value
        val newState = oldState.reducer()
        println("[ViewModel] State changed: $oldState -> $newState")
        _state.value = newState
    }

    protected fun sendEffect(effect: F) {
        println("[ViewModel] Effect sent: $effect")
        viewModelScope.launch { _effect.emit(effect) }
    }

    fun clear() {
        println("[ViewModel] ViewModel cleared: ${this::class.simpleName}")
        viewModelScope.cancel()
    }
}