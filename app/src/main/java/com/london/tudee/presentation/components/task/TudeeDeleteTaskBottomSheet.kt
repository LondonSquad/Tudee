package com.london.tudee.presentation.components.task

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.components.bottom_sheet.TudeeBottomSheet
import com.london.tudee.presentation.components.buttons.TudeeNegativeButton
import com.london.tudee.presentation.components.buttons.TudeeSecondaryButton
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun TudeeDeleteTaskBottomSheet(
    visible: Boolean,
    onDismiss: () -> Unit,
    onConfirmDelete: () -> Unit
) {
    if (!visible) return

    TudeeBottomSheet(
        visible = true,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Delete Task",
                    style = TudeeTheme.typography.headlineMedium,
                    color = TudeeTheme.colors.title,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Are you sure to continue?",
                    style = TudeeTheme.typography.bodyMedium,
                    color = TudeeTheme.colors.body,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Image(
                    painter = painterResource(R.drawable.tudee_delete),
                    contentDescription = null,
                    modifier = Modifier.size(width = 107.dp, height = 100.dp)
                )
            }
        },
        actions = {
            TudeeNegativeButton(
                text = "Delete",
                onClick = onConfirmDelete,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            TudeeSecondaryButton(
                text = "Cancel",
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@ThemePreviews
@Composable
fun TudeeDeleteTaskBottomSheetPreview() {
    TudeeTheme {
        TudeeDeleteTaskBottomSheet(
            visible = true,
            onDismiss = {},
            onConfirmDelete = {}
        )
    }
}
