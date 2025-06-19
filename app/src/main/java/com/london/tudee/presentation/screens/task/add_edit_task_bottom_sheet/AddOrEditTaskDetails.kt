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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.london.tudee.R
import com.london.tudee.domain.entities.Priority
import com.london.tudee.presentation.components.CategoryItem
import com.london.tudee.presentation.components.TudeeTextField
import com.london.tudee.presentation.components.date.TudeeDatePicker
import com.london.tudee.presentation.components.priority.PrioritySelector
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.screens.categories.CategoryUiModel
import com.london.tudee.presentation.screens.categories.rememberSampleCategories
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddOrEditTaskDetails(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    onTitleValueChange: (String) -> Unit,
    onDescriptionValueChange: (String) -> Unit,
    onSelectedDateChange: () -> Unit,
    onSelectedPriorityChange: () -> Unit,
    onSelectedCategoryChange: () -> Unit,
    viewModel: AddOrEditTaskViewModel = viewModel(),
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
                onTitleValueChange = {
                    viewModel.updateTitle(it)
                    onTitleValueChange(it)
                },
                onDescriptionValueChange = {
                    viewModel.updateDescription(it)
                    onDescriptionValueChange(it)
                },
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
                viewModel.updateSelectedDate(date)
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
    onTitleValueChange: (String) -> Unit,
    onDescriptionValueChange: (String) -> Unit,
    onDateFieldClick: () -> Unit,
    onPrioritySelected: (Priority) -> Unit,
    onCategorySelected: (CategoryUiModel) -> Unit,
) {

    val categories = rememberSampleCategories()

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
    categories: List<CategoryUiModel>,
    selectedCategory: CategoryUiModel?,
    onCategorySelected: (CategoryUiModel) -> Unit
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
    categories: List<CategoryUiModel>,
    selectedCategory: CategoryUiModel?,
    onCategorySelected: (CategoryUiModel) -> Unit
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
                        iconRes = category.iconRes,
                        title = stringResource(category.title),
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

//@ThemePreviews
//@Composable
//private fun PreviewAddOrEditTaskDetails() {
//    TudeeTheme {
//        AddOrEditTaskDetails(
//            title = R.string.add_new_task,
//            onTitleValueChange = {},
//            onDescriptionValueChange = {},
//            onSelectedDateChange = {
//            },
//    }
//}

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