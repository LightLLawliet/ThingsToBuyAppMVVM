package com.example.thingstobuyappmvvm.presentation

import com.example.thingstobuyappmvvm.domain.Card

sealed class NewUiState {

    data class AddAll(private val args: List<Card>) : NewUiState()

    data class Add(private val args: Card) : NewUiState()

    data class Replace(private val position: Int, private val card: Card) : NewUiState()

    data class Remove(private val position: Int) : NewUiState()
}