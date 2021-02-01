package com.example.faceeditor.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.faceeditor.database.DBInterface
import com.example.faceeditor.database.FilterInput
import com.example.faceeditor.database.Member
import com.example.faceeditor.database.MemberList
import com.example.faceeditor.extension.Resource
import com.example.faceeditor.extension.SingleLiveEvent
import com.example.faceeditor.models.RemoteManager
import kotlinx.coroutines.*

enum class TaskType{
    All, Faces, Logs, Names
}

class TaskViewModel(
    private var dbManger: DBInterface,
    private var remoteService: RemoteManager
): ViewModel(){

    val dataLoading = SingleLiveEvent<Boolean>()

    private var controll = ControlledTaskBehavior<MutableList<Member>>()
    var contactsLiveData: MutableLiveData<MutableList<Member>> = MutableLiveData()

    suspend fun getTasks(type: TaskType = TaskType.All){

        dataLoading.value = true

        val local = dbManger.getFaces(FilterInput(sortOrder = "asc"))
        contactsLiveData.value = local
        val job = withContext(Dispatchers.IO) {
            controll.cancelPreviousThenRun {
                delay(1000L)
                val result = when (type) {
                    TaskType.All -> remoteService.getAllMembers()
//                TaskType.Faces -> repository.getFacesFromRealm()
//                TaskType.Logs -> repository.getLogsFromRealm()
//                TaskType.Names -> repository.getNamesFromRealm()
                    else -> remoteService.getAllMembers()
                }
                result.data?.results?.takeIf { it.isNotEmpty() }?.run {

                    dbManger.insertFaces(this)
                    this
                } ?: mutableListOf()
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