import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.pager.PagerState

@OptIn(ExperimentalFoundationApi::class)
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
