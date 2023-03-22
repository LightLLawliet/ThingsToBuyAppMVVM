package com.example.thingstobuyappmvvm.presentation

import com.example.thingstobuyappmvvm.core.Init
import com.example.thingstobuyappmvvm.domain.NewMainInteractor

class NewViewModel(
    private val communication: NewMainCommunication.Mutable,
    private val interactor: NewMainInteractor
) : Init {
    override fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            val items = interactor.cards()
            communication.put(NewUiState.Add())
        }
    }
}