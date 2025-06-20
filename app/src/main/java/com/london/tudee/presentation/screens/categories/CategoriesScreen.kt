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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.domain.entities.Category
import com.london.tudee.presentation.components.CategoryItem
import com.london.tudee.presentation.components.buttons.TudeeFloatingActionButton
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(
    @StringRes screenTitle: Int = R.string.categories,
    viewModel: CategoriesViewModel = koinViewModel(),
    onCategoryClick: (Category) -> Unit,
    onAddCategoryClick: () -> Unit
) {
    val categories by viewModel.categoryUiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
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

            // Show error message if there's an error
            uiState.errorMessage?.let { error ->
                Text(
                    text = "Error: $error",
                    color = TudeeTheme.colors.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
            
            // Show loading indicator
            if (uiState.isLoading) {
                Text(
                    text = "Loading categories...",
                    modifier = Modifier.padding(16.dp)
                )
            }
            
            // Show empty state or categories
            if (!uiState.isLoading && uiState.errorMessage == null) {
                if (categories.isEmpty()) {
                    Text(
                        text = "No categories found",
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
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
                                title = category.name,
                                tint = category.tint,
                                count = category.taskCount,
                                onClick = { onCategoryClick(category) }
                            )
                        }
                    }
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
            onCategoryClick = {},
            onAddCategoryClick = {}
        )
    }
}