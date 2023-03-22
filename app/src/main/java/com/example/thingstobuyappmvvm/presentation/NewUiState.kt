package com.example.thingstobuyappmvvm.presentation

import com.example.thingstobuyappmvvm.domain.Card

sealed class NewUiState {

    data class Add(private val args: Card) : NewUiState() {

    }
}