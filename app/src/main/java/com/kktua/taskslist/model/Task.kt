package com.kktua.taskslist.model

import java.io.Serializable

data class Task(
    val id: Long = 0,
    val task: String
) : Serializable