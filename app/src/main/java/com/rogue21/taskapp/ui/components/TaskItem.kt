package com.rogue21.taskapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.rogue21.taskapp.model.Task
import com.rogue21.taskapp.ui.theme.AppTypography

@Composable
fun TaskItem(
    task: Task,
    onDeleteClick: () -> Unit,
    onToggleCompleted: (Boolean) -> Unit,
    onTaskClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = if (task.isCompleted) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        MaterialTheme.colorScheme.surface
    }

    val contentAlpha = if (task.isCompleted) 0.6f else 1f

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTaskClick() },
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = onToggleCompleted
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Text(
                    text = task.title,
                    style = AppTypography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    textDecoration = if (task.isCompleted)
                        TextDecoration.LineThrough
                    else
                        TextDecoration.None,
                    modifier = Modifier.alpha(contentAlpha)
                )

                if (task.description.isNotBlank()) {
                    Text(
                        text = task.description,
                        style = AppTypography.bodyMedium,
//                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.alpha(contentAlpha)
                    )
                }
            }

            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Task"
                )
            }
        }
    }
}