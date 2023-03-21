package com.example.thingstobuyappmvvm.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.thingstobuyappmvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = (application as ProvideViewModel).provideMainViewModel()
        val textView = binding.mainTextView
        val resetButton = binding.resetButton
        viewModel.observe(this) { uiState ->
            uiState.apply(textView, resetButton)
        }

        resetButton.setOnClickListener {
            viewModel.reset()
        }
        viewModel.init(savedInstanceState == null)
    }
}