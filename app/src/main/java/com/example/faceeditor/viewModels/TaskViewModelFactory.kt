package com.example.faceeditor.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.faceeditor.database.DBInterface

@Suppress("UNCHECKED_CAST")
class TaskViewModelFactory constructor(

    private val DBManager: DBInterface
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(TaskViewModel::class.java) ->
                    TaskViewModel(DBManager)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}