package com.lig.juntrain.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.lig.juntrain.model.Task
import com.lig.juntrain.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_task.view.*

class TaskAdapter(private val taskList: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.Item>() {
    var onItemClick: ((Task, ImageView) -> Unit)? = null

    fun updateTasks(tasks: List<Task>) {
        this.taskList.clear()
        this.taskList.addAll(tasks)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        taskList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, taskList.size)
    }

    fun getTaskByPosition(position: Int) : Task {
        return taskList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Item {
        return Item(parent)
    }

    override fun onBindViewHolder(holder: Item, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class Item(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)) {


        fun bind(task: Task) = with(itemView) {
            txtTask.text = task.task
            if (task.image != null) {
                imgTask.visibility = View.VISIBLE
                Picasso.get().load(task.image).into(imgTask)
            }

            txtStatus.text = task.status

            this.setOnClickListener {
                onItemClick?.invoke(task, imgTask)
            }
        }

    }
}