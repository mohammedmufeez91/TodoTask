package com.mufeez.taskzero.data.repository

import com.mufeez.taskzero.data.model.Task

/**
 * Created by Mufeez kashimji on 27-09-19.
 */

interface TaskRepositoryHelper {
    val allTasks: MutableList<Task>

    fun addTask(task: Task)
    fun deleteTask(task: Task)
    fun completeTask(task: Task)
}
