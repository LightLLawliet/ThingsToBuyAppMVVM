package com.example.thingstobuyappmvvm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import org.junit.Assert.assertEquals
import org.junit.Test

class NewViewModelTest {

    @Test
    fun start() {
        val communication = FakeCommunication()
        val interactor = FakeInteractor(listOf(Card.Add))
        val viewModel = NewViewModel(communication, interactor)
        viewModel.init(isFirstRun = true)
        assertEquals(NewUiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.list.size)

    }

    @Test
    fun `add first card`() {
        val communication = FakeCommunication()
        val interactor = FakeInteractor(listOf(Card.Add))
        val viewModel = NewViewModel(communication, interactor)
        viewModel.init(isFirstRun = true)
        assertEquals(NewUiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, Card.Make), communication.list[1])
        assertEquals(2, communication.list.size)
    }

    @Test
    fun `cancel make card`() {

        val communication = FakeCommunication()
        val interactor = FakeInteractor(listOf(Card.Add))
        val viewModel = NewViewModel(communication, interactor)
        viewModel.init(isFirstRun = true)
        assertEquals(NewUiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, Card.Make), communication.list[1])
        assertEquals(2, communication.list.size)

        viewModel.cancelMakingCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, Card.Add), communication.list[2])
        assertEquals(3, communication.list.size)
    }

    @Test
    fun `save first card`() {
        val communication = FakeCommunication()
        val interactor = FakeInteractor(listOf(Card.Add))
        val viewModel = NewViewModel(communication, interactor)
        viewModel.init(isFirstRun = true)
        assertEquals(NewUiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, Card.Make), communication.list[1])
        assertEquals(2, communication.list.size)

        interactor.canAddNewCard = true

        viewModel.saveNewCard(text = "days without smoking", position = 0)
        assertEquals(
            NewUiState.Replace(
                position = 0,
                Card.ZeroDays(text = "days without smoking")
            ),
            communication.list[3]
        )
        assertEquals(4, communication.list.size)
        assertEquals(1, interactor.canAddNewCardList)
        assertEquals(true, interactor.canAddNewCardList[0])
    }

    @Test
    fun `save only one card`() {
        val communication = FakeCommunication()
        val interactor = FakeInteractor(listOf(Card.Add))
        val viewModel = NewViewModel(communication, interactor)
        viewModel.init(isFirstRun = true)
        assertEquals(NewUiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, Card.Make), communication.list[1])
        assertEquals(2, communication.list.size)

        interactor.canAddNewCard = false

        viewModel.saveNewCard(text = "days without smoking", position = 0)
        assertEquals(
            NewUiState.Replace(
                position = 0,
                Card.ZeroDays(text = "days without smoking")
            ),
            communication.list[3]
        )
        assertEquals(3, communication.list.size)
        assertEquals(1, interactor.canAddNewCardList)
        assertEquals(false, interactor.canAddNewCardList[0])
    }
}

private class FakeInteractor(private val cards: List<Card>) : NewMainInteractor() {

    override fun cards(): List<Card> {
        return cards
    }

    var canAddNewCard: Boolean = true
    val canAddNewCardList = mutableListOf<Boolean>()

    override fun canAddNewCard(): Boolean {
        canAddNewCardList.add(canAddNewCard)
        return canAddNewCard
    }
}

private class FakeCommunication : NewMainCommunication {

    val list = mutableListOf<NewUiState>()

    override fun put(newUiState: NewUiState) {
        list.add(newUiState)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<NewUiState>) = Unit
}