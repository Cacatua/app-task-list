package com.kktua.taskslist.data

import android.content.ContentValues
import android.content.Context
import com.kktua.taskslist.helper.DbHelper
import com.kktua.taskslist.model.Task

class TaskDAO(context: Context) : ITaskDAO {

    private val dataBase = DbHelper(context)

    override fun insert(task: Task): Boolean {
        val contentValues = ContentValues()
        contentValues.put("nome", task.task)

        try {
            dataBase.writableDatabase.insert(DbHelper.TABLE_TASK, null, contentValues)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    override fun update(task: Task): Boolean {
        val contentValues = ContentValues()
        contentValues.put("nome", task.task)

        val args = arrayOf(task.id.toString())

        try {
            dataBase.writableDatabase.update(DbHelper.TABLE_TASK, contentValues, "id=?", args)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    override fun delete(task: Task): Boolean {
        val args = arrayOf(task.id.toString())

        try {
            dataBase.writableDatabase.delete(DbHelper.TABLE_TASK, "id=?", args)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    override fun list(): List<Task> {
        val taskList = ArrayList<Task>()
        val result =
            dataBase.readableDatabase.rawQuery("SELECT id, nome FROM ${DbHelper.TABLE_TASK}", null)

        val columnId = result.getColumnIndex("id")
        val columnName = result.getColumnIndex("nome")

        while (result.moveToNext()) {
            taskList.add(
                Task(
                    result.getLong(columnId),
                    result.getString(columnName)
                )
            )
        }

        result.close()

        return taskList
    }
}