package com.bingugi.todoapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bingugi.todoapp.AddTodo
import com.bingugi.todoapp.Home
import com.bingugi.todoapp.ui.addtodo.AddTodoScreen
import com.bingugi.todoapp.ui.todolist.HomeScreen

@Composable
fun TodoApp(){

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Home.route
    ) {
        composable(route= Home.route){
            HomeScreen(navController = navController)
        }
        composable(
            route = AddTodo.route,
        ) {
            AddTodoScreen {
                navController.popBackStack()
            }
        }
    }
}