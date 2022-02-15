package com.kktua.taskslist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kktua.taskslist.R
import com.kktua.taskslist.helper.RecyclerViewInterface
import com.kktua.taskslist.model.Task

class TaskAdapter(
    private val taskList: List<Task>,
    private val recyclerViewInterface: RecyclerViewInterface
) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemList =
            LayoutInflater.from(parent.context).inflate(R.layout.task_list_adapter, parent, false)

        return ViewHolder(itemList, recyclerViewInterface)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.task.text = taskList[position].task
    }

    override fun getItemCount() = this.taskList.count()

    inner class ViewHolder(
        itemView: View,
        private val recyclerViewInterface: RecyclerViewInterface
    ) : RecyclerView.ViewHolder(itemView),
        RecyclerViewInterface {
        val task: TextView = itemView.findViewById(R.id.tv_task_description)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    recyclerViewInterface.onItemClick(adapterPosition)
                }
            }

            itemView.setOnLongClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    recyclerViewInterface.onLongItemClick(adapterPosition)
                }
                return@setOnLongClickListener true
            }
        }

        override fun onItemClick(position: Int) {
            recyclerViewInterface.onItemClick(position)
        }

        override fun onLongItemClick(position: Int) {
            recyclerViewInterface.onLongItemClick(position)
        }

    }
}
