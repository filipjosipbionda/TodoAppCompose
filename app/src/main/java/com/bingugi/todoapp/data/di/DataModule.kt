package com.bingugi.todoapp.data.di
import androidx.room.Room
import com.bingugi.todoapp.data.database.AppDatabase
import com.bingugi.todoapp.data.repository.TodoRepository
import com.bingugi.todoapp.ui.addtodo.AddTodoViewModel
import com.bingugi.todoapp.ui.todolist.TodoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule= module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "todo_database"
        ).build()
    }

    single {
        get<AppDatabase>().todoDao()
    }

    single {
        TodoRepository(get())
    }
}