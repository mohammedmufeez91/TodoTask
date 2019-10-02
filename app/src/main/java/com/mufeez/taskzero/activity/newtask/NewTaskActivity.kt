package com.mufeez.taskzero.activity.newtask

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

import com.mufeez.taskzero.R
import com.mufeez.taskzero.activity.main.MainActivity
import com.mufeez.taskzero.activity.main.MainPresenter
import com.mufeez.taskzero.activity.main.MainView
import com.mufeez.taskzero.base.BaseActivity
import com.mufeez.taskzero.data.model.Task
import com.mufeez.taskzero.widgets.CircleImageView

/**
 * Created by Mufeez kashimji on 27-09-19.
 */

class NewTaskActivity : BaseActivity(), NewTaskView, MainView, View.OnClickListener {

    private var mPresenter: NewTaskPresenter? = null
    private var editPresenter: MainPresenter? = null
    private var lowPriorityIv: CircleImageView? = null
    private var normalPriorityIv: CircleImageView? = null
    private var highPriorityIv: CircleImageView? = null
    private var saveIv: ImageView? = null
    private var titleInput: EditText? = null
    private var bodyInput: EditText? = null
    internal var i: Intent? = null
    internal var isEdit = false
    /**
     * the priority for current task, by default have Task.PRIORITY_DEFAULT
     * if user choose other priority will be overridden
     */
    private var mPriority = Task.PRIORITY_DEFAULT
    var task: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)

        lowPriorityIv = findViewById(R.id.low_priority_iv)
        normalPriorityIv = findViewById(R.id.normal_priority_iv)
        highPriorityIv = findViewById(R.id.high_priority_iv)
        saveIv = findViewById(R.id.save_iv)
        titleInput = findViewById(R.id.title_input)
        bodyInput = findViewById(R.id.body_input)

        saveIv!!.setOnClickListener(this)
        lowPriorityIv!!.setOnClickListener(this)
        normalPriorityIv!!.setOnClickListener(this)
        highPriorityIv!!.setOnClickListener(this)

        mPresenter = NewTaskPresenter()
        editPresenter = MainPresenter()
        mPresenter!!.attachView(this)
        editPresenter!!.attachView(this)

        val intent = intent

        if (intent != null) {
            task = intent.getParcelableExtra("edit")
            if (task != null) {
                titleInput!!.setText(task!!.getTitle())
                bodyInput!!.setText(task!!.getBody())
                isEdit = true
                //saveIv.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_system_update_alt_black_24dp));
            }
        }
    }

    override fun onTasks(tasks: MutableList<Task>) {

    }

    override fun onTaskDeleted(task: Task) {

    }

    override fun onTaskAdded(task: Task) {
        onBackPressed()
    }

    override fun onTaskCompleted(task: Task) {
        Toast.makeText(this, "successfully updated task", Toast.LENGTH_SHORT).show()
        //onBackPressed();
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.low_priority_iv -> if (mPriority != Task.PRIORITY_LOW) {
                mPriority = Task.PRIORITY_LOW
                lowPriorityIv!!.borderColor = resources.getColor(R.color.colorPrimaryDark)

                highPriorityIv!!.borderColor = resources.getColor(android.R.color.transparent)
                normalPriorityIv!!.borderColor = resources.getColor(android.R.color.transparent)
            } else {
                mPriority = Task.PRIORITY_DEFAULT
                lowPriorityIv!!.borderColor = resources.getColor(android.R.color.transparent)
            }
            R.id.normal_priority_iv -> if (mPriority != Task.PRIORITY_NORMAL) {
                mPriority = Task.PRIORITY_NORMAL
                normalPriorityIv!!.borderColor = resources.getColor(R.color.colorPrimaryDark)

                highPriorityIv!!.borderColor = resources.getColor(android.R.color.transparent)
                lowPriorityIv!!.borderColor = resources.getColor(android.R.color.transparent)
            } else {
                mPriority = Task.PRIORITY_DEFAULT
                normalPriorityIv!!.borderColor = resources.getColor(android.R.color.transparent)
            }
            R.id.high_priority_iv ->
                //if chosen
                if (mPriority != Task.PRIORITY_HIGH) {
                    mPriority = Task.PRIORITY_HIGH
                    highPriorityIv!!.borderColor = resources.getColor(R.color.colorPrimaryDark)

                    normalPriorityIv!!.borderColor = resources.getColor(android.R.color.transparent)
                    lowPriorityIv!!.borderColor = resources.getColor(android.R.color.transparent)
                } else {
                    mPriority = Task.PRIORITY_DEFAULT
                    highPriorityIv!!.borderColor = resources.getColor(android.R.color.transparent)
                }//if user click again when it already chosen nee to set priority to DEFAULT and un chose the circle
            R.id.save_iv -> {

                val title = titleInput!!.text.toString()
                val body = bodyInput!!.text.toString()
                if (title == null || TextUtils.isEmpty(title.trim { it <= ' ' })) {
                    showMessage(R.string.empty_title_message)
                    return
                }
                if (isEdit == true) {
                    Log.i("editing-title", title)
                    /*
                    Task newtask=new Task(title,body,mPriority,false);
*/
                    task!!.setTitle(title)
                    task!!.setBody(body)
                    task!!.setPriority(mPriority)
                    editPresenter!!.completeTask(task!!)

                } else {
                    mPresenter!!.saveTask(title, body, mPriority)
                }
            }
        }
    }
}
