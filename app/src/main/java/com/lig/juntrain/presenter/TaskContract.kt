package com.lig.juntrain.presenter

import androidx.lifecycle.LiveData
import com.lig.juntrain.model.Task

interface TaskContract {

    interface View {
        fun initView(task: Task)
    }

    interface Presenter {
        fun insertTask(task: Task)
    }

}