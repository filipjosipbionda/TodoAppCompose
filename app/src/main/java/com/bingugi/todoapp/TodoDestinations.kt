package com.bingugi.todoapp
interface TodoDestinations{
    val route:String
}

object Home:TodoDestinations{
    override val route: String
        get() = "homescreen"
}

object AddTodo:TodoDestinations{
    override val route: String
        get() = "addtodoscreen"
}

