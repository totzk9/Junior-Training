package com.lig.juntrain.model

import androidx.lifecycle.LiveData

interface TaskRepository {
    fun saveTask(task: Task)
    fun getAllTasks(): LiveData<List<Task>>
    fun deleteTask(task: Task)
//    fun getTask(taskId: Int): LiveData<Task>
}