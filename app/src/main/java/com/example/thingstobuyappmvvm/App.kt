package com.example.thingstobuyappmvvm

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData

class App : Application(), ProvideViewModel {

    private lateinit var viewModel: MainVewModel

    override fun onCreate() {
        super.onCreate()
        viewModel = MainVewModel(
            MainRepository.Base(
                CacheDataSource.Base(
                    getSharedPreferences("base", Context.MODE_PRIVATE),
                ),
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