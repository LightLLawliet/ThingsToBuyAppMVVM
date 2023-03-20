package com.example.thingstobuyappmvvm

import org.junit.Assert.assertEquals
import org.junit.Test

class MainRepositoryTest {

    @Test
    fun `zero days`() {
        val cacheDataSource = FakeDataSource()
        val now = FakeNow.Base()
        val repository = MainRepository.Base(cacheDataSource, now)
        now.addTime(1544)
        val actual = repository.days()
        val expected: Long = 0
        assertEquals(expected, actual)
        assertEquals(1544, cacheDataSource.time(-1))
    }

    @Test
    fun `some days`() {
        val cacheDataSource = FakeDataSource()
        val now = FakeNow.Base()
        cacheDataSource.save(2 * 24 * 3600 * 1000)
        now.addTime(9 * 24 * 3600 * 1000)
        val repository = MainRepository.Base(cacheDataSource, now)
        val actual = repository.days()
        val expected: Long = 7
        assertEquals(expected, actual)
    }

    @Test
    fun `test reset`() {
        val cacheDataSource = FakeDataSource()
        val now = FakeNow.Base()
        val repository = MainRepository.Base(cacheDataSource, now)
        now.addTime(54321)
        repository.reset()
        assertEquals(54321, cacheDataSource.time(-1))
    }
}

private interface FakeNow : Now {

    fun addTime(difference: Long)

    class Base : FakeNow {
        private var time = 0L

        override fun time(): Long = time

        override fun addTime(difference: Long) {
            this.time = difference
        }
    }
}

private class FakeDataSource : CacheDataSource {

    private var time: Long = -100

    override fun save(time: Long) {
        this.time = time
    }

    override fun time(default: Long): Long {
        return if (time == -100L) default else time
    }
}