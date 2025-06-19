package com.london.tudee.presentation.screens.categories

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.london.tudee.R
import com.london.tudee.presentation.components.TudeeTextField
import com.london.tudee.presentation.components.bottom_sheet.TudeeBottomSheetScreen
import com.london.tudee.presentation.components.buttons.TudeePrimaryButton
import com.london.tudee.presentation.components.buttons.TudeeSecondaryButton
import com.london.tudee.presentation.design_system.color.RectBorderColor
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme


@Composable
fun EditCategoryScreen(
    modifier: Modifier = Modifier,
    categoryId: Int,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDismiss: () -> Unit
) {
    TudeeBottomSheetScreen(
        showBottomSheet = true,
        modifier = modifier,
        onDismiss = {},
        screenContent = {},
        bottomSheetActions = {},
        bottomSheetContent = {
            CategoryEditContent(
                modifier = modifier,
                categoryId = categoryId,
                onSaveClick = onSaveClick,
                onDeleteClick = onDeleteClick,
                onDismiss = onDismiss
            )
        }
    )
}

@Composable
fun CategoryEditContent(
    modifier: Modifier = Modifier,
    categoryId: Int,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDismiss: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.edit_category),
                style = TudeeTheme.typography.titleLarge,
                color = TudeeTheme.colors.title
            )
            Text(
                text = stringResource(R.string.delete),
                style = TudeeTheme.typography.labelLarge,
                color = TudeeTheme.colors.error,
                modifier = Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onDeleteClick,
                )
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        var text by remember { mutableStateOf("") }
        TudeeTextField(
            icon = R.drawable.add_category_icon,
            hint = R.string.category_name,
            value = text,
            onValueChange = { text = it },
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.category_image),
            style = TudeeTheme.typography.titleMedium,
            color = TudeeTheme.colors.title
        )

        Spacer(modifier = Modifier.height(12.dp))

        ImagePicker()

        Spacer(modifier = Modifier.height(36.dp))


        TudeePrimaryButton(
            onClick = onSaveClick,
            text = stringResource(R.string.save),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        TudeeSecondaryButton(
            onClick = onDismiss,
            text = stringResource(R.string.cancel),
            modifier = Modifier.fillMaxWidth()

        )
    }

}


@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
    onImagePicked: (Uri?) -> Unit = {}
) {

    var imageUri by remember { mutableStateOf<Uri?>(null) }


    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        onImagePicked(uri)
    }


    Box(
        modifier = modifier
            .size(112.dp)
            .clip(TudeeTheme.shapes.extraSmall)
            .drawBehind {
                drawRect(
                    color = RectBorderColor,
                    style = Stroke(
                        width = 1.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f), 0f)
                    )
                )
            }, contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = modifier
                .size(112.dp),
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .matchParentSize()
                        .clip(TudeeTheme.shapes.extraSmall),
                    contentScale = ContentScale.Crop
                )
            }

        }

        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(shape = TudeeTheme.shapes.extraSmall)
                .background(TudeeTheme.colors.surfaceHigh)
                .clickable {
                    imagePickerLauncher.launch("image/*")
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.pencil_edit_01),
                contentDescription = "Pick Image",
                tint = TudeeTheme.colors.secondary,
                modifier = Modifier.padding(6.dp)
            )
        }
    }

}

@ThemePreviews
@Composable
private fun EditCategoryScreenPreview() {
    TudeeTheme {
        EditCategoryScreen(
            modifier = Modifier,
            categoryId = 1,
            onSaveClick = {},
            onDeleteClick = {},
            onDismiss = {}
        )
    }
}