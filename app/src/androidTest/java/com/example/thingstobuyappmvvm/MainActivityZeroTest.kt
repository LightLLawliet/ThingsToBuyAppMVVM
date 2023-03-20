package com.example.thingstobuyappmvvm

import android.content.SharedPreferences
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers
import org.junit.Test

class MainActivityZeroTest : AbstractUiTest() {

    override fun init(sharedPref: SharedPreferences) {
        sharedPref.edit().clear().apply()
    }

    @Test
    fun test_n_days_and_reset() {
        val mainPage = MainPage()
        Espresso.onView(ViewMatchers.withId(mainPage.mainText))
            .check(ViewAssertions.matches(ViewMatchers.withText("0")))
        Espresso.onView(ViewMatchers.withId(mainPage.resetButton))
            .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
    }
}