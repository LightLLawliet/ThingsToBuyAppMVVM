package com.example.thingstobuyappmvvm.presentation

import com.example.thingstobuyappmvvm.core.Communication
import com.example.thingstobuyappmvvm.core.SingleLiveEvent

interface NewMainCommunication {

    interface Put : Communication.Put<NewUiState>

    interface Observe : Communication.Observe<NewUiState>

    interface Mutable : Put, Observe

    class Base : Communication.Abstract<NewUiState>(SingleLiveEvent()), Mutable
}