package com.example.faceeditor.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.faceeditor.database.DBInterface
import com.example.faceeditor.database.FilterInput
import com.example.faceeditor.database.FilterOutput
import com.example.faceeditor.extension.SingleLiveEvent
import kotlinx.coroutines.*

enum class TaskType{
    All, Faces, Logs, Names
}

class TaskViewModel(private var DBManger: DBInterface): ViewModel(){

    val dataLoading = SingleLiveEvent<Boolean>()

    private var controll = ControlledTaskBehavior<MutableList<FilterOutput>>()
    var contactsLiveData: MutableLiveData<MutableList<FilterOutput>> = MutableLiveData()

    suspend fun getTasks(type: TaskType = TaskType.All, filter: FilterInput? = null){

        dataLoading.value = true

         val job = withContext(Dispatchers.IO) {
             controll.cancelPreviousThenRun {
                 delay(1000L)
                 val result = when (type) {
                     TaskType.All -> DBManger.getFaces(filter)
//                TaskType.Faces -> repository.getFacesFromRealm()
//                TaskType.Logs -> repository.getLogsFromRealm()
//                TaskType.Names -> repository.getNamesFromRealm()
                     else -> DBManger.getFaces(null)
                 }
                 result
             }
        }

        contactsLiveData.value = job

        dataLoading.value = false
    }
}

class ControlledTaskBehavior<T>{

    private var cachedTasks: Deferred<T>? = null

    suspend fun cancelPreviousThenRun(block: suspend () -> T): T{

        cachedTasks?.cancelAndJoin()

        return coroutineScope {
            // 建立一個 async 並且 suspend
            val newTask = async {
                block()
            }

            // newTask 執行完畢時清除舊的 cachedTasks 任務
            newTask.invokeOnCompletion {
                cachedTasks = null
            }

            // newTask 完成後交給 cachedTasks
            cachedTasks = newTask
            // newTask 恢復狀態並開始執行
            newTask.await()
        }
    }
}