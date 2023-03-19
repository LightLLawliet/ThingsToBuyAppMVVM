package com.example.thingstobuyappmvvm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
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

    override fun observe(owner: LifecycleOwner, observer: Observer<UiState>) =
        communication.observe(owner, observer)
}

interface MainCommunication {

    interface Put {
        fun put(value: UiState)
    }

    interface Observe {
        fun observe(owner: LifecycleOwner, observer: Observer<UiState>)
    }

    interface Mutable : Put, Observe

    class Base(private val liveData: MutableLiveData<UiState>) : Mutable {
        override fun put(value: UiState) {
            liveData.value = value
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<UiState>) {
            liveData.observe(owner, observer)
        }

    }
}

sealed class UiState {
    object ZeroDays : UiState() {

    }

    data class NDays(private val days: Int) : UiState() {

    }
}