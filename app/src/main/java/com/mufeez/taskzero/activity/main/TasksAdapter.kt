package com.mufeez.taskzero.activity.main

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

import com.mufeez.taskzero.R
import com.mufeez.taskzero.activity.newtask.NewTaskActivity
import com.mufeez.taskzero.data.model.Task

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Mufeez kashimji on 27-09-19.
 */

class TasksAdapter(private val mList: MutableList<Task>, private val context: Context, private val presenter: MainPresenter) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    private var mOnClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(v: View, position: Int)
        fun onItemLongClick(v: View, position: Int)
    }

    fun setItemClickListener(l: OnItemClickListener) {
        this.mOnClickListener = l
    }

    fun addTask(task: Task) {
        mList.add(task)
        notifyDataSetChanged()
    }

    fun removeTask(task: Task) {
        if (mList.contains(task)) {
            mList.remove(task)
            notifyDataSetChanged()
        }
    }


    fun getItem(position: Int): Task {
        return mList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.task_layout, parent, false)
        return TaskViewHolder(item)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = mList[position]
        holder.titleTv.text = task.getTitle()
        holder.bodyTv.text = task.getBody()
        holder.timeTv.text = task.createdAtText
        holder.wrapper.setBackgroundResource(task.backgroundColor)
        if (task.getIscompleted()!!) {
            holder.checkBox.isChecked = true
            //mainPresenter.onTaskCompleted(task);
        } else {
            holder.checkBox.isChecked = false
            //mainPresenter.onTaskCompleted(task);
        }

        Log.i("task-a", task.getIscompleted().toString())
        holder.checkBox.setOnClickListener {
            if (holder.checkBox.isChecked) {
                Toast.makeText(context, "clicked yes", Toast.LENGTH_LONG).show()
                //completeTask(task,position,true);
                task.setIscompleted(true)
                presenter.completeTask(task)
            } else {
                Toast.makeText(context, "clicked no", Toast.LENGTH_LONG).show()
                task.setIscompleted(false)
                presenter.completeTask(task)
                //completeTask(task,position,false);
            }
        }

        holder.editBbtn.setOnClickListener {
            //task.setIscompleted(true);
            //presenter.editTask(task);

            //Task newtask=new Task(task.getTitle(),task.getBody(),task.getPriority(),task.getIscompleted());

            Log.i("Task-ad-uid", task.getUid().toString())

            val i = Intent(context, NewTaskActivity::class.java)
            i.putExtra("edit", task)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {
        var titleTv: TextView
        var timeTv: TextView
        var bodyTv: TextView
        var wrapper: RelativeLayout
        var checkBox: CheckBox
        var editBbtn: ImageButton

        init {
            titleTv = itemView.findViewById(R.id.title_tv)
            timeTv = itemView.findViewById(R.id.time_tv)
            bodyTv = itemView.findViewById(R.id.body_tv)
            wrapper = itemView.findViewById(R.id.wrapper)
            checkBox = itemView.findViewById(R.id.task_completed_checkbox)
            editBbtn = itemView.findViewById(R.id.edit_btn)
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View) {
            mOnClickListener!!.onItemClick(v, adapterPosition)
        }

        override fun onLongClick(v: View): Boolean {
            mOnClickListener!!.onItemLongClick(v, adapterPosition)
            return false
        }
    }
}
