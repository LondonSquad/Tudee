package com.london.tudee.presentation.screens.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemGesturesPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.london.tudee.R
import com.london.tudee.presentation.components.buttons.TudeeTextButton
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnBoardingHorizontalPager(
    onClickSkip: () -> Unit,
    viewModel: OnBoardingViewModel = koinViewModel()
) {
    val pagerState = rememberPagerState(pageCount = { OnBoardingContent.size })
    val coroutineScope = rememberCoroutineScope()
    val currentPage = viewModel.currentPage.collectAsState().value


    LaunchedEffect(pagerState.currentPage) {
        viewModel.onPageChanged(pagerState.currentPage)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        OnBoardingBackground()

        AnimatedVisibility(visible = currentPage != OnBoardingContent.size - 1) {
            TudeeTextButton(
                onClick = {
                    viewModel.markOnboardingSeen()
                    onClickSkip()
                },
                text = stringResource(R.string.skip),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .systemGesturesPadding(),
                isLoading = false,
                isDisabled = false,
            )
        }

        HorizontalPager(state = pagerState) { pageIndex ->
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Column {
                    OnboardingScreen(
                        title = OnBoardingContent[pageIndex].title,
                        body = OnBoardingContent[pageIndex].body,
                        image = OnBoardingContent[pageIndex].image,
                        modifier = Modifier.fillMaxHeight(0.9f),
                        onClickForward = {
                            viewModel.navigateNext(pagerState, coroutineScope)

                            if (pageIndex == OnBoardingContent.size - 1) {
                                viewModel.markOnboardingSeen()
                                onClickSkip()
                            }
                        }
                    )
                }
            }
        }

        StepIndicatorBar(
            currentPage,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .systemGesturesPadding()
        )
    }
}

@ThemePreviews
@Composable
fun PreviewOnboardingFlow() {
    TudeeTheme {
        OnBoardingHorizontalPager(onClickSkip = {})
    }
}