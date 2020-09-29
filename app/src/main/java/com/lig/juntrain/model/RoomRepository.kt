package com.lig.juntrain.model

import androidx.lifecycle.LiveData
import java.util.concurrent.Executors

class RoomRepository : TaskRepository {
    private val dao: TaskDao = TaskApplication.database.taskDao()


    private val executor = Executors.newSingleThreadExecutor()

    override fun saveTask(task: Task) {
        executor.execute {
            dao.insertTask(task)
        }
    }

    override fun getAllTasks(): LiveData<List<Task>> {
        return dao.getAllTasks()
    }

    override fun deleteTask(task: Task) {
        executor.execute {
            dao.deleteTaskStatus(task)
        }
    }

//    override fun getTask(taskId: Int): LiveData<Task> {
//        return dao.getTask(taskId)
//    }
}