package com.london.tudee.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.utils.converterStringToBitmap

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    iconRes: String,
    title: String,
    taskCount: Int = 0,
    categoryId: Int = 0,
    inCategorySection: Boolean = false,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val bitmap = remember(iconRes) {
        converterStringToBitmap(iconRes)
    }
    val image = rememberAsyncImagePainter(bitmap)

    Column(
        modifier = modifier
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(78.dp)
                .background(
                    color = TudeeTheme.colors.surfaceHigh,
                    shape = TudeeTheme.shapes.circle
                )
                .padding(if (categoryId <= 17) 23.dp else 0.dp)
        ) {
            Image(
                painter = image,
                contentScale = if (categoryId >= 17) ContentScale.FillBounds else ContentScale.Fit,
                contentDescription = "Editable categories",
                modifier = if (categoryId >= 17)
                    Modifier
                        .fillMaxSize()
                        .clip(TudeeTheme.shapes.circle)
                else Modifier.size(32.dp),
            )

            if (isSelected) {
                Image(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = "Selected",
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = 15.dp, y = (-25).dp)
                        .background(
                            color = TudeeTheme.colors.greenAccent,
                            shape = TudeeTheme.shapes.circle
                        )
                        .padding(2.dp)
                )
            }
            if(inCategorySection){Box(
                modifier = Modifier
                    .width(36.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 20.dp, y = (-20).dp)
                    .background(
                        color = TudeeTheme.colors.surfaceLow,
                        shape = TudeeTheme.shapes.circle
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = taskCount.toString(),
                    style = TudeeTheme.typography.labelSmall,
                    color = TudeeTheme.colors.hint
                )
            }}
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            style = TudeeTheme.typography.labelSmall,
            color = TudeeTheme.colors.body,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}