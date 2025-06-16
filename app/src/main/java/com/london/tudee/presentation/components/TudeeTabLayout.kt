package com.london.tudee.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import kotlinx.coroutines.launch

data class TabItem(
    @StringRes val text: Int,
    val number: Int
)

@Composable
fun TudeeTabLayout(
    tabs: List<TabItem>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        tabs.forEachIndexed { index, tab ->
            TudeeTab(
                text = tab.text,
                number = tab.number,
                isSelected = index == selectedIndex,
                onClick = { onTabSelected(index) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TudeeTabLayoutWithPager(
    modifier: Modifier = Modifier,
    tabs: List<TabItem> = listOf(
        TabItem(text = R.string.in_progresss, number = 14),
        TabItem(text = R.string.to_do, number = 8),
        TabItem(text = R.string.done, number = 5)
    ),
    content: @Composable (page: Int) -> Unit
) {
    val pagerState = rememberPagerState { tabs.size }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        TudeeTabLayout(
            tabs = tabs,
            selectedIndex = pagerState.currentPage,
            onTabSelected = { index ->
                coroutineScope.launch { pagerState.animateScrollToPage(index) }
            },
            modifier = Modifier.fillMaxWidth()
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            content(page)
        }
    }
}


/* Usage Example */
@Composable
fun TabLayoutScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TudeeTheme.colors.surface)
    ) {
        TudeeTabLayoutWithPager { page ->
            // Content for each tab
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Center
            ) {
                Text(
                    text = "Content for tab $page",
                    style = TudeeTheme.typography.titleLarge,
                    color = TudeeTheme.colors.title,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }


        }
    }

}

@ThemePreviews
@Composable
private fun TudeeTabLayoutWithPagerPreview() {
    TudeeTheme {
        TabLayoutScreen()
    }
}