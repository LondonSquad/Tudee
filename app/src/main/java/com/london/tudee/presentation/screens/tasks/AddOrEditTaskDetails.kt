package com.london.tudee.presentation.screens.tasks

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
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
    // Get screen height minus some padding
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val maxHeight = screenHeight * 0.75f // Leave 25% space at top

    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = maxHeight) // Critical constraint
            .background(TudeeTheme.colors.surface)
    ) {
        // This Box provides proper scrolling boundaries
        Box(
            modifier = Modifier
                .weight(1f, fill = false) // Takes available space without forcing full height
                .verticalScroll(rememberScrollState())
        ) {
            TaskDetailsContent(
                title = title,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
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
            .padding(16.dp) // Add internal padding
    ) {
        TaskHeader(title)
        TaskInputFields()
        PrioritySection(selectedPriority) { selectedPriority = it }
        CategorySection(categories, selectedCategory) { selectedCategory = it }

        // Add bottom spacer to prevent last item from sticking to edge
        Spacer(modifier = Modifier.height(32.dp))
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Split categories into rows of 3
        categories.chunked(3).forEach { rowCategories ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowCategories.forEach { category ->
                    CategoryItem(
                        modifier = Modifier.weight(1f),
                        iconRes = category.iconRes,
                        title = stringResource(category.title),
                        tint = category.tint,
                        isSelected = category == selectedCategory,
                        onClick = { onCategorySelected(category) }
                    )

                    // Add spacing between items, except for the last item in the row
                    if (category != rowCategories.last()) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }

                // Fill empty spaces in the last row if needed
                val emptySpaces = 3 - rowCategories.size
                if (emptySpaces > 0) {
                    repeat(emptySpaces) {
                        Spacer(modifier = Modifier.weight(1f))
                        if (it < emptySpaces - 1) {
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                    }
                }
            }
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

@ThemePreviews
@Composable
private fun PreviewCategorySection() {
    TudeeTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(TudeeTheme.colors.surface)
                .padding(16.dp)
        ) {
            var selectedCategory by remember { mutableStateOf<CategoryUiModel?>(null) }
            val categories = rememberSampleCategories()

            CategorySection(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )
        }
    }
}