package com.example.thingstobuyappmvvm.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class MainVewModel(
    private val repository: MainRepository,
    private val communication: MainCommunication.Mutable
) : MainCommunication.Observe {

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            val days = repository.days()

            communication.put(
                if (days == 0) UiState.ZeroDays
                else UiState.NDays(days)
            )
        }
    }

    fun reset() {
        repository.reset()
        communication.put(UiState.ZeroDays)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<UiState>) =
        communication.observe(owner, observer)
}
