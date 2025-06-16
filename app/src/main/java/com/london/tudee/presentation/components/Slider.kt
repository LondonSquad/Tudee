package com.london.tudee.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.london.tudee.R
import com.london.tudee.presentation.design_system.text_style.nunitoFont
import com.london.tudee.presentation.design_system.theme.TudeeTheme


enum class TaskEmoji(val emoji: Int) {
    POOR(R.drawable.poor_emoji),
    BAD(R.drawable.bad_emoji),
    OKAY(R.drawable.okay_status),
    GOOD(R.drawable.good_emoji)
}

@Composable
fun TaskStatusSlider(
    modifier: Modifier = Modifier,
    title: String = "Nothing on your list…",
    subtitle: String = "You just scrolling, not working.",
    note: String = "Tudee is watching. back to work!!!",
    @DrawableRes emoji: Int = R.drawable.poor_emoji,
    @DrawableRes tudeePicture: Int = R.drawable.poor,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(92.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = modifier
                    .padding(vertical = 15.dp)
            ) {
                Row {

                    Text(
                        text = title,
                        fontFamily = nunitoFont,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0XDE1F1F1F),
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Image(
                        modifier = Modifier
                            .size(20.dp),
                        painter = painterResource(id = emoji),
                        contentDescription = null,
                    )
                }

                Text(
                    text = subtitle,
                    fontFamily = nunitoFont,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0XDE1F1F1F),
                    lineHeight = 17.sp
                )
                Text(
                    text = note,
                    fontFamily = nunitoFont,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0XDE1F1F1F),
                    lineHeight = 17.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
//
            Box(
                modifier = Modifier
                    .width(76.dp)
                    .fillMaxHeight(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Box(
                    modifier = Modifier
                        .size(65.dp)
                        .background(
                            color = TudeeTheme.colors.primary.copy(alpha = .2f),
                            shape = TudeeTheme.shapes.circle
                        )
                )

                Box(
                    modifier = Modifier
                        .size(74.dp)
                        .padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = tudeePicture),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun TaskStatusSliderPreview() {
    Column(
        modifier = Modifier
            .padding(16.dp),
//            .offset(y = 56.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TaskStatusSlider()
    }
}

//@Preview(showBackground = true)
//@Composable
//fun TaskStatusSliderArabicPreview() {
//    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
//        Column(
//            modifier = Modifier.padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            TaskStatusSlider()
//        }
//    }
//}
