package com.bingugi.todoapp.ui.todolist
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bingugi.todoapp.data.model.Todo
import com.bingugi.todoapp.data.repository.TodoRepository
import com.bingugi.todoapp.ui.addtodo.convertMillisToDateString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.ZoneId

class TodoListViewModel(private val repository: TodoRepository):ViewModel() {
    private val _todos = MutableStateFlow<Map<String, List<Todo>>>(emptyMap())
    val todos: StateFlow<Map<String, List<Todo>>>
        get() = _todos

    init {
        val todayDate = LocalDate.now()
        viewModelScope.launch {
            val todayInMillis = getDateInMillis(todayDate)

            withContext(Dispatchers.IO) {
                repository.todos().collect { todos ->
                    repository.deleteExpiredTasks(todayInMillis)
                    _todos.value = groupTodosByDate(todos)
                }
            }
        }
    }

    fun delete(todo: Todo) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteTodo(todo)
            }
        }
    }


    private fun groupTodosByDate(todos: List<Todo>): Map<String, List<Todo>> {
        return todos
            .sortedWith(compareBy<Todo> { it.date }.thenBy { it.priority }.thenBy { it.name })
            .groupBy { convertMillisToDateString(it.date) }
    }

    private fun getDateInMillis(date: LocalDate): Long {
        return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}



