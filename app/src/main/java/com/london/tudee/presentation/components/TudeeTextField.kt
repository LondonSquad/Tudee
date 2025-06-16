package com.london.tudee.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun TudeeTextField(
    modifier: Modifier = Modifier,
    icon: Int? = null,
    @StringRes hint: Int,
    multiLined: Boolean = false,
) {
    var text by rememberSaveable { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val cornerRadius = TudeeTheme.shapes.small
    val outlineColor = TudeeTheme.colors.stroke
    val focusedBorderColor = TudeeTheme.colors.primary
    val borderColor = if (isFocused) focusedBorderColor else outlineColor

    val iconColor = if (text.isNotEmpty()) TudeeTheme.colors.body else TudeeTheme.colors.hint

    Box(
        modifier = modifier
            .background(TudeeTheme.colors.surface)
            .fillMaxWidth()
            .height(if (multiLined) 168.dp else 56.dp)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = cornerRadius,
            )
            .clip(cornerRadius)
            .padding(top = if (multiLined) 12.dp else 0.dp)
            .clickable {
                focusRequester.requestFocus()
            },
        contentAlignment = if (multiLined) Alignment.TopCenter else Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = "icon",
                    tint = iconColor,
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .size(24.dp)
                )

                VerticalDivider(
                    modifier = Modifier
                        .width(1.dp)
                        .height(30.dp)
                        .background(outlineColor)
                )
            }

            Spacer(modifier = modifier.width(12.dp))

            BasicTextField(
                value = text,
                onValueChange = { text = it },
                singleLine = !multiLined,
                interactionSource = interactionSource,
                decorationBox = { innerTextField ->
                    if (text.isEmpty()) {
                        Text(
                            text = stringResource(hint),
                            color = TudeeTheme.colors.hint,
                            style = TudeeTheme.typography.labelMedium
                        )
                    }
                    innerTextField()
                },
                textStyle = TudeeTheme.typography.bodyMedium
                    .copy(color = TudeeTheme.colors.body),
                maxLines = if (multiLined) Int.MAX_VALUE else 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .padding(end = 12.dp),
            )
        }
    }
}

@ThemePreviews
@Composable
fun TudeeTextFieldPreview() {
    TudeeTheme {
        TudeeTextField(
            multiLined = false,
            icon = R.drawable.add_date_icon,
            hint = R.string.task_title
        )
    }
}