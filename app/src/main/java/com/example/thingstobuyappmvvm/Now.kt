package com.example.thingstobuyappmvvm

interface Now {
    fun time(): Long

    class Base : Now {
        override fun time() = System.currentTimeMillis()
    }
}

