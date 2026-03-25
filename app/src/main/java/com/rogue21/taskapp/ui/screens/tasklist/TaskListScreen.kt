package com.rogue21.taskapp.ui.screens.tasklist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rogue21.taskapp.model.Task
import com.rogue21.taskapp.ui.components.SectionHeader
import com.rogue21.taskapp.ui.components.TaskItem
import com.rogue21.taskapp.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    onNavigateToAddTask: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToEditTask: (Int) -> Unit,
    viewModel: TaskListViewModel
) {
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tasks") },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddTask) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task"
                )
            }
        }
    ) { padding ->

        if (tasks.isEmpty()) {
            EmptyState(
                modifier = Modifier.padding(padding)
            )
        } else {
            TaskListContent(
                tasks = tasks,
                onDelete = { viewModel.deleteTask(it) },
                onToggle = { task, isChecked ->
                    viewModel.setTaskCompleted(task, isChecked)
                },
                onTaskClick = onNavigateToEditTask,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TaskListContent(
    tasks: List<Task>,
    onDelete: (Task) -> Unit,
    onToggle: (Task, Boolean) -> Unit,
    onTaskClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val activeTasks = tasks.filter { !it.isCompleted }
    val completedTasks = tasks.filter { it.isCompleted }
    var isCompletedExpanded by rememberSaveable { mutableStateOf(true) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // ACTIVE SECTION
        if (activeTasks.isNotEmpty()) {
            item {
                SectionHeader(title = "Active")
            }

            items(
                items = activeTasks,
                key = { it.id }
            ) { task ->
                TaskItem(
                    task = task,
                    onDeleteClick = { onDelete(task) },
                    onToggleCompleted = { onToggle(task, it) },
                    onTaskClick = { onTaskClick(task.id) },
                    modifier = Modifier.animateItem(
                        placementSpec = tween(durationMillis = 300)
                    )
                )
            }
        }

        // COMPLETED SECTION
        if (completedTasks.isNotEmpty()) {

            item {
                CompletedHeader(
                    count = completedTasks.size,
                    isExpanded = isCompletedExpanded,
                    onToggle = { isCompletedExpanded = !isCompletedExpanded }
                )
            }

            item {
                AnimatedVisibility(
                    visible = isCompletedExpanded,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        completedTasks.forEach { task ->
                            TaskItem(
                                task = task,
                                onDeleteClick = { onDelete(task) },
                                onToggleCompleted = { onToggle(task, it) },
                                onTaskClick = { onTaskClick(task.id) },
                                modifier = Modifier.animateItem(
                                    placementSpec = tween(durationMillis = 300)
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyState(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No tasks yet.\nTap + to add one!",
            style = AppTypography.bodyLarge
        )
    }
}

@Composable
private fun CompletedHeader(
    count: Int,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 0f else -90f,
        label = "arrow_rotation"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Completed ($count)",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Toggle Completed",
            modifier = Modifier.rotate(rotation)
        )
    }
}