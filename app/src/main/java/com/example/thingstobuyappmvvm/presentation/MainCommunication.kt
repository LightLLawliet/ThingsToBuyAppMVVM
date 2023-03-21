package com.example.thingstobuyappmvvm.presentation

import com.example.thingstobuyappmvvm.core.Communication


interface MainCommunication {

    interface Put : Communication.Put<UiState>

    interface Observe : Communication.Observe<UiState>

    interface Mutable : Put, Observe

    class Base : Communication.Abstract<UiState>(), Mutable
}
