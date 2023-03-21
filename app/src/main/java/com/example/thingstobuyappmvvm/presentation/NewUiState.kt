package com.example.thingstobuyappmvvm.presentation

import com.example.thingstobuyappmvvm.domain.Card

sealed class NewUiState {

    class Add(private vararg val args: Card) : NewUiState() {

    }
}