package com.london.tudee.presentation.screens.categories.crud

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.london.tudee.R
import com.london.tudee.domain.entities.Category
import com.london.tudee.presentation.components.TudeeTextField
import com.london.tudee.presentation.components.bottom_sheet.TudeeBottomSheetScreen
import com.london.tudee.presentation.components.buttons.TudeePrimaryButton
import com.london.tudee.presentation.components.buttons.TudeeSecondaryButton
import com.london.tudee.presentation.design_system.color.RectBorderColor
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import org.koin.androidx.compose.koinViewModel
import java.io.ByteArrayOutputStream

// Helper function to convert URI to Base64
private fun uriToBase64(context: Context, uri: Uri): String? {
    return try {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        Base64.encodeToString(byteArray, Base64.DEFAULT)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

// Helper function to convert Base64 string to Bitmap
private fun base64ToBitmap(base64String: String): Bitmap? {
    return try {
        if (base64String.isBlank()) return null
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        android.graphics.BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Composable
fun EditCategoryScreen(
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
            CategoryEditContent(
                modifier = modifier,
                category = category,
                onDismiss = onDismiss,
            )
        }
    )
}

@Composable
private fun CategoryEditContent(
    modifier: Modifier = Modifier,
    category: Category,
    onDismiss: () -> Unit,
    viewModel: EditCategoryScreenViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    var categoryName by remember { mutableStateOf(category.title) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

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
                    onClick = {
                        // nav to delete category screen
                    },
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

        TudeePrimaryButton(
            onClick = {
                val base64Image = imageUri?.let { uriToBase64(context, it) } ?: category.iconRes
                viewModel.editCategory(
                    category = Category(
                        id = category.id,
                        title = categoryName,
                        //arName = categoryName,
                        iconRes = base64Image,
                        isDefault = category.isDefault,
                        //  tint = category.tint,
                        taskCount = category.taskCount
                    )
                )
                onDismiss()
            },
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
private fun ImagePickerEditCategory(
    modifier: Modifier = Modifier,
    currentImageUri: String? = null,
    onImagePicked: (Uri?) -> Unit
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
            // Display new selected image or existing Base64 image
            when {
                imageUri != null -> {
                    // Show newly selected image
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .matchParentSize()
                            .clip(TudeeTheme.shapes.extraSmall),
                        contentScale = ContentScale.Crop
                    )
                }

                !currentImageUri.isNullOrBlank() -> {
                    // Show existing Base64 image
                    val bitmap = base64ToBitmap(currentImageUri)
                    if (bitmap != null) {
                        Image(
                            painter = rememberAsyncImagePainter(bitmap),
                            contentDescription = "Current Image",
                            modifier = Modifier
                                .matchParentSize()
                                .clip(TudeeTheme.shapes.extraSmall),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        // Show edit button if image exists, otherwise show upload area
        val hasImage = imageUri != null || !currentImageUri.isNullOrBlank()
        if (hasImage) {
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
                    contentDescription = "Edit Image",
                    tint = TudeeTheme.colors.secondary,
                    modifier = Modifier.padding(6.dp)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        imagePickerLauncher.launch("image/*")
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add_image),
                    contentDescription = "Add Image",
                    tint = TudeeTheme.colors.hint,
                )
                Text(
                    text = stringResource(R.string.upload),
                    style = TudeeTheme.typography.labelMedium,
                    color = TudeeTheme.colors.hint,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }

}

@ThemePreviews
@Composable
private fun EditCategoryScreenPreview() {
    TudeeTheme {
        EditCategoryScreen(
            modifier = Modifier,
            category = Category(
                id = 1,
                title = "Work",
                //  arName = "العمل",
                iconRes = "",
                isDefault = true,
                taskCount = 0,
                //   tint = TudeeTheme.colors.primary.value
            ),
            onDismiss = {}
        )
    }
}