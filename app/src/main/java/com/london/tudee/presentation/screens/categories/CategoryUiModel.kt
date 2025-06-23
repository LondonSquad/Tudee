package com.london.tudee.presentation.screens.categories

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.TudeeTheme

data class CategoryUiModel(
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
    val tint: Color,
    val count: Int
)

@Composable
fun rememberSampleCategories(): List<CategoryUiModel> {
    val colors = TudeeTheme.colors
    return listOf(
        CategoryUiModel(
            iconRes = R.drawable.ic_education,
            title = R.string.category_education,
            tint = colors.purpleAccent,
            count = 16
        ),
        CategoryUiModel(
            iconRes = R.drawable.ic_shopping,
            title = R.string.category_shopping,
            tint = colors.secondary,
            count = 23
        ),
        CategoryUiModel(
            iconRes = R.drawable.ic_medical,
            title = R.string.category_medical,
            tint = colors.primary,
            count = 4
        ),
        CategoryUiModel(
            iconRes = R.drawable.ic_gym,
            title = R.string.category_gym,
            tint = colors.primary,
            count = 15
        ),
        CategoryUiModel(
            iconRes = R.drawable.ic_entertainment,
            title = R.string.category_entertainment,
            tint = colors.yellowAccent,
            count = 33
        ),
        CategoryUiModel(
            iconRes = R.drawable.ic_cooking,
            title = R.string.category_cooking,
            tint = colors.pinkAccent,
            count = 0
        ),
        CategoryUiModel(
            iconRes = R.drawable.ic_family,
            title = R.string.category_family_friend,
            tint = colors.secondary,
            count = 4
        ),
        CategoryUiModel(
            iconRes = R.drawable.ic_travel,
            title = R.string.category_traveling,
            tint = colors.yellowAccent,
            count = 0
        ),
        CategoryUiModel(
            iconRes = R.drawable.ic_agriculture,
            title = R.string.category_agriculture,
            tint = colors.greenAccent,
            count = 34
        ),
        CategoryUiModel(
            iconRes = R.drawable.ic_coding,
            title = R.string.category_coding,
            tint = colors.purpleAccent,
            count = 10
        ),
        CategoryUiModel(
            iconRes = R.drawable.ic_adoration,
            title = R.string.category_adoration,
            tint = colors.primary,
            count = 45
        ),
        CategoryUiModel(
            iconRes = R.drawable.ic_bug_fix,
            title = R.string.category_fixing_bugs,
            tint = colors.pinkAccent,
            count = 3
        ),
        CategoryUiModel(
            iconRes = R.drawable.ic_cleaning,
            title = R.string.category_cleaning,
            tint = colors.greenAccent,
            count = 58
        ),
        CategoryUiModel(
            iconRes = R.drawable.ic_work,
            title = R.string.category_work,
            tint = colors.secondary,
            count = 1
        ),
        CategoryUiModel(
            iconRes = R.drawable.ic_budgeting,
            title = R.string.category_budgeting,
            tint = colors.purpleAccent,
            count = 23
        ),
        CategoryUiModel(
            iconRes = R.drawable.ic_self_care,
            title = R.string.category_self_care,
            tint = colors.yellowAccent,
            count = 44
        ),
        CategoryUiModel(
            iconRes = R.drawable.ic_event,
            title = R.string.category_event,
            tint = colors.pinkAccent,
            count = 2
        )
    )
}