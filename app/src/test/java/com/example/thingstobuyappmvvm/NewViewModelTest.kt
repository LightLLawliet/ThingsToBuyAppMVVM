package com.example.thingstobuyappmvvm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.thingstobuyappmvvm.domain.Card
import com.example.thingstobuyappmvvm.domain.NewMainInteractor
import com.example.thingstobuyappmvvm.presentation.NewMainCommunication
import com.example.thingstobuyappmvvm.presentation.NewUiState
import com.example.thingstobuyappmvvm.presentation.NewViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class NewViewModelTest {

    @Test
    fun start() {
        val communication = FakeCommunication()
        val interactor = FakeInteractor(listOf(Card.Add))
        val viewModel = NewViewModel(communication, interactor)
        viewModel.init(isFirstRun = true)
        assertEquals(NewUiState.AddAll(listOf(Card.Add)), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.init(isFirstRun = true)
        assertEquals(1, communication.list.size)
    }

    @Test
    fun `add first card`() {
        val communication = FakeCommunication()
        val interactor = FakeInteractor(listOf(Card.Add))
        val viewModel = NewViewModel(communication, interactor)
        viewModel.init(isFirstRun = true)
        assertEquals(NewUiState.AddAll(listOf(Card.Add)), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, card = Card.Make), communication.list[1])
        assertEquals(2, communication.list.size)
    }

    @Test
    fun `cancel make card`() {

        val communication = FakeCommunication()
        val interactor = FakeInteractor(listOf(Card.Add))
        val viewModel = NewViewModel(communication, interactor)
        viewModel.init(isFirstRun = true)
        assertEquals(NewUiState.AddAll(listOf(Card.Add)), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, card = Card.Make), communication.list[1])
        assertEquals(2, communication.list.size)

        viewModel.cancelMakeCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, card = Card.Add), communication.list[2])
        assertEquals(3, communication.list.size)
    }

    @Test
    fun `save first card`() {
        val communication = FakeCommunication()
        val interactor = FakeInteractor(listOf(Card.Add))
        val viewModel = NewViewModel(communication, interactor)

        viewModel.init(isFirstRun = true)
        assertEquals(NewUiState.AddAll(listOf(Card.Add)), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, card = Card.Make), communication.list[1])
        assertEquals(2, communication.list.size)

        interactor.canAddNewCard = true
        viewModel.saveNewCard(text = "buy a book", position = 0)
        assertEquals("buy a book", interactor.savedNewCardList[0])
        assertEquals(1, interactor.savedNewCardList.size)
        assertEquals(1, interactor.canAddNewCardList[0])
        assertEquals(
            NewUiState.Replace(
                position = 0,
                card = Card.ZeroDays(text = "buy a book", id = 4L)
            ),
            communication.list[2]
        )
        assertEquals(NewUiState.Add(Card.Add), communication.list[3])
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
        assertEquals(NewUiState.AddAll(listOf(Card.Add)), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, card = Card.Make), communication.list[1])
        assertEquals(2, communication.list.size)

        interactor.canAddNewCard = false
        viewModel.saveNewCard(text = "buy a book", position = 0)
        assertEquals("buy a book", interactor.savedNewCardList[0])
        assertEquals(1, interactor.savedNewCardList.size)
        assertEquals(1, interactor.canAddNewCardList.size)
        assertEquals(false, interactor.canAddNewCardList[0])
        assertEquals(
            NewUiState.Replace(
                position = 0,
                card = Card.ZeroDays(text = "buy a book", id = 4L)
            ),
            communication.list[2]
        )
        assertEquals(3, communication.list.size)
        assertEquals(1, interactor.canAddNewCardList)
        assertEquals(false, interactor.canAddNewCardList[0])
    }

    @Test
    fun `test edit zero days card and cancel`() {
        val communication = FakeCommunication()
        val interactor =
            FakeInteractor(listOf(Card.ZeroDays(text = "buy a book", id = 1L), Card.Add))
        val viewModel = NewViewModel(communication, interactor)
        viewModel.init(isFirstRun = true)

        assertEquals(
            NewUiState.AddAll(listOf(Card.ZeroDays(text = "buy a book", id = 1L), Card.Add)),
            communication.list[0]
        )
        assertEquals(1, communication.list.size)

        viewModel.editZeroDaysCard(
            position = 0,
            card = Card.ZeroDays(text = "buy a book", id = 1L)
        )
        assertEquals(
            NewUiState.Replace(
                position = 0,
                card = Card.ZeroDaysEdit(text = "buy a book", id = 1L)
            ),
            communication.list[1]
        )
        assertEquals(2, communication.list.size)

        viewModel.cancelEditZeroDaysCard(position = 0)
        assertEquals(
            NewUiState.Replace(
                position = 0,
                card = Card.ZeroDays(text = "buy a book", id = 1L),
            ),
            communication.list[2]
        )
        assertEquals(3, communication.list.size)
    }

    @Test
    fun `test delete zero days card`() {
        val communication = FakeCommunication()
        val interactor =
            FakeInteractor(listOf(Card.ZeroDays(text = "buy a book", id = 1L), Card.Add))
        val viewModel = NewViewModel(communication, interactor)
        viewModel.init(isFirstRun = true)

        assertEquals(
            NewUiState.AddAll(listOf(Card.ZeroDays(text = "buy a book", id = 1L), Card.Add)),
            communication.list[0]
        )
        assertEquals(1, communication.list.size)
        viewModel.editZeroDaysCard(
            position = 0,
            card = Card.ZeroDays(text = "buy a book", id = 1L)
        )
        assertEquals(
            NewUiState.Replace(
                position = 0,
                card = Card.ZeroDaysEdit(text = "buy a book", id = 1L)
            ),
            communication.list[1]
        )
        assertEquals(2, communication.list.size)

        viewModel.deleteCard(position = 0, id = 1L)

        assertEquals(NewUiState.Remove(position = 0), communication.list[2])
        assertEquals(3, communication.list.size)
    }

    @Test
    fun `test delete zero days card when add card not present`() {
        val communication = FakeCommunication()
        val interactor =
            FakeInteractor(
                listOf(
                    Card.ZeroDays(text = "buy a book", id = 1L),
                    (Card.ZeroDays(text = "buy a phone", id = 2L))
                )
            )
        val viewModel = NewViewModel(communication, interactor)
        viewModel.init(isFirstRun = true)

        assertEquals(
            NewUiState.AddAll(
                listOf(
                    Card.ZeroDays(text = "buy a book", id = 1L),
                    Card.ZeroDays(text = "buy a book", id = 1L)
                )
            ),
            communication.list[0]
        )
        assertEquals(1, communication.list.size)


        viewModel.editZeroDaysCard(
            position = 1,
            card = Card.ZeroDays(text = "buy a book", id = 1L)
        )
        assertEquals(
            NewUiState.Replace(
                position = 1,
                card = Card.ZeroDaysEdit(text = "buy a phone", id = 2L)
            ),
            communication.list[1]
        )
        assertEquals(2, communication.list.size)

        viewModel.deleteCard(position = 1, id = 2L)
        assertEquals(NewUiState.Remove(position = 1), communication.list[2])
        assertEquals(NewUiState.Add(Card.Add), communication.list[3])
        assertEquals(4, communication.list.size)
    }

    @Test
    fun `test edit zero days card and save`() {
        val communication = FakeCommunication()
        val interactor =
            FakeInteractor(
                listOf(
                    Card.ZeroDays(text = "buy a book", id = 1L),
                    (Card.ZeroDays(text = "buy a phone", id = 2L))
                )
            )
        val viewModel = NewViewModel(communication, interactor)
        viewModel.init(isFirstRun = true)

        assertEquals(
            NewUiState.Add(
                Card.ZeroDays(text = "buy a book", id = 1L),
                Card.ZeroDays(text = "buy a book", id = 1L)
            ),
            communication.list[0]
        )
        assertEquals(1, communication.list.size)


        viewModel.editZeroDaysCard(position = 1)
        assertEquals(
            NewUiState.Replace(
                position = 1,
                card = Card.ZeroDaysEdit(text = "buy a phone", id = 2L)
            ),
            communication.list[1]
        )
        assertEquals(2, communication.list.size)

        viewModel.saveEditedZeroDaysCard(text = "buy a house", position = 1)
        assertEquals(
            NewUiState.Replace(
                position = 1,
                card = Card.ZeroDays(text = "buy a house", id = 2L)
            ),
            communication.list[2]
        )
        assertEquals(3, communication.list.size)

    }
}

private class FakeInteractor(private val cards: List<Card>) : NewMainInteractor() {

    var canAddNewCard: Boolean = true
    val canAddNewCardList = mutableListOf<Boolean>()
    var savedNewCardList = mutableListOf<String>()

    override fun cards(): List<Card> {
        return cards
    }

    override fun canAddNewCard(): Boolean {
        canAddNewCardList.add(canAddNewCard)
        return canAddNewCard
    }

    override fun newCard(text: String): Card {
        savedNewCardList.add(text)
        return Card.ZeroDays(text = text, id = 4L)
    }
}

private class FakeCommunication : NewMainCommunication.Mutable {

    val list = mutableListOf<NewUiState>()

    override fun put(value: NewUiState) {
        list.add(value)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<NewUiState>) = Unit
}

class VarargClass(private vararg val cards: String)

fun main() {
    VarargClass("a", "b")
}