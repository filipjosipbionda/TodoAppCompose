package com.bingugi.todoapp.ui.todolist.items.todoheader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TodoListHeader(date:String,modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primaryContainer)) {
        Text(
            text = date,
            fontSize=16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(
                vertical = 4.dp,
                horizontal = 8.dp
            )
        )
    }
}