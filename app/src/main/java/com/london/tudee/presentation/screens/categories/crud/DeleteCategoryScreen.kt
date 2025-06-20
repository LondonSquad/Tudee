package com.london.tudee.presentation.screens.categories.crud

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.domain.entities.Category
import com.london.tudee.presentation.components.bottom_sheet.TudeeBottomSheetScreen
import com.london.tudee.presentation.components.buttons.TudeeNegativeButton
import com.london.tudee.presentation.components.buttons.TudeeSecondaryButton
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun DeleteCategoryScreen(
    modifier: Modifier = Modifier,
    category: Category,
    onDismiss: () -> Unit
) {
    TudeeBottomSheetScreen(
        showBottomSheet = true,
        modifier = modifier,
        onDismiss = {},
        screenContent = {},
        bottomSheetActions = {},
        bottomSheetContent = {
            DeleteCategoryContent(
                modifier = modifier,
                category = category,
                onCancel = onDismiss
            )
        }
    )
}

@Composable
fun DeleteCategoryContent(
    category: Category,
    modifier: Modifier = Modifier,
    onCancel: () -> Unit,
    viewModel: DeleteCategoryScreenViewModel = koinViewModel()
) {


    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Text(
            text = stringResource(R.string.delete_category),
            style = TudeeTheme.typography.headlineMedium,
            color = TudeeTheme.colors.title,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.delete_task_message),
            style = TudeeTheme.typography.bodyMedium,
            color = TudeeTheme.colors.body,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(R.drawable.tudee_delete),
                contentDescription = null,
                modifier = Modifier.size(width = 107.dp, height = 100.dp)
            )
        }

        Spacer(modifier = Modifier.height(36.dp))


        TudeeNegativeButton(
            text = stringResource(R.string.delete),
            onClick = {
                viewModel.deleteCategory(category)
                if (viewModel.uiState.value.isDeleted) {
                    onCancel()
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        TudeeSecondaryButton(
            text = stringResource(R.string.cancel),
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@ThemePreviews
@Composable
private fun DeleteCategoryScreenPreview() {
    TudeeTheme {
        DeleteCategoryScreen(
            modifier = Modifier,
            category = Category(
                id = 1,
                name = "Work",
                arName = "العمل",
                iconPath = "ic_work",
                isDefault = true
            ),
            onDismiss = {}
        )
    }
}