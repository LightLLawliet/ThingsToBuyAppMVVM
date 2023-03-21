package com.example.thingstobuyappmvvm.domain

import androidx.cardview.widget.CardView

interface NewMainInteractor {

    fun cards(): List<Card>

    fun canAddNewCard(): Boolean

    fun newCard(text: String): CardView

    fun deleteCard(id: Long)

    fun updateCard(id: Long, newText: String)

    fun resetCard(id: Long)
}