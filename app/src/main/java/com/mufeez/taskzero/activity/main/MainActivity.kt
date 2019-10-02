package com.mufeez.taskzero.activity.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.mufeez.taskzero.R
import com.mufeez.taskzero.activity.newtask.NewTaskActivity
import com.mufeez.taskzero.base.BaseActivity
import com.mufeez.taskzero.data.model.Task
import androidx.recyclerview.widget.*

class MainActivity : BaseActivity(), MainView, View.OnClickListener, TasksAdapter.OnItemClickListener {

    private var mPresenter: MainPresenter? = null
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: TasksAdapter? = null
    private var noTasksTv: TextView? = null
    private var addTaskIv: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPresenter = MainPresenter()
        mPresenter!!.attachView(this)

        mRecyclerView = findViewById(R.id.recycler_view)
        noTasksTv = findViewById(R.id.no_tasks_tv)
        addTaskIv = findViewById(R.id.add_task_iv)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        mRecyclerView!!.layoutManager = mLayoutManager
        addTaskIv!!.setOnClickListener(this)

        mPresenter!!.fetchAllTasks()

    }

    override fun onTasks(tasks: MutableList<Task>) {
        if (tasks.size == 0)
            noTasksTv!!.visibility = View.VISIBLE
        else {
            noTasksTv!!.visibility = View.GONE
        }
        mAdapter = TasksAdapter(tasks, this, mPresenter!!)
        mAdapter!!.setItemClickListener(this)
        mRecyclerView!!.adapter = mAdapter
    }

    override fun onTaskAdded(task: Task) {
        mAdapter!!.addTask(task)
        if (mAdapter!!.itemCount == 0)
            noTasksTv!!.visibility = View.VISIBLE
        else {
            noTasksTv!!.visibility = View.GONE
        }
        showMessage("\"" + task.getTitle() + "\"" + "was added successfully")
    }


    override fun onTaskCompleted(task: Task) {
        showMessage("\"" + task.getTitle() + "\"" + "was Completed successfully")
    }


    override fun onTaskDeleted(task: Task) {
        mAdapter!!.removeTask(task)
        if (mAdapter!!.itemCount == 0)
            noTasksTv!!.visibility = View.VISIBLE
        else {
            noTasksTv!!.visibility = View.GONE
        }
        showMessage("\"" + task.getTitle() + "\"" + "was deleted successfully")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.add_task_iv -> openNewTaskActivity()
        }//mPresenter.addTask();
    }

    override fun onItemClick(v: View, position: Int) {
        val t = mAdapter!!.getItem(position)
        showMessage(t.getTitle())
    }

    override fun onItemLongClick(v: View, position: Int) {
        val t = mAdapter!!.getItem(position)
        mPresenter!!.deleteTask(t)
    }


    fun openNewTaskActivity() {
        val intent = Intent(this, NewTaskActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter!!.detachView()
    }
}
