package com.london.tudee.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.london.tudee.R
import com.london.tudee.presentation.components.DateBadge
import com.london.tudee.presentation.components.HomeTopBar
import com.london.tudee.presentation.components.StatusCard
import com.london.tudee.presentation.components.TaskItem
import com.london.tudee.presentation.components.TaskStatusSlider
import com.london.tudee.presentation.components.buttons.TudeeFloatingActionButton
import com.london.tudee.presentation.components.priority.Priority
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    Scaffold(
        floatingActionButton = { TudeeFloatingActionButton(
            painter = painterResource(R.drawable.note_add),
            contentDescription = "note icon",
            onClick = {  },
            isEnabled = true
        ) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top App Bar with Status Bar Padding
            Box(
                modifier = Modifier
                    .background(TudeeTheme.colors.primary)
                    .padding(horizontal = 16.dp)
                    .padding(WindowInsets.statusBars.asPaddingValues())
                    .height(72.dp)
                    .fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val isDark = remember { mutableStateOf(false) }
                    HomeTopBar(
                        isDarkMode = isDark.value,
                        onCheckedChange = { isDark.value = it },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(TudeeTheme.colors.surface)
                    .verticalScroll(rememberScrollState())
            ) {
                // 🟡 This Box wraps both the background & lifted content
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    // Background bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .background(TudeeTheme.colors.primary)
                            .align(Alignment.TopCenter)
                            .zIndex(0f)
                    )

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clip(shape = TudeeTheme.shapes.small)
                            .background(TudeeTheme.colors.surfaceHigh)
                            .zIndex(1f)
                            .align(Alignment.TopCenter)
                    ) {
                        DateBadge(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .align(Alignment.CenterHorizontally),
                            dateText = "today, 22 Jun 2025"
                        )

                        TaskStatusSlider(
                            title = "Stay working!",
                            subtitle = "You've completed 3 out of 10 tasks, keep going",
                            note = null,
                            emoji = R.drawable.okay_status,
                            tudeePicture = R.drawable.tudee_warning,
                            modifier = Modifier.padding(horizontal = 6.dp)
                        )

                        Text(
                            text = "Overview",
                            style = TudeeTheme.typography.titleLarge,
                            color = TudeeTheme.colors.title,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )

                        Spacer(Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(bottom = 12.dp)
                                .fillMaxWidth()
                        ) {
                            StatusCard(
                                backgroundColor = TudeeTheme.colors.greenAccent,
                                statusIcon = R.drawable.file_verified,
                                tasksNumber = 2,
                                taskStatusName = R.string.Done,
                                modifier = Modifier.weight(1f)
                            )

                            StatusCard(
                                backgroundColor = TudeeTheme.colors.yellowAccent,
                                statusIcon = R.drawable.file_pin,
                                tasksNumber = 16,
                                taskStatusName = R.string.In_Progress,
                                modifier = Modifier.weight(1f)
                            )

                            StatusCard(
                                backgroundColor = TudeeTheme.colors.purpleAccent,
                                statusIcon = R.drawable.file_unknown,
                                tasksNumber = 1,
                                taskStatusName = R.string.To_Do,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "In progress",
                        style = TudeeTheme.typography.titleLarge,
                        color = TudeeTheme.colors.title
                    )

                    Box(
                        Modifier
                            .background(
                                color = TudeeTheme.colors.surfaceHigh,
                                shape = TudeeTheme.shapes.circle,
                            )
                            .padding(vertical = 6.dp, horizontal = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = "12",
                                style = TudeeTheme.typography.labelSmall,
                                color = TudeeTheme.colors.body
                            )

                            Icon(
                                painter = painterResource(R.drawable.left_arrow_icon),
                                contentDescription = null,
                                tint = TudeeTheme.colors.body
                            )
                        }
                    }
                }

                LazyHorizontalGrid(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    rows = GridCells.Fixed(2),
                    modifier = Modifier.height(230.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(20) {
                        TaskItem(
                            modifier = Modifier,
                            priority = Priority.HIGH,
                            iconResId = R.drawable.ic_education,
                            title = "Organize Study Desk",
                            description = "Review cell structure and functions tomorrow...",
                            date = null,
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "To-Do",
                        style = TudeeTheme.typography.titleLarge,
                        color = TudeeTheme.colors.title
                    )

                    Box(
                        Modifier
                            .background(
                                color = TudeeTheme.colors.surfaceHigh,
                                shape = TudeeTheme.shapes.circle,
                            )
                            .padding(vertical = 6.dp, horizontal = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = "9",
                                style = TudeeTheme.typography.labelSmall,
                                color = TudeeTheme.colors.body
                            )

                            Icon(
                                painter = painterResource(R.drawable.left_arrow_icon),
                                contentDescription = null,
                                tint = TudeeTheme.colors.body
                            )
                        }
                    }
                }

                LazyHorizontalGrid(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    rows = GridCells.Fixed(2),
                    modifier = Modifier.height(230.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(20) {
                        TaskItem(
                            modifier = Modifier,
                            priority = Priority.HIGH,
                            iconResId = R.drawable.ic_education,
                            title = "Organize Study Desk",
                            description = "Review cell structure and functions tomorrow...",
                            date = null
                        )
                    }
                }
            }
        }
    }
}

@ThemePreviews
@Composable
fun PreviewHomeScreen() {
    TudeeTheme {
        HomeScreen()
    }
}