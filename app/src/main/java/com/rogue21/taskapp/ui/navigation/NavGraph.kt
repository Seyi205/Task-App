package com.rogue21.taskapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rogue21.taskapp.ui.screens.addedit.AddEditTaskScreen
import com.rogue21.taskapp.ui.screens.settings.SettingsScreen
import com.rogue21.taskapp.ui.screens.tasklist.TaskListScreen
import com.rogue21.taskapp.ui.screens.tasklist.TaskListViewModel

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
//    repository: TaskRepository
){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = TaskListRoute,
        modifier = modifier
    ) {
        composable<TaskListRoute> {
            val viewModel: TaskListViewModel = hiltViewModel()

            TaskListScreen(
                onNavigateToAddTask = {
                    navController.navigate(AddEditTaskRoute())
                },
                onNavigateToSettings = {
                    navController.navigate(SettingsRoute)
                },
                onNavigateToEditTask = { taskId ->
                    navController.navigate(AddEditTaskRoute(taskId))
                },
                viewModel = viewModel
            )
        }

        composable<AddEditTaskRoute> { backStackEntry ->

            val route = backStackEntry.toRoute<AddEditTaskRoute>()

            AddEditTaskScreen(
                taskId = route.taskId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<SettingsRoute> {
            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}