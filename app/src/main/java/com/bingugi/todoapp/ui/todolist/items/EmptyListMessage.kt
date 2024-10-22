package com.bingugi.todoapp.ui.todolist.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bingugi.todoapp.R
import com.bingugi.todoapp.ui.theme.TodoAppTheme

@Composable
fun EmptyListMessage(isInLandscape:Boolean,modifier: Modifier=Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier=modifier
    ) {
        Text(
            text = stringResource(id = R.string.tv_notasks_text),
            textAlign = TextAlign.Center,
            modifier=Modifier.padding(if(isInLandscape)40.dp else 0.dp)
        )
    }
}


@Preview
@Composable
fun EmptyListMessagePreview(){
    TodoAppTheme {
        Surface {
            EmptyListMessage(false)
        }
    }
}