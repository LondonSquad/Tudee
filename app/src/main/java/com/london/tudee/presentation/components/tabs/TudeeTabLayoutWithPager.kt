package com.london.tudee.presentation.components.tabs

import androidx.annotation.StringRes
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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.london.tudee.R
import com.london.tudee.domain.entities.Task
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import kotlinx.coroutines.launch

@Immutable
data class TabItem(
    @StringRes val text: Int,
    val number: Int
)

@Composable
fun TudeeTabLayout(
    tabs: List<TabItem>,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    onTabSelected: (Int) -> Unit
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

@Composable
fun TudeeTabLayoutWithPager(
    modifier: Modifier = Modifier,
    initialTabIndex: Int = 0,
    tabs: List<TabItem>,
    tasksList: List<List<Task>> = emptyList(),
    headerContent: (@Composable () -> Unit)? = null,
    content: @Composable (page: Int, tasks: List<Task>) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = initialTabIndex
    ) { tabs.size }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        headerContent?.invoke()
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
            val tasks = tasksList[page]
            content(page, tasks)
        }
    }
}


@Composable
fun TabLayoutScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TudeeTheme.colors.surface)
    ) {
        TudeeTabLayoutWithPager(
            tabs = listOf(
                TabItem(text = R.string.In_Progress, number = 14),
                TabItem(text = R.string.To_Do, number = 8),
                TabItem(text = R.string.Done, number = 5)
            )
        ) { page, _ ->
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