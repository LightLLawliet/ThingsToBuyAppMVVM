package com.example.thingstobuyappmvvm.presentation

import com.example.thingstobuyappmvvm.core.Init
import com.example.thingstobuyappmvvm.domain.Card
import com.example.thingstobuyappmvvm.domain.NewMainInteractor

class NewViewModel(
    private val communication: NewMainCommunication.Mutable,
    private val interactor: NewMainInteractor
) : Init, NewViewModelAction {
    override fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            val items = interactor.cards()
            communication.put(NewUiState.AddAll(items))
        }
    }

    override fun addCard(position: Int) {
        communication.put(NewUiState.Replace(position, Card.Make))
    }

    override fun cancelMakeCard(position: Int) {
        communication.put(NewUiState.Replace(position, Card.Add))
    }

    override fun saveNewCard(text: String, position: Int) {
        val card = interactor.newCard(text)
        val canAddNewCard = interactor.canAddNewCard()
        communication.put(NewUiState.Replace(position, card))

        if (canAddNewCard)
            communication.put(NewUiState.Add(Card.Add))
    }

    override fun editZeroDaysCard(position: Int, card: Card.ZeroDays) {
        communication.put(NewUiState.Replace(position, card.toEditable()))
    }
}

interface NewViewModelAction : AddCard, CancelMakeCard, SaveNewCard, EditZeroDaysCard

interface AddCard {
    fun addCard(position: Int)
}

interface CancelMakeCard {

    fun cancelMakeCard(position: Int)
}

interface SaveNewCard {

    fun saveNewCard(text: String, position: Int)
}

interface EditZeroDaysCard {

    fun editZeroDaysCard(position: Int, card: Card.ZeroDays)
}