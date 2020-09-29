package com.lig.juntrain.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)

    @Delete
    fun deleteTaskStatus(vararg task: Task)

//    @Query("SELECT * FROM task WHERE taskId = :taskId")
//    fun getTask(taskId: Int): LiveData<Task>

    @Query("SELECT * FROM task")
    fun getAllTasks(): LiveData<List<Task>>
}