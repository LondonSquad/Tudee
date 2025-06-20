package com.london.tudee.presentation.screens.onboarding

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class OnBoardingViewModel(
    private val onboardingPrefs: OnboardingPreferences
) : ViewModel() {

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val _shouldShowOnboarding = MutableStateFlow(true)
    val shouldShowOnboarding: StateFlow<Boolean> = _shouldShowOnboarding.asStateFlow()

    init {
        viewModelScope.launch {
            val hasSeen = onboardingPrefs.hasSeenOnboarding()
            _shouldShowOnboarding.value = !hasSeen
        }
    }

    fun markOnboardingSeen() {
        viewModelScope.launch {
            onboardingPrefs.setOnboardingSeen(true)
            _shouldShowOnboarding.value = false
        }
    }

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
            }
        }
    }
}