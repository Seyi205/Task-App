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
    task: Task,                                 // The data to display
    onDeleteClick: () -> Unit,                  // What happens when delete is tapped
    onToggleCompleted: (Boolean) -> Unit,       // What happens when checkbox changes
    onTaskClick: () -> Unit,                    // What happens when card is tapped
    modifier: Modifier = Modifier               // Optional styling from the parent
) {
    val containerColor = if (task.isCompleted) {
        MaterialTheme.colorScheme.surfaceVariant        // greyed out
    } else {
        MaterialTheme.colorScheme.surface               // normal
    }

    val contentAlpha = if (task.isCompleted) 0.6f else 1f   // 60% vs 100% opacity

    Card(
        modifier = modifier
            .fillMaxWidth()                             // stretch to full screen width
            .clickable { onTaskClick() },               // entire card is tappable
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        // 2.dp elevation creates a subtle drop shadow, just enough to lift it off the background.
    ) {
        Row(
            modifier = Modifier
            .padding(16.dp),                                 // 16dp breathing room on all sides
            verticalAlignment = Alignment.CenterVertically,         // items aligned to middle
            horizontalArrangement = Arrangement.spacedBy(12.dp)     // 12dp gap between children
        ) {

            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = onToggleCompleted
            )

            Column(
                modifier = Modifier.weight(1f),                 // <- takes ALL remaining horizontal space
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Text(
                    text = task.title,
                    style = AppTypography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold        // slightly bold
                    ),
                    textDecoration = if (task.isCompleted)
                        TextDecoration.LineThrough              // strikethrough
                    else
                        TextDecoration.None,
                    modifier = Modifier
                        .alpha(contentAlpha)      // faded if completed
                )

                if (task.description.isNotBlank()) {
                    Text(
                        text = task.description,
                        style = AppTypography.bodyMedium,
//                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,     // muted color
                        modifier = Modifier.alpha(contentAlpha)
                    )
                }
            }

            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,             // built-in trash icon
                    contentDescription = "Delete Task"              // for accessibility / screen readers
                )
            }
        }
    }
}

//            The Layout Structure
//
//            This is the hierarchy of composables (UI elements nested inside each other):
//            Card  <- the rounded rectangle container
//            └── Row  <- horizontal arrangement
//            ├── Checkbox  <- left side
//            ├── Column    <- middle, takes up remaining space
//            │    ├── Text (title)
//            │    └── Text (description) <- only shown if not blank
//            └── IconButton (delete)  <- right side