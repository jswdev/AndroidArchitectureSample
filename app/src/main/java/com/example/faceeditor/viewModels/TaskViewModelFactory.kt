package com.example.faceeditor.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.faceeditor.database.DBInterface
import com.example.faceeditor.models.RemoteManager

@Suppress("UNCHECKED_CAST")
class TaskViewModelFactory constructor(

    private val dbManager: DBInterface,
    private val remoteService: RemoteManager
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(TaskViewModel::class.java) ->
                    TaskViewModel(dbManager, remoteService)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}