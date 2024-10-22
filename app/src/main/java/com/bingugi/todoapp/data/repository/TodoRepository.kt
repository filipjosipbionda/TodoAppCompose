package com.bingugi.todoapp.data.repository

import com.bingugi.todoapp.data.database.TodoDao
import com.bingugi.todoapp.data.model.Todo

class TodoRepository(private val todoDao: TodoDao) {

    suspend fun insertTodo(todo: Todo){
        todoDao.insertTodo(todo)
    }

    suspend fun deleteExpiredTasks(date: Long){
        todoDao.deleteExpiredTasks(date)
    }

    suspend fun deleteTodo(todo: Todo){
        todoDao.deleteTask(todo)
    }

    fun todos()=todoDao.getTodosStream()
}