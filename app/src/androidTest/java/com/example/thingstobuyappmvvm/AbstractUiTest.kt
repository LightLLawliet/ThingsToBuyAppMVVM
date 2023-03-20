package com.example.thingstobuyappmvvm

import android.content.Intent
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class AbstractUiTest {
    @get:Rule
    val activityScenarioRule = lazyActivityScenarioRule<MainActivity>(launchActivity = false)

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<App>()
        val sharedPreferences = SharedPref.Test().make(context)
        init(sharedPreferences)
        activityScenarioRule.launch(Intent(context, MainActivity::class.java))
    }

    protected abstract fun init(sharedPref: SharedPreferences)
}