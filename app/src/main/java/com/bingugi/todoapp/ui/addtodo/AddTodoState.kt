package com.bingugi.todoapp.ui.addtodo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import com.bingugi.todoapp.data.model.Priority

class AddTodoStateHolder(
    initialName: String = "",
    initialDescription: String = "",
    initialSelectedDate: Long?=null,
    initialShowDatePicker: Boolean=false,
    initialPriority: Priority = Priority.Low,
    initialShowDropDownMenu:Boolean=false,
) {
    var name by mutableStateOf(initialName)
    var description by mutableStateOf(initialDescription)
    var selectedDate by mutableStateOf(initialSelectedDate)
    var showDatePicker by mutableStateOf(initialShowDatePicker)
    var selectedPriority by mutableStateOf(initialPriority)
        private set
    var showDropDownMenu by mutableStateOf(initialShowDropDownMenu)
        private set

    fun openDatePicker() {
        showDatePicker = true
    }

    fun closeDatePicker() {
        showDatePicker = false
    }

    fun setDate(dateMillis: Long?) {
        selectedDate = dateMillis
    }

    fun showDropDownMenu(value:Boolean){
        showDropDownMenu=value
    }
    fun setPriority(priority: Priority){
        selectedPriority=priority
    }

    fun isRequiredEntered():Boolean{
        return name.isEmpty() || selectedDate==null
    }

    companion object{
        val AddTodoSaver: Saver<AddTodoStateHolder, *> = Saver(
            save = { addTodoStateHolder ->
                listOf(addTodoStateHolder.name,addTodoStateHolder.description, addTodoStateHolder.selectedDate, addTodoStateHolder.showDatePicker,addTodoStateHolder.selectedPriority,addTodoStateHolder.showDropDownMenu)
            },
            restore={restoredList ->
                val restoredName= restoredList[0] as String
                val restoredDescription= restoredList[1] as String
                val restoredDate = restoredList[2] as Long?
                val restoredShowDatePicker = restoredList[3] as Boolean
                val restoredPriority=restoredList[4] as Priority
                val restoredShowDropDownMenu=restoredList[5] as Boolean

                AddTodoStateHolder(
                    restoredName,
                    restoredDescription,
                    restoredDate,
                    restoredShowDatePicker,
                    restoredPriority,
                    restoredShowDropDownMenu
                )
            }
        )
    }
}