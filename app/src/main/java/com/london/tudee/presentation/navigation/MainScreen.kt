package com.london.tudee.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.london.tudee.R
import com.london.tudee.presentation.components.bottom_navigation_bar.TudeeBottomNavigationBar
import com.london.tudee.presentation.screens.categories.CategoriesScreen
import com.london.tudee.presentation.screens.categories.crud.EditCategoryScreen
import com.london.tudee.presentation.screens.home.HomeScreen
import com.london.tudee.presentation.screens.task.add_edit_task_bottom_sheet.AddOrEditTaskBottomSheet
import com.london.tudee.presentation.screens.task.confirm_delete_task.ConfirmDeleteTaskScreen
import com.london.tudee.presentation.screens.task.task_details.TaskDetailsScreen
import com.london.tudee.presentation.screens.task.view_tasks.EditTaskDetails
import com.london.tudee.presentation.screens.tasks.TasksScreen

@Composable
fun MainScreen() {
    val childNavController = rememberNavController()
    var activeSheet by remember { mutableStateOf<ActiveBottomSheet>(ActiveBottomSheet.None) }
    var categoryID: Int? = 0
    Scaffold(
        bottomBar = {
            if (activeSheet == ActiveBottomSheet.None) {
                TudeeBottomNavigationBar(navController = childNavController)
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.padding(padding)) {
                NavHost(
                    navController = childNavController,
                    startDestination = Screen.Home.route,
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen(
                            onFabClicked = {
                                activeSheet = ActiveBottomSheet.AddTask
                            },
                            onTaskClicked = { task ->
                                activeSheet = ActiveBottomSheet.TaskDetails(task)
                            },
                            onArrowClicked = { tab ->
                                childNavController.navigate(Screen.TaskTab.createRoute(tab))
                            }
                        )
                    }

                    composable(Screen.Tasks.route) {
                        TasksScreen(
//                            onDeleteClick = {
//                                activeSheet = ActiveBottomSheet.DeleteTask
//                            }
                        )
                    }


                    composable(Screen.TaskTab.route) { backStackEntry ->
                        val tabString = backStackEntry.arguments?.getString("tab") ?: "todo"
                        val tab = TaskTabStatus.fromValue(tabString)
                        TasksScreen(
//                            defaultTab = tab,
//                            onDeleteClick = {
//                                activeSheet = ActiveBottomSheet.DeleteTask
//                            }
                        )
                    }


                    composable(Screen.Categories.route) {
                        CategoriesScreen(
                            onCategoryClick = { categoryId ->
                                childNavController.navigate(
                                    Screen.CategoryDetails.createRoute(
                                        categoryId
                                    )
                                )
                            },
                            onAddCategoryClick = {
                                activeSheet = ActiveBottomSheet.AddCategory
                            }
                        )
                    }


                    composable(Screen.CategoryDetails.route) { backStackEntry ->
                        val categoryId =
                            backStackEntry.arguments?.getString("categoryId")?.toIntOrNull()
                        // Category
                        EditTaskDetails(
                            navController = childNavController,
                            onEditCategory = {
                                activeSheet = ActiveBottomSheet.EditCategory
                            },
                            categortyId = categoryID ?: 0
                        )

                    }
                }
            }
            when (val sheet = activeSheet) {
                is ActiveBottomSheet.TaskDetails -> {
                 //   var taskStatus by remember { mutableStateOf(sheet.task.status) }
                    TaskDetailsScreen(
                        taskId = sheet.task.id,
                        onEditClick = { activeSheet = ActiveBottomSheet.EditTask(sheet.task) },
                        onDismiss = { activeSheet = ActiveBottomSheet.None },
                        showBottomSheet = true // ????,
                    )

                }

                is ActiveBottomSheet.EditTask -> {
                    var titleText by remember { mutableStateOf("") }
                    var descriptionText by remember { mutableStateOf("") }
//                    AddOrEditTaskBottomSheet(
//                        title = R.string.edit_task,
//                        buttonText = R.string.save,
//                     //   onDismiss = { activeSheet = ActiveBottomSheet.None },
//
//                    ) {}
                    AddOrEditTaskBottomSheet(
                        title = R.string.edit_task,
                        buttonText = R.string.save,
                        modifier = Modifier,
                        screenContent = {},
                    )
                }

                is ActiveBottomSheet.AddTask -> {
                    var titleText by remember { mutableStateOf("") }
                    var descriptionText by remember { mutableStateOf("") }
//                    AddOrEditTaskBottomSheet(
//                        title = R.string.add_new_task,
//                        buttonText = R.string.add,
//                        onDismiss = { activeSheet = ActiveBottomSheet.None },
//                        //  onSaved = {  }
//                    ) {}
                    AddOrEditTaskBottomSheet(
                        title =R.string.add_new_task,
                        buttonText = R.string.add,
                        modifier = Modifier,
                        screenContent = {},
                    )
                }


                ActiveBottomSheet.AddCategory -> {
//                    AddCategoryBottomSheet(
//                        onDismiss = { activeSheet = ActiveBottomSheet.None }
//                    )
                }

                is ActiveBottomSheet.EditCategory -> {
//                    EditCategoryBottomSheet(
//                        categoryId = sheet.categoryId,
//                        onDismiss = { activeSheet = ActiveBottomSheet.None }
//                    )
                }

                is ActiveBottomSheet.DeleteTask -> {
                    ConfirmDeleteTaskScreen(
                        onTaskDeleted = {
                          //  activeSheet = ActiveBottomSheet.None
                        },
                    )
                }
                ActiveBottomSheet.None -> Unit
            }
        }
    }
}


