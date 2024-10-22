@file:Suppress("NAME_SHADOWING")

package com.bingugi.todoapp.ui.todolist

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.bingugi.todoapp.AddTodo
import com.bingugi.todoapp.R
import com.bingugi.todoapp.data.model.Todo
import com.bingugi.todoapp.ui.todolist.items.EmptyListMessage
import com.bingugi.todoapp.ui.todolist.items.todoheader.TodoListHeader
import com.bingugi.todoapp.ui.todolist.items.todoitem.SwipeToDeleteContainer
import com.bingugi.todoapp.ui.todolist.items.todoitem.TodoListItem
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    todoListViewModel: TodoListViewModel = koinViewModel()
) {
    val todos by todoListViewModel.todos.collectAsStateWithLifecycle()
    var showEmptyListMessage by remember {
        mutableStateOf(false)
    }
    val configuration = LocalConfiguration.current
    val isInLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(stringResource(id = R.string.app_name))
                },
            )
        },
        floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigateSingleTopTo(AddTodo.route) },
                    modifier=Modifier.navigationBarsPadding(),
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.tdlist_fab_text)
                    )}
            }
    ) { innerPadding ->
        LaunchedEffect(key1 = todos) {
            delay(500L)
            if (todos.isEmpty()) {
                showEmptyListMessage = true
            }
        }
        if (showEmptyListMessage) {
            EmptyListMessage(
                isInLandscape,
                modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        } else {
            HomeContent(
                todos = todos,
                onRemove = { todoListViewModel.delete(it) },
                modifier = modifier.padding(innerPadding)
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    todos: Map<String, List<Todo>>,
    onRemove: (Todo) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        for ((date, tasks) in todos) {
            stickyHeader {
                TodoListHeader(date = date)
            }
            items(
                items = tasks,
                key = { it.id }
            ) { todo ->
                SwipeToDeleteContainer(
                    todo = todo,
                    onDelete = { todo ->
                        onRemove(todo)
                    },
                ) { todo ->
                    Column {
                        TodoListItem(
                            todo = todo,
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

private fun NavController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}











