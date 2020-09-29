package com.lig.juntrain.presenter

import androidx.lifecycle.LiveData
import com.lig.juntrain.model.Task

interface NewTaskContract {

    interface View {
        fun showError()
        fun pendingButtonPressed()
        fun doneButtonPressed()
    }

    interface Presenter {
        fun insertTask(task: Task)
    }

}