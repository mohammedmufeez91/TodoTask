package com.mufeez.taskzero.activity.main

import com.mufeez.taskzero.base.BasePresenter
import com.mufeez.taskzero.data.model.Task
import com.mufeez.taskzero.data.repository.TaskRepository

/**
 * Created by Mufeez kashimji on 27-09-19.
 */

class MainPresenter : BasePresenter<MainView>(), TaskRepository.TaskRepoObserver {

    private var mTasksRepo: TaskRepository? = null

    override fun attachView(view: MainView) {
        super.attachView(view)
        mTasksRepo = TaskRepository.getInstance(view.context)
        mTasksRepo!!.addObserver(this)
    }

    override fun detachView() {
        super.detachView()
        mTasksRepo!!.removeObserver(this)
    }

    fun fetchAllTasks() {
        val l = mTasksRepo!!.allTasks
        view!!.onTasks(l)

    }

    fun deleteTask(t: Task) {
        mTasksRepo!!.deleteTask(t)
    }

    fun completeTask(t: Task) {
        mTasksRepo!!.completeTask(t)
    }

    /**
     * get notified from TaskRepoObserver
     * @param task
     */
    override fun onTaskAdded(task: Task) {
        view!!.onTaskAdded(task)
    }

    /**
     * get notified from TaskRepoObserver
     * @param task
     */
    override fun onTaskDeleted(task: Task) {
        view!!.onTaskDeleted(task)
    }

    override fun onTaskCompleted(task: Task) {
        view!!.onTaskCompleted(task)
    }


}
