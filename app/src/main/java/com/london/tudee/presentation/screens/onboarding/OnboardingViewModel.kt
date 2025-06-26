package com.london.tudee.presentation.screens.onboarding

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.domain.services.AppPreferencesService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class OnBoardingViewModel(
    private val appPreferencesService: AppPreferencesService
) : ViewModel() {

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    fun onPageChanged(page: Int) {
        _currentPage.value = page
    }

    fun navigateNext(pagerState: PagerState, scope: CoroutineScope) {
        scope.launch {
            val nextPage = pagerState.currentPage + 1
            if (nextPage <= pagerState.pageCount - 1) {
                pagerState.scrollToPage(pagerState.currentPage)
                pagerState.animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(durationMillis = 750, easing = FastOutSlowInEasing)
                )
                onPageChanged(nextPage)
            }
        }
    }

    fun onboardingFinished() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { appPreferencesService.setOnBoardingShown() }.onFailure {
                    Log.e(
                        "OnboardingViewModel",
                        "onboardingFinished: ",
                        it
                    )
                }
        }
    }

    fun onPageSelected(page: Int, pagerState: PagerState, scope: CoroutineScope) {
        scope.launch {
            if (page in 0 until pagerState.pageCount) {
                pagerState.animateScrollToPage(
                    page = page,
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                )
                onPageChanged(page)
            }
        }
    }
}