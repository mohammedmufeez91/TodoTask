package com.mufeez.taskzero.data.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * Created by Mufeez kashimji on 27-09-19.
 */

@Dao
interface TaskDao {

    @get:Query("SELECT * FROM task")
    val allTasks: List<Task>

    @Query("SELECT * FROM task WHERE uid = :uid")
    fun getTask(uid: String): Task

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTask(vararg tasks: Task)

    @Delete
    fun deleteTask(task: Task)

    @Update
    fun updateItem(task: Task)

    /* @Query("UPDATE task SET iscompleted = :iscompletedvalue  WHERE uid = :itemid ")
    void updateItem(boolean iscompletedvalue,int itemid);*/

}
