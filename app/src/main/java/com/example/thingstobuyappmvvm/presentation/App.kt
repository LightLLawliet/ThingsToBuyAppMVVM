package com.example.thingstobuyappmvvm.presentation

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.thingstobuyappmvvm.BuildConfig

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
