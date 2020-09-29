package com.lig.juntrain.presenter

import androidx.lifecycle.LiveData
import com.lig.juntrain.model.RoomRepository
import com.lig.juntrain.model.Task
import com.lig.juntrain.model.TaskRepository

class EditTaskPresenter (private val repository: TaskRepository = RoomRepository()) : BasePresenter<TaskContract.View>(), TaskContract.Presenter {

    override fun insertTask(task: Task) {
        repository.saveTask(task)
    }

}