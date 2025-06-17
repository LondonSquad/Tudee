package com.london.tudee.presentation.screens.onboarding

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.components.buttons.TudeeFloatingActionButton
import com.london.tudee.presentation.design_system.text_style.TudeeTextStyle
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun OnboardingScreen(
    @StringRes title: Int,
    @StringRes body: Int,
    image: Int,
    modifier: Modifier = Modifier,
    onClickForward: () -> Unit = {},
) {
    TudeeTheme {
            Column(modifier.fillMaxSize()) {
                Column(Modifier.fillMaxSize(),
                   verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        painter = painterResource(id = image),
                        contentDescription = null
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 32.dp)
                            .fillMaxHeight(0.4f)
                            .background(
                                color = TudeeTheme.colors.onPrimaryCard,
                                shape = RoundedCornerShape(32.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = TudeeTheme.colors.onPrimaryStroke,
                                shape = RoundedCornerShape(32.dp)
                            ),
                    ) {

                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .padding(top = 24.dp , start = 16.dp )
                        ) {
                            Text(
                                text = stringResource(title),
                                style = TudeeTextStyle.titleMedium,
                                color = TudeeTheme.colors.title,
                                modifier = Modifier.align(Alignment.TopCenter)
                            )
                            Text(
                                stringResource(body),
                                style = TudeeTextStyle.bodyMedium,
                                color = TudeeTheme.colors.body,
                                modifier = Modifier.align(Alignment.BottomEnd),
                                textAlign = TextAlign.Center,

                            )
                        }
                    }
                    TudeeFloatingActionButton(
                        modifier = Modifier.offset(y = -30.dp),
                        painter = painterResource(R.drawable.arrow_right_double),
                        contentDescription = "Add Note",
                        isEnabled = true,
                        onClick = onClickForward
                    )
                }
        }
    }
}

@ThemePreviews
@Composable
fun PreviewOnboardingScreen() {
    TudeeTheme {
        OnboardingScreen(
            title = R.string.on_bording_title_1,
            body = R.string.on_bording_body_1,
            image = R.drawable.on_boarding1
        )
    }
}
