package com.mufeez.taskzero.data.model

/**
 * Created by Mufeez kashimji on 27-09-19.
 */
/**
 * Builder for Task calss
 */
class TaskBuilder {
    private var title: String? = null
    private val createdAt: Long = 0
    private var body: String? = null
    private var priority: Int = 0
    private var iscompleted: Boolean = false

    fun setTitle(title: String): TaskBuilder {
        this.title = title
        return this
    }

    fun setBody(body: String): TaskBuilder {
        this.body = body
        return this
    }

    fun setPriority(priority: Int): TaskBuilder {
        this.priority = priority
        return this
    }

    fun setCompleted(completed: Boolean): TaskBuilder {
        this.iscompleted = completed
        return this
    }

    fun build(): Task {
        return Task(title, body, priority, iscompleted)
    }
}
