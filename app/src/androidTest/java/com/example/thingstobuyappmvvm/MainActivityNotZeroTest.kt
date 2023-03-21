package com.example.thingstobuyappmvvm

import android.content.SharedPreferences
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.thingstobuyappmvvm.presentation.CacheDataSource
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityNotZeroTest : AbstractUiTest() {

    override fun init(sharedPref: SharedPreferences) {
        CacheDataSource.Base(sharedPref).save(System.currentTimeMillis() - 17L * 24 * 3600 * 1000)
    }

    @Test
    fun test_n_days_and_reset() {
        val mainPage = MainPage()
        onView(withId(mainPage.mainText)).check(matches(withText("17")))
        onView(withId(mainPage.resetButton)).check(matches(isDisplayed()))
        onView(withId(mainPage.resetButton)).perform(click())
        onView(withId(mainPage.mainText)).check(matches(withText("0")))
        onView(withId(mainPage.resetButton)).check(matches(not(isDisplayed())))
    }


}