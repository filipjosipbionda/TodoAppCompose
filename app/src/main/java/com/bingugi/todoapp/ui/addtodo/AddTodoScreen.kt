package com.bingugi.todoapp.ui.addtodo

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bingugi.todoapp.R
import com.bingugi.todoapp.data.model.Todo
import com.bingugi.todoapp.ui.theme.TodoAppTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoScreen(navigateBack: () -> Unit) {
    val addTodoState by rememberSaveable(stateSaver = AddTodoStateHolder.AddTodoSaver) {
        mutableStateOf(
            AddTodoStateHolder()
        )
    }
    val addTodoViewModel: AddTodoViewModel = koinViewModel()
    val configuration = LocalConfiguration.current
    val isInLandscape=configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                id = R.string.add_todo_arrowback_description
                            )
                        )
                    }
                },
                actions = {
                    if (isInLandscape) {
                        IconButton(
                            onClick = {
                                      val todo=Todo(
                                          addTodoState.name,
                                          addTodoState.selectedDate!!,
                                          addTodoState.description,
                                          addTodoState.selectedPriority
                                      )
                                addTodoViewModel.addTodo(todo)
                                navigateBack()
                            },
                            enabled = !addTodoState.isRequiredEntered()
                        ) {
                            Icon(imageVector = Icons.Default.Done, contentDescription = null)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(stringResource(id = R.string.add_new_todo_title))
                },
            )
        }
    ) { paddingValues ->
        AddTodoContent(
            addTodoState,
            isInLandscape,
            {
                addTodoViewModel.addTodo(it)
            }, navigateBack,
            Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun AddTodoContent(
    addTodoState: AddTodoStateHolder,
    isInLandscape:Boolean,
    onAddTodo: (Todo) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
    ) {
            OutlinedTextField(
                label = {
                    Text(text = stringResource(R.string.addtodo_label_name))
                },
                maxLines = 1,
                value = addTodoState.name,
                onValueChange = {
                    addTodoState.name = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                ),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                label = {
                    Text(text = stringResource(R.string.addtodo_label_description))
                },
                maxLines = 5,
                value = addTodoState.description,
                onValueChange = {
                    addTodoState.description = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        label = { Text(text = stringResource(R.string.addtodo_date_label)) },
                        value = if (addTodoState.selectedDate != 0L) convertMillisToDateString(
                            addTodoState.selectedDate
                        ) else "",
                        onValueChange = {
                        },
                        readOnly = true,
                        leadingIcon = {
                            IconButton(onClick = { addTodoState.openDatePicker() }) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = stringResource(
                                        id = R.string.addtodo_select_date_description
                                    )
                                )
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 6.dp)
                    )
                    TodoDropDownMenu(
                        selectedPriority = addTodoState.selectedPriority.name,
                        expanded = addTodoState.showDropDownMenu,
                        onClick = {
                            addTodoState.setPriority(it)
                            addTodoState.showDropDownMenu(false)
                        },
                        onExpandedChange = {
                            addTodoState.showDropDownMenu(it)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 6.dp)
                    )
                }
                if (!isInLandscape) {
                    Text(
                        text = stringResource(id = R.string.addtodo_instructions),
                        fontSize = 10.sp,
                    )
                }
            }
        if(!isInLandscape) {
                OutlinedButton(
                    onClick = {
                        val todo = Todo(
                            name = addTodoState.name,
                            description = addTodoState.description,
                            priority = addTodoState.selectedPriority,
                            date = addTodoState.selectedDate!!,
                        )
                        onAddTodo(todo)
                        navigateBack()
                    },
                    enabled = !addTodoState.isRequiredEntered()
                ) {
                    Text(text = stringResource(R.string.addtodo_button_text))
                }
            }
        }

    if (addTodoState.showDatePicker) {
        TodoDatePicker(
            onDateSelected = { date ->
                addTodoState.setDate(date)
                addTodoState.closeDatePicker()
            },
            onDismiss = { addTodoState.closeDatePicker() },
        )
    }
}

@Preview
@Composable
fun AddTodoScreenPreview() {
    TodoAppTheme {
        AddTodoScreen {}
    }
}

