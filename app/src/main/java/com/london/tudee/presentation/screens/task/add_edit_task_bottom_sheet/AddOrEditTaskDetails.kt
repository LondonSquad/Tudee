package com.london.tudee.presentation.screens.task.add_edit_task_bottom_sheet

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.london.tudee.R
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Priority
import com.london.tudee.presentation.components.CategoryItem
import com.london.tudee.presentation.components.TudeeTextField
import com.london.tudee.presentation.components.date.TudeeDatePicker
import com.london.tudee.presentation.components.priority.PrioritySelector
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.mapper.CategoryMapper
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddOrEditTaskDetails(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    viewModel: AddOrEditTaskViewModel = koinViewModel(),
    categories: List<Category> = emptyList()
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val maxHeight = screenHeight * 0.75f
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = maxHeight)
            .background(TudeeTheme.colors.surface)
    ) {
        Box(
            modifier = Modifier
                .weight(1f, fill = false)
                .verticalScroll(rememberScrollState())
        ) {
            TaskDetailsContent(
                title = title,
                uiState = uiState,
                categories = categories.ifEmpty { uiState.categories },
                onTitleValueChange = { viewModel.updateTitle(it) },
                onDescriptionValueChange = { viewModel.updateDescription(it) },
                onDateFieldClick = { viewModel.showDatePicker() },
                onPrioritySelected = { viewModel.updateSelectedPriority(it) },
                onCategorySelected = { viewModel.updateSelectedCategory(it) },
                modifier = modifier.fillMaxWidth()
            )
        }
    }

    if (uiState.showDatePicker) {
        TudeeDatePicker(
            onDateSelected = { date ->
                viewModel.updateSelectedDate(date ?: System.currentTimeMillis())
                viewModel.hideDatePicker()
            },
            onDismiss = {
                viewModel.hideDatePicker()
            }
        )
    }
}
@Composable
private fun TaskDetailsContent(
    modifier: Modifier,
    @StringRes title: Int,
    uiState: AddOrEditTaskUiState,
    categories: List<Category>,
    onTitleValueChange: (String) -> Unit,
    onDescriptionValueChange: (String) -> Unit,
    onDateFieldClick: () -> Unit,
    onPrioritySelected: (Priority) -> Unit,
    onCategorySelected: (Category) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        TaskHeader(title)
        TaskInputFields(
            title = uiState.title,
            description = uiState.description,
            selectedDate = uiState.selectedDate,
            onTitleValueChange = onTitleValueChange,
            onDescriptionValueChange = onDescriptionValueChange,
            onDateFieldClick = onDateFieldClick
        )
        PrioritySection(
            selectedPriority = uiState.selectedPriority,
            onPrioritySelected = onPrioritySelected
        )
        CategorySection(
            categories = categories,
            selectedCategory = uiState.selectedCategory,
            onCategorySelected = onCategorySelected
        )
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
private fun TaskInputFields(
    title: String,
    description: String,
    selectedDate: Long?,
    onTitleValueChange: (String) -> Unit,
    onDescriptionValueChange: (String) -> Unit,
    onDateFieldClick: () -> Unit
) {
    TudeeTextField(
        icon = R.drawable.add_task_icon,
        hint = R.string.task_title,
        value = title,
        onValueChange = onTitleValueChange
    )
    Spacer(modifier = Modifier.height(16.dp))

    TudeeTextField(
        multiLined = true,
        hint = R.string.description,
        value = description,
        onValueChange = onDescriptionValueChange
    )
    Spacer(modifier = Modifier.height(16.dp))

    val dateFormatter = SimpleDateFormat("dd, MM, yyyy", Locale.getDefault())
    val dateText = selectedDate?.let { dateFormatter.format(Date(it)) } ?: ""

    TudeeTextField(
        icon = R.drawable.add_date_icon,
        hint = R.string.select_date,
        value = dateText,
        onValueChange = {},
        readOnly = true,
        onClick = onDateFieldClick
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
    categories: List<Category>,
    selectedCategory: Category?,
    onCategorySelected: (Category) -> Unit
) {
    SectionTitle(titleRes = R.string.category)
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
    categories: List<Category>,
    selectedCategory: Category?,
    onCategorySelected: (Category) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        categories.chunked(3).forEach { rowCategories ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowCategories.forEach { category ->
                    CategoryItem(
                        modifier = Modifier.weight(1f),
                        iconRes = CategoryMapper.getIconResource(category),
                        title = CategoryMapper.getCategoryDisplayName(category),
                        isSelected = category == selectedCategory,
                        onClick = { onCategorySelected(category) }
                    )

                    if (category != rowCategories.last()) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }

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
private fun PreviewCategorySection() {
    TudeeTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(TudeeTheme.colors.surface)
                .padding(16.dp)
        ) {
            var selectedCategory by remember { mutableStateOf<Category?>(null) }
            val sampleCategories = rememberSampleDomainCategories()

            CategorySection(
                categories = sampleCategories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )
        }
    }
}

@Composable
private fun rememberSampleDomainCategories(): List<Category> {
    return remember {
        listOf(
            Category(
                id = 1,
                name = "Education",
                arName = "التعليم",
                iconPath = "ic_education",
                isDefault = true
            ),
            Category(
                id = 2,
                name = "Shopping",
                arName = "التسوق",
                iconPath = "ic_shopping",
                isDefault = true
            ),
            Category(
                id = 3,
                name = "Medical",
                arName = "طبي",
                iconPath = "ic_medical",
                isDefault = true
            ),
            Category(
                id = 4,
                name = "Gym",
                arName = "رياضة",
                iconPath = "ic_gym",
                isDefault = false
            ),
            Category(
                id = 5,
                name = "Entertainment",
                arName = "ترفيه",
                iconPath = "ic_entertainment",
                isDefault = false
            ),
            Category(
                id = 6,
                name = "Cooking",
                arName = "طبخ",
                iconPath = "ic_cooking",
                isDefault = false
            ),
            Category(
                id = 7,
                name = "Family & Friends",
                arName = "العائلة والأصدقاء",
                iconPath = "ic_family",
                isDefault = false
            ),
            Category(
                id = 8,
                name = "Traveling",
                arName = "سفر",
                iconPath = "ic_travel",
                isDefault = false
            ),
            Category(
                id = 9,
                name = "Agriculture",
                arName = "زراعة",
                iconPath = "ic_agriculture",
                isDefault = false
            )
        )
    }
}