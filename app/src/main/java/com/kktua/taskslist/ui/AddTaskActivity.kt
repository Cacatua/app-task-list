package com.kktua.taskslist.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.kktua.taskslist.R
import com.kktua.taskslist.data.TaskDAO
import com.kktua.taskslist.model.Task

class AddTaskActivity : AppCompatActivity() {

    private var task: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        task = intent.getSerializableExtra("task") as? Task

        if (task != null) {
            findViewById<TextInputEditText>(R.id.et_task).setText(task!!.task)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_task, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_task -> {
                if (task != null)
                    saveChanges()
                else
                    saveNewTask()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveChanges() {
        val taskDescription = findViewById<TextInputEditText>(R.id.et_task).text.toString()

        if (taskDescription.isNotEmpty()) {
            val task = Task(this.task?.id ?: 0, taskDescription)
            val taskDAO = TaskDAO(applicationContext)
            if (taskDAO.update(task)) {
//                Toast.makeText(applicationContext, "Sucesso ao alterar a tarefa", Toast.LENGTH_LONG)
//                    .show()
                finish()
            } else {
//                Toast.makeText(applicationContext, "Falha ao alterar a tarefa", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun saveNewTask() {
        val taskDescription = findViewById<TextInputEditText>(R.id.et_task).text.toString()

        if (taskDescription.isNotEmpty()) {
            val task = Task(0, taskDescription)
            val taskDAO = TaskDAO(applicationContext)
            if (taskDAO.insert(task)) {
//                Toast.makeText(applicationContext, "Sucesso ao salvar a tarefa", Toast.LENGTH_LONG)
//                    .show()
                finish()
            } else {
//                Toast.makeText(applicationContext, "Falha ao salvar a tarefa", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }
}