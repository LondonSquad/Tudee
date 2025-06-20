@file:JvmName("TaskDetailsScreenKt")

package com.london.tudee.presentation.screens.task.view_tasks

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.london.tudee.R
import com.london.tudee.presentation.components.TopAppBar
import com.london.tudee.presentation.components.date.TudeeDatePicker
import com.london.tudee.presentation.components.tabs.TabItem
import com.london.tudee.presentation.components.tabs.TudeeTabLayoutWithPager
import com.london.tudee.presentation.components.task.SwipeToDeleteTask
import com.london.tudee.presentation.components.task.TaskItem
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import org.koin.androidx.compose.koinViewModel
import com.london.tudee.presentation.navigation.TaskTabStatus
import com.london.tudee.presentation.screens.categories.getSampleCategories
import com.london.tudee.presentation.screens.tasks.EmptyTasksScreen
import kotlin.math.log

@Composable
fun EditTaskDetails(
    viewModel: EditTaskViewModel = koinViewModel(),
    navController: NavHostController,
    onEditCategory: () -> Unit,
    categortyId: Int
) {

    val uiState by viewModel.uiState.collectAsState()
    when {
        uiState.isLoading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        uiState.errMessage != null -> ErrorScreen(modifier = Modifier.fillMaxSize())
        else -> EditTaskDetailsContent(
            state = uiState,
            navController = navController,
            onEditCategory = onEditCategory,
            viewModel = viewModel,
            categortyId = categortyId
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier) {
        Text(
            text = "Loading...",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(modifier) {
        Text(
            text = "There was an unexpected error",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EditTaskDetailsContent(
    state: EditTaskDetailsState,
    navController: NavHostController,
    onEditCategory: () -> Unit,
    viewModel: EditTaskViewModel,
    categortyId: Int
) {
    //  val tasksList = getSampleCategories(TudeeTheme.colors)
//    val category = viewModel.uiState.value.allTasks.find { it.id == categortyId }
//        ?: viewModel.uiState.value.allTasks.get(0)

    Column(
        modifier = Modifier
            .background(TudeeTheme.colors.surface)
    ) {
        TopAppBar(
            title = R.string.app_name, //category.title,
            onBackClick = { navController.popBackStack() },
            onClickAction = onEditCategory,
            modifier = Modifier,
            navigationIcon = {
                IconButton(
                    onClick = it,
                    modifier = Modifier
                        .then(
                            if (LocalLayoutDirection.current == LayoutDirection.Rtl)
                                Modifier.rotate(180f)
                            else Modifier
                        )
                        .border(
                            1.dp,
                            TudeeTheme.colors.stroke,
                            TudeeTheme.shapes.circle
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.back_arrow),
                        contentDescription = stringResource(R.string.back_arrow),
                        tint = TudeeTheme.colors.body
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = it,
                    modifier = Modifier
                        .then(
                            if (LocalLayoutDirection.current == LayoutDirection.Rtl)
                                Modifier.rotate(180f)
                            else Modifier
                        )
                        .border(
                            1.dp,
                            TudeeTheme.colors.stroke,
                            TudeeTheme.shapes.circle
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.edit_icon),
                        contentDescription = "Edit Icon",
                        tint = TudeeTheme.colors.body
                    )
                }
            }
        )
    }
//        )
//        val pagerState = rememberPagerState(
//            initialPage = 0,
//            initialPageOffsetFraction = 0f,
//            pageCount = { 3 }
//        )


//            var showDatePicker by remember { mutableStateOf(false) }
//            var selectedDate by remember { mutableStateOf<Long?>(null) }
    TudeeTabLayoutWithPager(
        tabs = listOf(
            TabItem(
                text = R.string.In_Progress,
                number = viewModel.uiState.value.inProgressTasks.size
            ),
            TabItem(text = R.string.To_Do, number = viewModel.uiState.value.toDoTasks.size),
            TabItem(text = R.string.Done, number = viewModel.uiState.value.doneTasks.size),

            )
    ) { page ->
        val tasks = when (page) {
            0 -> viewModel.uiState.value.inProgressTasks
            1 -> viewModel.uiState.value.toDoTasks
            2 -> viewModel.uiState.value.doneTasks
            else -> emptyList()
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(TudeeTheme.colors.surface)
        ) {
            if (tasks.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 121.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    EmptyTasksScreen()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(tasks.size) { index ->
                        SwipeToDeleteTask(
                            modifier = Modifier,
                            task = tasks[index],
                            onDeleteClick = {}
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }


    }
//
//        TudeeTabLayoutWithPager(
//            tabs = listOf(
//                TabItem(
//                    text = R.string.In_Progress,
//                    number =10, //category.tasks.count { it.status == Status.IN_PROGRESS }),
//                TabItem(
//                    text = R.string.Done,
//                    number = 9,//category.tasks.count { it.status == Status.DONE }),
//                TabItem(
//                    text = R.string.To_Do,
//                    number = 2,//category.tasks.count { it.status == Status.TO_DO }),
//            ),
//        ) { page ->
////            val filteredTasks = when (page) {
////                0 -> category.tasks.filter { it.status == Status.IN_PROGRESS }
////                1 -> category.tasks.filter { it.status == Status.DONE }
////                2 -> category.tasks.filter { it.status == Status.TO_DO }
////                else -> emptyList()
////            }
//       //     Log.d("ddd", filteredTasks.toString())
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(TudeeTheme.colors.surface)
//            ) {
//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(horizontal = 16.dp, vertical = 8.dp)
//                ) {
//                    items(filteredTasks.size) { index ->
//                        TaskItem(
//                            modifier = Modifier,
//                            isSelected = true,
//                            task = category.tasks[index],
//                            onTaskClicked = {}
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                    }
//                }
//            }
//        }
    // حراااااااممم
//        TudeeTabLayout(
//            tabs = listOf(
//                TabItem(text = R.string.In_Progress, number = 14),
//                TabItem(text = R.string.Done, number = 10),
//                TabItem(text = R.string.To_Do, number = 2),
//            ),
//            selectedIndex = 0,
//            onTabSelected = {},
//            modifier = Modifier.fillMaxWidth()
//        )
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(1.dp)
//                .background(TudeeTheme.colors.stroke)
//        )
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(TudeeTheme.colors.surface)
//        ) {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(horizontal = 16.dp, vertical = 8.dp)
//            ) {
//                items(category.tasks.size) { index ->
//                    TaskItem(
//                        modifier = Modifier,
//                        isSelected = true,
//                        task = category.tasks[index],
//                        onTaskClicked = {}
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                }
//            }
//        }
}


@ThemePreviews
@Composable
private fun TudeeTaskPreview() {
    TudeeTheme {
//        EditTaskDetails(
//            navController = rememberNavController(),
//          //  categoryId = 0,
//            onEditCategory = {})
//    }
    }
}