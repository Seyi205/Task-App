package com.rogue21.taskapp.ui.screens.addedit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rogue21.taskapp.ui.theme.AppTextFieldDefaults
import com.rogue21.taskapp.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    taskId: Int?,
    onBackClick: () -> Unit,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is AddEditTaskEffect.NavigateBack -> onBackClick()
            }
        }
    }

    LaunchedEffect(taskId) {
        if (taskId != null) {
            viewModel.loadTask(taskId)
        }
    }

    val titleText = when {
        taskId == null && uiState.isSaving -> "Adding..."
        taskId == null -> "Add Task"
        uiState.isSaving -> "Updating..."
        else -> "Edit Task"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(titleText) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // FORM CARD
            Card(
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

//                    // Completion toggle
//                    if (taskId != null) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .clickable {
//                                    viewModel.onCompletedChange(!uiState.isCompleted)
//                                },
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Checkbox(
//                                checked = uiState.isCompleted,
//                                onCheckedChange = viewModel::onCompletedChange
//                            )
//
//                            Text(
//                                text = if (uiState.isCompleted) "Completed" else "Mark as completed",
//                                style = AppTypography.bodyLarge
//                            )
//                        }
//                    }

                    // Title field
                    OutlinedTextField(
                        value = uiState.taskTitle,
                        onValueChange = viewModel::onTitleChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Task Title") },
                        placeholder = { Text("Enter task title") },
                        colors = AppTextFieldDefaults.outlinedColors(),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        singleLine = true
                    )

                    // Description field
                    OutlinedTextField(
                        value = uiState.description,
                        onValueChange = viewModel::onDescriptionChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Description") },
                        placeholder = { Text("Enter description") },
                        colors = AppTextFieldDefaults.outlinedColors(),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        maxLines = 4
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Primary action stays OUTSIDE the card
            Button(
                onClick = viewModel::saveTask,
                enabled = uiState.isSaveEnabled && !uiState.isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (uiState.isSaving) "Saving..." else "Save Task"
                )
            }
        }
    }
}