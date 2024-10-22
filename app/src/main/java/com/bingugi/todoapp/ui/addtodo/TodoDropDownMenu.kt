package com.bingugi.todoapp.ui.addtodo
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bingugi.todoapp.R
import com.bingugi.todoapp.data.model.Priority

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDropDownMenu(selectedPriority:String,expanded:Boolean,onExpandedChange:(Boolean)->Unit,onClick:(Priority)->Unit,modifier:Modifier=Modifier) {

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange =onExpandedChange,modifier=modifier ){
        OutlinedTextField(
            value = selectedPriority,
            onValueChange ={},
            readOnly = true,
            label = {Text(stringResource(R.string.addtodo_ddmenu_label)) },
            modifier = modifier.fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable,true)
            )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
        ) {
            Priority.entries.forEach {
                DropdownMenuItem(text = { Text(text = it.name)}, onClick = {onClick(it)})
            }
        }
    }
}