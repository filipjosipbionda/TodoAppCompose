package com.bingugi.todoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    val name:String,
    val date:Long,
    val description:String,
    val priority: Priority
){
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}