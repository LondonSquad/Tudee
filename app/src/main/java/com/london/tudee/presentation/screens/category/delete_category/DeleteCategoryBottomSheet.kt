package com.london.tudee.presentation.screens.category.delete_category

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DeleteCategoryScreen(
    category: Category,
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
    onCategoryDeleted: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteError: () -> Unit = {},
) {
    TudeeBottomSheetScreen(
        showBottomSheet = showBottomSheet,
        modifier = modifier,
        onDismiss = onDismiss,
        screenContent = {},
        bottomSheetActions = {},
        bottomSheetContent = {
            DeleteCategoryContent(
                modifier = modifier,
                category = category,
                onCancel = onDismiss,
                onCategoryDeleted = onCategoryDeleted,
                onDeleteError = onDeleteError
            )
        }
    )
}

@Composable
private fun DeleteCategoryContent(
    category: Category,
    modifier: Modifier = Modifier,
    onCancel: () -> Unit,
    onCategoryDeleted: () -> Unit,
    onDeleteError: () -> Unit = {},
    viewModel: DeleteCategoryScreenViewModel = koinViewModel()
) {
    val uiState by viewModel.deleteState.collectAsState()

    LaunchedEffect(uiState.isDeleted, uiState.errorMessage) {
        when {
            uiState.isDeleted -> onCategoryDeleted()
            uiState.errorMessage != null -> onDeleteError()
        }
    }
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
                if (viewModel.deleteState.value.isDeleted) {
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
                title = "Work",
                iconRes = "",
                isDefault = true,
                taskCount = 0,
            ),
            onDismiss = {},
            onCategoryDeleted = {},
            showBottomSheet = true
        )
    }
}