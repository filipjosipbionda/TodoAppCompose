package com.bingugi.todoapp.ui.todolist.items.todoitem

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bingugi.todoapp.data.model.Priority
import com.bingugi.todoapp.data.model.Todo
import com.bingugi.todoapp.ui.theme.High
import com.bingugi.todoapp.ui.theme.Low
import com.bingugi.todoapp.ui.theme.Medium
import com.bingugi.todoapp.ui.theme.Urgent

@Composable
fun TodoListItem(todo: Todo,modifier: Modifier = Modifier,
) {
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    var isChecked by rememberSaveable {
        mutableStateOf(false)
    }

        ListItem(
            leadingContent = {
                Box(
                    modifier = modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(setColorBasedOnPriority(todo.priority))
                )
            },
            headlineContent = { Text(text = todo.name) },
            supportingContent = {
                Text(
                    text = todo.description,
                    maxLines = if (isExpanded) 3 else 1,
                    modifier = Modifier
                        .animateContentSize()
                )
            },
            trailingContent = {
                             Checkbox(checked = isChecked, onCheckedChange ={ isChecked=!isChecked } )
            },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onSurface)
                .clickable {
                    isExpanded = !isExpanded
                }
        )
}

fun setColorBasedOnPriority(priority: Priority): Color {
    return when (priority) {
        Priority.Urgent -> Urgent
        Priority.High -> High
        Priority.Medium -> Medium
        Priority.Low -> Low
    }
}
