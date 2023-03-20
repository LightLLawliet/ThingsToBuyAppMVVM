package com.example.thingstobuyappmvvm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData

class App : Application(), ProvideViewModel {

    private lateinit var viewModel: MainVewModel

    override fun onCreate() {
        super.onCreate()
        val sharedPref = if (BuildConfig.DEBUG) SharedPref.Test() else SharedPref.Base()
        viewModel = MainVewModel(
            MainRepository.Base(
                CacheDataSource.Base(sharedPref.make(this)),
                Now.Base()
            ),
            MainCommunication.Base(MutableLiveData())
        )
    }

    override fun provideMainViewModel(): MainVewModel {
        return viewModel
    }
}

interface ProvideViewModel {

    fun provideMainViewModel(): MainVewModel
}
