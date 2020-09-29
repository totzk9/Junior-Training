package com.lig.juntrain.presenter

import androidx.lifecycle.LiveData
import com.lig.juntrain.model.RoomRepository
import com.lig.juntrain.model.Task
import com.lig.juntrain.model.TaskRepository

class MainPresenter (private val repository: TaskRepository = RoomRepository()) : MainContract.Presenter {
    override fun getAllTasks(): LiveData<List<Task>> {
        return repository.getAllTasks()
    }

    override fun deleteTask(task: Task) {
        repository.deleteTask(task)
    }
}