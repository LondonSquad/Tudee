package com.london.tudee.presentation.screens.category.category_details

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.london.tudee.R
import com.london.tudee.domain.entities.Category
import com.london.tudee.presentation.components.TudeeTextField
import com.london.tudee.presentation.components.buttons.TudeePrimaryButton
import com.london.tudee.presentation.components.buttons.TudeeSecondaryButton
import com.london.tudee.presentation.design_system.color.RectBorderColor
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.utils.galleryImageToBitmap
import com.london.tudee.presentation.utils.saveImageToInternalStorage
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailsEditBottomSheet(
    category: Category,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    onDismiss: () -> Unit,
    onEditSuccess: () -> Unit = {},
    onEditError: () -> Unit = {}
) {

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        ),
        windowInsets = WindowInsets(0),
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                modifier = Modifier.background(TudeeTheme.colors.surface),
            )
        },
        containerColor = TudeeTheme.colors.surface
    ){
        CategoryDetailsEditContent(
            category = category,
            onDismiss = onDismiss,
            onDeleteClick = onDeleteClick,
            onEditSuccess = onEditSuccess,
            onEditError = onEditError
        )
    }
}

@Composable
private fun CategoryDetailsEditContent(
    modifier: Modifier = Modifier,
    category: Category,
    onDismiss: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditSuccess: () -> Unit = {},
    onEditError: () -> Unit = {},
    viewModel: CategoryDetailsViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    var categoryName by remember { mutableStateOf(category.title ?: "") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isEdited, uiState.errorMessage) {
        when {
            uiState.isEdited -> {
                onEditSuccess()
            }

            uiState.errorMessage != null -> onEditError()
        }

    }

    Column(
        modifier = modifier.padding(horizontal = 16.dp)
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
                    onClick = onDeleteClick,
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TudeeTextField(
            icon = R.drawable.add_category_icon,
            hint = R.string.category_name,
            value = categoryName,
            onValueChange = { categoryName = it },
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.category_image),
            style = TudeeTheme.typography.titleMedium,
            color = TudeeTheme.colors.title
        )

        Spacer(modifier = Modifier.height(12.dp))

        ImagePickerEditCategory(
            currentImageUri = category.iconRes
        ) { uri ->
            imageUri = uri
        }

        Spacer(modifier = Modifier.height(36.dp))

        val isSaveDisabled =
            ((imageUri == null && categoryName.isCategoryNotChanged(category.title))
                    || categoryName.isBlank())

        TudeePrimaryButton(
            onClick = {
                val savedImageUri = saveImageToInternalStorage(
                    context = context,
                    bitmap = galleryImageToBitmap(context, imageUri!!),
                    fileName = categoryName
                )
                viewModel.editCategory(
                    category = Category(
                        id = category.id,
                        title = categoryName,
                        iconRes = savedImageUri,
                        isDefault = category.isDefault,
                        taskCount = category.taskCount
                    )
                )
                onDismiss()
            },
            text = stringResource(R.string.save),
            modifier = Modifier.fillMaxWidth(),
            isDisabled = isSaveDisabled
        )


        Spacer(modifier = Modifier.height(12.dp))

        TudeeSecondaryButton(
            onClick = onDismiss,
            text = stringResource(R.string.cancel),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private fun String.isCategoryNotChanged(categoryTitle: String?): Boolean {
    return categoryTitle?.let { this == it } ?: this.isEmpty()
}

@Composable
private fun ImagePickerEditCategory(
    modifier: Modifier = Modifier, currentImageUri: String? = null, onImagePicked: (Uri?) -> Unit
) {
    var imageUri by remember { mutableStateOf(currentImageUri?.toUri()) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        onImagePicked(uri)
    }

    Box(modifier = modifier
        .size(112.dp)
        .drawBehind {
            drawRoundRect(
                color = RectBorderColor, style = Stroke(
                    width = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(48f, 24f), 0f),
                    cap = StrokeCap.Butt
                ), cornerRadius = CornerRadius(16.dp.toPx(), 16.dp.toPx())
            )
        }
        .clip(TudeeTheme.shapes.extraSmall), contentAlignment = Alignment.Center) {
        Image(
            painter = rememberAsyncImagePainter(imageUri),
            contentDescription = "Selected Image",
            modifier = Modifier
                .matchParentSize()
                .padding(4.dp)
                .clip(TudeeTheme.shapes.extraSmall),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(shape = TudeeTheme.shapes.extraSmall)
                .background(TudeeTheme.colors.surfaceHigh)
                .clickable {
                    imagePickerLauncher.launch("image/*")
                }, contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.pencil_edit_01),
                contentDescription = "Edit Image",
                tint = TudeeTheme.colors.secondary,
                modifier = Modifier.padding(6.dp)
            )
        }
    }
}

@ThemePreviews
@Composable
private fun CategoryDetailsEditScreenPreview() {
    TudeeTheme {
        CategoryDetailsEditBottomSheet(
            modifier = Modifier, category = Category(
            id = 1,
            title = "Work",
            iconRes = "",
            isDefault = true,
            taskCount = 0,
        ), onDismiss = {}, onDeleteClick = {}
        )
    }
}