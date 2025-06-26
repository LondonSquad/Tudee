package com.london.tudee.presentation.screens.onboarding

import com.google.common.truth.Truth.assertThat
import com.london.tudee.domain.services.AppPreferencesService
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnBoardingViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var appPreferencesService: AppPreferencesService
    private lateinit var viewModel: OnBoardingViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        clearAllMocks()
        appPreferencesService = mockk(relaxed = true)
        viewModel = OnBoardingViewModel(appPreferencesService)
    }


    @Test
    fun `onPageChanged should update currentPage`() = runTest {
        // When
        viewModel.onPageChanged(2)
        // Then
        assertThat(viewModel.currentPage.value).isEqualTo(2)
    }


    @Test
    fun `navigateNext should not animate if on last page`() = runTest {
        // Given
        val pagerState = mockk<androidx.compose.foundation.pager.PagerState>()
        every { pagerState.currentPage } returns 2
        every { pagerState.pageCount } returns 3
        coEvery { pagerState.scrollToPage(any()) } just Runs
        coEvery { pagerState.animateScrollToPage(any(), any()) } just Runs
        val scope = CoroutineScope(testDispatcher)

        // When
        viewModel.navigateNext(pagerState, scope)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 0) { pagerState.scrollToPage(any()) }
        coVerify(exactly = 0) { pagerState.animateScrollToPage(any(), any()) }
    }

    @Test
    fun `onboardingFinished should call setOnboardingShown on appPreferencesService`() = runTest {
        // Given
        coEvery { appPreferencesService.setOnBoardingShown() } just Runs

        // When
        viewModel.onboardingFinished()
        advanceUntilIdle()

        // Then
        coVerify { appPreferencesService.setOnBoardingShown() }
    }
}