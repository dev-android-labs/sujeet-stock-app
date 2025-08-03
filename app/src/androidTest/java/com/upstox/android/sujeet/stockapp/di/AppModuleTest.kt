package com.upstox.android.sujeet.stockapp.di

/**
 * Created by SUJEET KUMAR on 8/3/2025
 */
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.upstox.android.sujeet.stockapp.data.local.dao.HoldingsDao
import com.upstox.android.sujeet.stockapp.data.remote.HoldingsApi
import com.upstox.android.sujeet.stockapp.domain.repository.HoldingsRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class AppModuleTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject lateinit var retrofit: Retrofit
    @Inject lateinit var api: HoldingsApi
    @Inject lateinit var dao: HoldingsDao
    @Inject lateinit var repository: HoldingsRepository

    @Test
    fun verifyDependenciesInjected() {
        hiltRule.inject()

        assertNotNull(retrofit)
        assertNotNull(api)
        assertNotNull(dao)
        assertNotNull(repository)
    }

    @Test
    fun verifyRetrofitBaseUrl() {
        hiltRule.inject()
        val baseUrl = retrofit.baseUrl().toString()
        assert(baseUrl.contains("api.mockbin.io"))
    }

    @Test
    fun repositoryFetchDoesNotCrash() = runBlocking {
        hiltRule.inject()
        // This just ensures repository can be called without DI issues
        // It will return Success or Error depending on connectivity
        val result = repository.fetchHoldings()
        assertNotNull(result)
    }
}
