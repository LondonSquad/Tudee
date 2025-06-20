package com.london.tudee.presentation.screens.categories

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.components.CategoryItem
import androidx.compose.foundation.lazy.grid.items
import com.london.tudee.presentation.components.buttons.TudeeFloatingActionButton
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun CategoriesScreen(
    @StringRes screenTitle: Int = R.string.categories,
    categories: List<CategoryUiModel> = getSampleCategories(TudeeTheme.colors),
    onCategoryClick: (Int) -> Unit,
    onAddCategoryClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TudeeTheme.colors.surface)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TudeeTheme.colors.surfaceHigh)
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                Text(
                    text = stringResource(screenTitle),
                    style = TudeeTheme.typography.headlineSmall,
                    color = TudeeTheme.colors.title,
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 12.dp, bottom = 12.dp)
            ) {
                items(categories) { category ->
                    CategoryItem(
                        iconRes = category.iconRes,
                        title = stringResource(category.title),
                        count = category.count,
                        isSelected = category.isSelected,
                        onClick = { onCategoryClick(category.id) }
                    )
                }
            }
        }

        TudeeFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp),
            painter = painterResource(id = R.drawable.ic_add_category_button),
            isEnabled = true,
            contentDescription = stringResource(R.string.fab_content_description),
            onClick = onAddCategoryClick,
        )
    }
}

@ThemePreviews
@Composable
fun CategoriesScreenPreview() {
    TudeeTheme {
        CategoriesScreen(
            screenTitle = R.string.categories,
            categories = getSampleCategories(TudeeTheme.colors),
            onCategoryClick = {},
            onAddCategoryClick = {}
        )
    }
}