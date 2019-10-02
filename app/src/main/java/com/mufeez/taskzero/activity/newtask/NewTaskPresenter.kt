package com.mufeez.taskzero.activity.newtask

import com.mufeez.taskzero.base.BasePresenter
import com.mufeez.taskzero.data.model.Task
import com.mufeez.taskzero.data.model.TaskBuilder
import com.mufeez.taskzero.data.repository.TaskRepository

/**
 * Created by Mufeez kashimji on 27-09-19.
 */

class NewTaskPresenter : BasePresenter<NewTaskView>() {

    private var mTasksRepo: TaskRepository? = null

    override fun attachView(view: NewTaskView) {
        super.attachView(view)
        mTasksRepo = TaskRepository.getInstance(view.context)
    }

    fun saveTask(title: String, body: String, priority: Int) {
        val task = TaskBuilder().setTitle(title).setBody(body).setCompleted(false).setPriority(priority).build()
        mTasksRepo!!.addTask(task)
        view!!.onTaskAdded(task)
    }
}
