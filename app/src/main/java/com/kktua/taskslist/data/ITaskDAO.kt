package com.kktua.taskslist.data

import com.kktua.taskslist.model.Task

interface ITaskDAO {
    fun insert(task: Task): Boolean
    fun update(task: Task): Boolean
    fun delete(task: Task): Boolean
    fun list(): List<Task>
}