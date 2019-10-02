package com.mufeez.taskzero.activity.main

import com.mufeez.taskzero.base.BaseView
import com.mufeez.taskzero.data.model.Task

/**
 * Created by Mufeez kashimji on 27-09-19.
 */

interface MainView : BaseView {
    fun onTasks(tasks: MutableList<Task>)
    fun onTaskDeleted(task: Task)
    fun onTaskAdded(task: Task)
    fun onTaskCompleted(task: Task)
}
