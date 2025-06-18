package com.london.tudee.presentation.screens.tasks

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.components.CategoryItem
import com.london.tudee.presentation.components.TudeeTextField
import com.london.tudee.presentation.components.priority.Priority
import com.london.tudee.presentation.components.priority.PrioritySelector
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.screens.categories.CategoryUiModel
import com.london.tudee.presentation.screens.categories.rememberSampleCategories

@Composable
fun AddOrEditTaskDetails(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
) {
    TaskDetailsContent(
        modifier = modifier,
        title = title
    )
}

@Composable
private fun TaskDetailsContent(
    modifier: Modifier,
    @StringRes title: Int
) {
    var selectedPriority by remember { mutableStateOf(Priority.HIGH) }
    var selectedCategory by remember { mutableStateOf<CategoryUiModel?>(null) }
    val categories = rememberSampleCategories()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TudeeTheme.colors.surface)
    ) {
        TaskHeader(title)
        TaskInputFields()
        PrioritySection(selectedPriority) { selectedPriority = it }
        CategorySection(categories, selectedCategory) { selectedCategory = it }
    }
}

@Composable
private fun TaskHeader(@StringRes title: Int) {
    Text(
        text = stringResource(title),
        style = TudeeTheme.typography.titleLarge,
        color = TudeeTheme.colors.title,
    )
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
private fun TaskInputFields() {
    TudeeTextField(
        icon = R.drawable.add_task_icon,
        hint = R.string.task_title,
        value = "",
        onValueChange = {}
    )
    Spacer(modifier = Modifier.height(16.dp))

    TudeeTextField(
        multiLined = true,
        hint = R.string.description,
        value = "",
        onValueChange = {}
    )
    Spacer(modifier = Modifier.height(16.dp))

    TudeeTextField(
        icon = R.drawable.add_date_icon,
        hint = R.string.select_date,
        value = "",
        onValueChange = {}
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun PrioritySection(
    selectedPriority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    SectionTitle(titleRes = R.string.priority)
    Spacer(modifier = Modifier.height(8.dp))

    PrioritySelector(
        selectedPriority = selectedPriority,
        onPrioritySelected = onPrioritySelected
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun CategorySection(
    categories: List<CategoryUiModel>,
    selectedCategory: CategoryUiModel?,
    onCategorySelected: (CategoryUiModel) -> Unit
) {
    SectionTitle(titleRes = R.string.category_title)
    Spacer(modifier = Modifier.height(8.dp))

    CategoriesGrid(
        categories = categories,
        selectedCategory = selectedCategory,
        onCategorySelected = onCategorySelected
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun SectionTitle(@StringRes titleRes: Int) {
    Text(
        text = stringResource(titleRes),
        style = TudeeTheme.typography.titleMedium,
        color = TudeeTheme.colors.title
    )
}

@Composable
private fun CategoriesGrid(
    categories: List<CategoryUiModel>,
    selectedCategory: CategoryUiModel?,
    onCategorySelected: (CategoryUiModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categories) { category ->
            CategoryItem(
                iconRes = category.iconRes,
                title = stringResource(category.title),
                tint = category.tint,
                isSelected = category == selectedCategory,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@ThemePreviews
@Composable
private fun PreviewAddOrEditTaskDetails() {
    TudeeTheme {
        AddOrEditTaskDetails(
            title = R.string.add_new_task,
        )
    }
}