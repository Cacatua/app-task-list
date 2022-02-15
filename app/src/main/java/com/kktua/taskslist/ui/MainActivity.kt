package com.kktua.taskslist.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kktua.taskslist.R
import com.kktua.taskslist.adapter.TaskAdapter
import com.kktua.taskslist.data.TaskDAO
import com.kktua.taskslist.databinding.ActivityMainBinding
import com.kktua.taskslist.helper.RecyclerViewInterface
import com.kktua.taskslist.model.Task

class MainActivity : AppCompatActivity(), RecyclerViewInterface {

    private lateinit var activityMainBinding: ActivityMainBinding

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskList: ArrayList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setSupportActionBar(activityMainBinding.toolbar)

        setupFloatingActionButton()

        loadTaskList()
    }

    private fun setupFloatingActionButton() {
        activityMainBinding.fab.setOnClickListener {
            startActivity(Intent(applicationContext, AddTaskActivity::class.java))
        }
    }

    private fun loadTaskList() {

        val taskDAO = TaskDAO(applicationContext)
        taskList = taskDAO.list() as ArrayList<Task>

        taskAdapter = TaskAdapter(taskList, this)

        layoutManager = LinearLayoutManager(applicationContext)

        activityMainBinding.rvTaskList.layoutManager = layoutManager
        activityMainBinding.rvTaskList.setHasFixedSize(true)
        activityMainBinding.rvTaskList.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                LinearLayout.VERTICAL
            )
        )
        activityMainBinding.rvTaskList.adapter = taskAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(position: Int) {
        val task = taskList[position]
        val intent = Intent(this, AddTaskActivity::class.java)
        intent.putExtra("task", task)
        startActivity(intent)
    }

    override fun onLongItemClick(position: Int) {
        val dialog = AlertDialog.Builder(this, androidx.appcompat.R.style.Theme_AppCompat_Light)
        dialog.setTitle("Confirmar exclusão?")
        dialog.setMessage("Deseja excluir a tarefa: " + taskList[position].task + "?")
        dialog.setPositiveButton("Sim", DialogInterface.OnClickListener { dialogInterface, i ->
            val taskDAO = TaskDAO(applicationContext)
            if (taskDAO.delete(taskList[position])) {
                loadTaskList()
                Toast.makeText(applicationContext, "Sucesso ao excluir a tarefa", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(applicationContext, "Erro ao excluir a tarefa", Toast.LENGTH_LONG)
                    .show()
            }
        })
        dialog.setNegativeButton("Não", null)

        dialog.create()
        dialog.show()
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
}