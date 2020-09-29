package com.lig.juntrain.presenter

import androidx.lifecycle.LiveData
import com.lig.juntrain.model.Task

interface MainContract {

    interface Presenter {
        fun getAllTasks(): LiveData<List<Task>>
        fun deleteTask(task: Task)
    }
}