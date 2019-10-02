package com.mufeez.taskzero.activity.newtask

import com.mufeez.taskzero.base.BaseView
import com.mufeez.taskzero.data.model.Task

/**
 * Created by Mufeez kashimji on 27-09-19.
 */

interface NewTaskView : BaseView {
    fun onTaskAdded(task: Task)
}
