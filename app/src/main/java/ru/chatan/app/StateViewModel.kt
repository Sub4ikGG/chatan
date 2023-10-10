package ru.chatan.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class StateViewModel <S, E> (initial: S) : ViewModel() {

    protected val mutableState: MutableStateFlow<S> = MutableStateFlow(initial)
    val state = mutableState.asStateFlow()

    fun getStateValue() = state.value

    fun run(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        block()
    }

    abstract fun send(event: E): Job

}