package com.example.thingstobuyappmvvm.presentation

import com.example.thingstobuyappmvvm.core.Communication

interface NewMainCommunication {

    interface Put : Communication.Put<NewUiState>

    interface Observe : Communication.Observe<NewUiState>

    interface Mutable : Put, Observe

    class Base : Communication.Abstract<NewUiState>(), Mutable
}