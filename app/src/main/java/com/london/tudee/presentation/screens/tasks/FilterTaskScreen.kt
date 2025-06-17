package com.london.tudee.presentation.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.components.DateItem
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun DateSection(month: String = "Jun,", year: String = "2025", dates: MutableList<DateItemClass>) {
    DateSelector(month, year)
    Spacer(modifier = Modifier.padding(top = 8.dp))
    DayOfWeekSelector(dates)
}

@Composable
fun DateSelector(dayOfMonth: String, dayOfWeek: String) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .border(
                    width = 1.dp, color = TudeeTheme.colors.stroke, shape = TudeeTheme.shapes.circle
                ), contentAlignment = Alignment.Center
        ) {
            Icon(painterResource(R.drawable.left_arrow), contentDescription = null)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                dayOfMonth,
                style = TudeeTheme.typography.labelMedium,
                color = TudeeTheme.colors.body
            )
            Text(
                "2025", style = TudeeTheme.typography.labelMedium, color = TudeeTheme.colors.body
            )
            Icon(
                painter = painterResource(R.drawable.down_arrow), contentDescription = null
            )
        }

        Box(
            modifier = Modifier
                .size(32.dp)
                .border(
                    width = 1.dp, color = TudeeTheme.colors.stroke, shape = TudeeTheme.shapes.circle
                ), contentAlignment = Alignment.Center
        ) {
            Icon(painterResource(R.drawable.right_arrow), contentDescription = null)
        }
    }
}

@Composable
fun DayOfWeekSelector(
    dates: MutableList<DateItemClass>
) {
    val datesTest = remember { dates }
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(start = 16.dp),
        userScrollEnabled = true

    ) {
        items(dates.size) { index ->
            DateItem(
                dayOfMonth = datesTest[index].dayOfMonth,
                dayOfWeek = datesTest[index].dayOfWeek,
                isSelected = datesTest[index].isSelected,
            ) {

                datesTest.forEachIndexed { i, item ->
                    datesTest[i] = item.copy(isSelected = false)
                }
                datesTest[index] = datesTest[index].copy(isSelected = true)
            }
        }
    }
}

@ThemePreviews
@Composable
fun PreviewFilterTaskScreen() {
    TudeeTheme {
    }
}
