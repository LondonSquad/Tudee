package com.london.tudee.presentation.screens.onboarding

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.pager.PagerState
import com.google.common.truth.Truth.assertThat
import com.london.tudee.domain.services.AppPreferencesService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnBoardingViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var appPreferencesService: AppPreferencesService
    private lateinit var viewModel: OnBoardingViewModel
    private lateinit var pagerState: PagerState

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        appPreferencesService = mockk(relaxed = true)
        pagerState = mockk(relaxed = true)
        viewModel = OnBoardingViewModel(appPreferencesService)
    }

    @Test
    fun `onPageChanged should update currentPage state`() = runTest {
        // Given
        val newPage = 2

        // When
        viewModel.onPageChanged(newPage)

        // Then
        assertThat(viewModel.currentPage.value).isEqualTo(newPage)
    }

    @Test
    fun `navigateNext should scroll to next page when not at last page`() = runTest {
        // Given
        every { pagerState.currentPage } returns 1
        every { pagerState.pageCount } returns 3
        coEvery { pagerState.scrollToPage(any()) } returns Unit
        coEvery { pagerState.animateScrollToPage(any(), any()) } returns Unit

        // When
        viewModel.navigateNext(pagerState, this)

        // Then
        coVerify { pagerState.scrollToPage(1) }
        coVerify {
            pagerState.animateScrollToPage(
                page = 2,
                animationSpec = tween(durationMillis = 750, easing = FastOutSlowInEasing)
            )
        }
    }


    @Test
    fun `onboardingFinished should call setOnboardingShown`() = runTest {
        // Given
        coEvery { appPreferencesService.setOnboardingShown() } returns Unit

        // When
        viewModel.onboardingFinished()


        // Then
        coVerify { appPreferencesService.setOnboardingShown() }
    }

}