package com.london.tudee.presentation.screens.categories

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.TudeeTheme

data class CategoryUiModel(
    val categoryId: Int? = null,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
    val count: Int,
    val isSelected: Boolean = false
)

@Composable
fun rememberSampleCategories(): List<CategoryUiModel> {
    val colors = TudeeTheme.colors
    return listOf(
        CategoryUiModel(
            categoryId = 1,
            iconRes = R.drawable.ic_education,
            title = R.string.category_education,
            count = 16
        ),
        CategoryUiModel(
            categoryId = 2,
            iconRes = R.drawable.ic_shopping,
            title = R.string.category_shopping,
            count = 23
        ),
        CategoryUiModel(
            categoryId = 3,
            iconRes = R.drawable.ic_medical,
            title = R.string.category_medical,
            count = 4
        ),
        CategoryUiModel(
            categoryId = 4,
            iconRes = R.drawable.ic_gym,
            title = R.string.category_gym,
            count = 15
        ),
        CategoryUiModel(
            categoryId = 5,
            iconRes = R.drawable.ic_entertainment,
            title = R.string.category_entertainment,
            count = 33
        ),
        CategoryUiModel(
            categoryId = 6,
            iconRes = R.drawable.ic_cooking,
            title = R.string.category_cooking,
            count = 0
        ),
        CategoryUiModel(
            categoryId = 7,
            iconRes = R.drawable.ic_family,
            title = R.string.category_family_friend,
            count = 4
        ),
        CategoryUiModel(
            categoryId = 8,
            iconRes = R.drawable.ic_travel,
            title = R.string.category_traveling,
            count = 0
        ),
        CategoryUiModel(
            categoryId = 9,
            iconRes = R.drawable.ic_agriculture,
            title = R.string.category_agriculture,
            count = 34
        ),
        CategoryUiModel(
            categoryId = 10,
            iconRes = R.drawable.ic_coding,
            title = R.string.category_coding,
            count = 10
        ),
        CategoryUiModel(
            categoryId = 11,
            iconRes = R.drawable.ic_adoration,
            title = R.string.category_adoration,
            count = 45
        ),
        CategoryUiModel(
            categoryId = 12,
            iconRes = R.drawable.ic_bug_fix,
            title = R.string.category_fixing_bugs,
            count = 3
        ),
        CategoryUiModel(
            categoryId = 13,
            iconRes = R.drawable.ic_cleaning,
            title = R.string.category_cleaning,
            count = 58
        ),
        CategoryUiModel(
            categoryId = 14,
            iconRes = R.drawable.ic_work,
            title = R.string.category_work,
            count = 1
        ),
        CategoryUiModel(
            categoryId = 15,
            iconRes = R.drawable.ic_budgeting,
            title = R.string.category_budgeting,
            count = 23
        ),
        CategoryUiModel(
            categoryId = 16,
            iconRes = R.drawable.ic_self_care,
            title = R.string.category_self_care,
            count = 44
        ),
        CategoryUiModel(
            categoryId = 17,
            iconRes = R.drawable.ic_event,
            title = R.string.category_event,
            count = 2
        )
    )
}