//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun MainScreen() {
//    val childNavController = rememberNavController()
//    var activeSheet by remember { mutableStateOf<ActiveBottomSheet>(ActiveBottomSheet.None) }
//    var categoryID: Int? = 0
//    Scaffold(
//        bottomBar = {
//            if (activeSheet == ActiveBottomSheet.None) {
//                TudeeBottomNavigationBar(navController = childNavController)
//            }
//        }
//    ) { padding ->
//        Box(modifier = Modifier.fillMaxSize()) {
//            Box(modifier = Modifier.padding(padding)) {
//                NavHost(
//                    navController = childNavController,
//                    startDestination = Screen.Home.route,
//                ) {
//                    composable(Screen.Home.route) {
//                        HomeScreen(
//                            onFabClicked = {
//                                activeSheet = ActiveBottomSheet.AddTask
//                            },
//                            onTaskClicked = { task ->
//                                activeSheet = ActiveBottomSheet.TaskDetails(task)
//                            },
//                            onArrowClicked = { tab ->
//                                childNavController.navigate(Screen.TaskTab.createRoute(tab))
//                            }
//                        )
//                    }
//
//                    composable(Screen.Tasks.route) {
//                        TasksScreen(
////                            onDeleteClick = {
////                                activeSheet = ActiveBottomSheet.DeleteTask
////                            }
//                        )
//                    }
//
//
//                    composable(Screen.TaskTab.route) { backStackEntry ->
//                        val tabString = backStackEntry.arguments?.getString("tab") ?: "todo"
//                        val tab = TaskTabStatus.fromValue(tabString)
//                        TasksScreen(
////                            defaultTab = tab,
////                            onDeleteClick = {
////                                activeSheet = ActiveBottomSheet.DeleteTask
////                            }
//                        )
//                    }
//
//
//                    composable(Screen.Categories.route) {
//                        CategoriesScreen(
//                            onCategoryClick = { categoryId ->
//                                childNavController.navigate(
//                                    Screen.CategoryDetails.createRoute(
//                                        categoryId
//                                    )
//                                )
//                            },
//                            onAddCategoryClick = {
//                                activeSheet = ActiveBottomSheet.AddCategory
//                            }
//                        )
//                    }
//
//                    composable(Screen.CategoryDetails.route) { backStackEntry ->
//                        val categoryId =
//                            backStackEntry.arguments?.getString("categoryId")?.toIntOrNull()
//                        // Category
//                        EditTaskDetails(
//                            navController = childNavController,
//                            categoryId = categoryId ?: 0,
//                            onEditClicked = {
//                                activeSheet = ActiveBottomSheet.EditCategory
//                            },
//                        )
//
//                    }
//                }
//            }
//            when (val sheet = activeSheet) {
//                is ActiveBottomSheet.TaskDetails -> {
//                    //   var taskStatus by remember { mutableStateOf(sheet.task.status) }
//
//                    TaskDetailsScreen(
//                        taskId = sheet.task.id,
//                        onEditClick = { activeSheet = ActiveBottomSheet.EditTask(sheet.task) },
//                        onDismiss = { activeSheet = ActiveBottomSheet.None },
//                        showBottomSheet = true // ????,
//                    )
////
//////
////                    TaskDetailsScreen(
//////                        taskName = sheet.task.title,
//////                        taskDescription = sheet.task.description,
//////                        taskPriority = sheet.task.priority,
//////                        taskStatus = taskStatus,
//////                        icon = sheet.task.iconResId,
////                        taskId = sheet.task.id,
////                        onDismiss = { activeSheet = ActiveBottomSheet.None },
////                        onEditClick = {
////                            activeSheet = ActiveBottomSheet.EditTask(sheet.task)
////                        },
////                        onMoveClick = {
//////                            taskStatus = when (taskStatus) {
//////                                Status.TO_DO -> Status.IN_PROGRESS
//////                                Status.IN_PROGRESS -> Status.DONE
//////                                Status.DONE -> Status.DONE
//////                                else -> {}
//////                            }
////                        }
////                    )
//                }
//
//                is ActiveBottomSheet.EditTask -> {
//                    var titleText by remember { mutableStateOf("") }
//                    var descriptionText by remember { mutableStateOf("") }
//                    AddOrEditTaskBottomSheet(
//                        title = R.string.edit_task,
//                        buttonText = R.string.save,
//                        onDismiss = { activeSheet = ActiveBottomSheet.None },
//                        titleValue = titleText,
//                        descriptionValue = descriptionText,
//                        onTitleValueChange = { titleText = it },
//                        onDescriptionValueChange = { descriptionText = it }
//                        //  onSaved = {  }
//                    ) {}
//                }
//
//                is ActiveBottomSheet.AddTask -> {
//                    var titleText by remember { mutableStateOf("") }
//                    var descriptionText by remember { mutableStateOf("") }
//                    AddOrEditTaskBottomSheet(
//                        title = R.string.add_new_task,
//                        buttonText = R.string.add,
//                        onDismiss = { activeSheet = ActiveBottomSheet.None },
//                        titleValue = titleText,
//                        descriptionValue = descriptionText,
//                        onTitleValueChange = { titleText = it },
//                        onDescriptionValueChange = { descriptionText = it }
//                        //  onSaved = {  }
//                    ) {}
//                }
//
//
//                ActiveBottomSheet.AddCategory -> {
////                    AddCategoryBottomSheet(
////                        onDismiss = { activeSheet = ActiveBottomSheet.None }
////                    )
//                }
//
//                is ActiveBottomSheet.EditCategory -> {
//                    EditCategoryScreen(
////                        categoryId = categoryID ?: 0,
////                        onSaveClick = {
////
////                        },
////                        onDeleteClick = {
////
////                        },
//                        category = ,
//                        onDismiss = { activeSheet = ActiveBottomSheet.None }
//                    )
//                }
//
//                is ActiveBottomSheet.DeleteTask -> {
//                    ConfirmDeleteTaskScreen(
//                        onTaskDeleted = {
//
//                        }
//                    )
//                }
//
//                ActiveBottomSheet.None -> Unit
//            }
//        }
//    }
//}
