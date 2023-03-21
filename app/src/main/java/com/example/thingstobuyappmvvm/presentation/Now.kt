package com.example.thingstobuyappmvvm.presentation

interface Now {
    fun time(): Long

    class Base : Now {
        override fun time() = System.currentTimeMillis()
    }
}

