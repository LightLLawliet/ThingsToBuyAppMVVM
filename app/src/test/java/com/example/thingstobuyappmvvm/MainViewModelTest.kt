package com.example.thingstobuyappmvvm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import org.junit.Assert.assertEquals
import org.junit.Test

class MainViewModelTest {

    @Test
    fun `test 0 days`() {
        val repository = FakeRepository(0)
        val communication = FakeMainCommunication.Base()
        val viewModel = MainVewModel(repository, communication)
        viewModel.init(isFirstRun = true)
        assertEquals(true, communication.checkCalledCount(1))
        assertEquals(true, communication.isTheSame(UiState.ZeroDays))
        viewModel.init(isFirstRun = false)
        assertEquals(true, communication.checkCalledCount(1))
    }

    @Test
    fun `test N days`() {
        val repository = FakeRepository(5)
        val communication = FakeMainCommunication.Base()
        val viewModel = MainVewModel(repository, communication)
        viewModel.init(isFirstRun = true)
        assertEquals(true, communication.checkCalledCount(1))
        assertEquals(true, communication.isTheSame(UiState.NDays(days = 5)))
        viewModel.init(isFirstRun = false)
        assertEquals(true, communication.checkCalledCount(1))
    }
}

private class FakeRepository(private val days: Int) : MainRepository {

    override fun days(): Int = days
}

private interface FakeMainCommunication : MainCommunication.Mutable {

    fun checkCalledCount(count: Int): Boolean

    fun isTheSame(uiState: UiState): Boolean

    class Base : FakeMainCommunication {
        private lateinit var state: UiState
        private var callCount = 0

        override fun isTheSame(uiState: UiState): Boolean = state == uiState

        override fun checkCalledCount(count: Int): Boolean = count == callCount

        override fun put(value: UiState) {
            callCount++
            state = value
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<UiState>) = Unit
    }
}