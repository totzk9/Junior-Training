package com.lig.juntrain.presenter

import android.text.TextUtils
import androidx.lifecycle.LiveData
import com.lig.juntrain.model.RoomRepository
import com.lig.juntrain.model.Task
import com.lig.juntrain.model.TaskRepository
import kotlinx.android.synthetic.main.activity_new_task.*

class NewTaskPresenter (private val repository: TaskRepository = RoomRepository()) :
    BasePresenter<NewTaskContract.View>(), NewTaskContract.Presenter {

    fun validateText(string: CharSequence) : Boolean {
        return TextUtils.isEmpty(string)
    }

    override fun insertTask(task: Task) {
        repository.saveTask(task)
    }

}