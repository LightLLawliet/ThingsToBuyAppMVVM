package com.example.thingstobuyappmvvm.presentation

import com.example.thingstobuyappmvvm.core.Init
import com.example.thingstobuyappmvvm.domain.NewMainInteractor

class NewViewModel(
    private val communication: NewMainCommunication,
    private val interactor: NewMainInteractor
) : Init {
    override fun init(isFirstRun: Boolean) {

    }
}