package com.example.thingstobuyappmvvm

interface MainRepository {

    fun days(): Int

    fun reset()

    class Base(
        private val cacheDataSource: CacheDataSource,
        private val now: Now
    ) : MainRepository {

        override fun days(): Int {
            val saved = cacheDataSource.time(-1)
            if (saved == -1L) {
                reset()
                return 0
            } else {
                val diff = now.time() - saved
                return (diff / (DAY)).toInt()
            }
        }

        companion object {
            private const val DAY = 24 * 3600 * 1000
        }

        override fun reset() {
            cacheDataSource.save(now.time())
        }
    }
}

interface Now {
    fun time(): Long

    class Base : Now {
        override fun time() = System.currentTimeMillis()
    }
}
