package com.lig.juntrain.view

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lig.juntrain.R
import com.lig.juntrain.model.Task
import com.lig.juntrain.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val presenter = MainPresenter()
    private val taskAdapter = TaskAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabNew.setOnClickListener {
            val intent = Intent(this, NewTaskActivity::class.java)

            startActivity(intent)
        }


        initRecyclerView()
        initData()
    }

    private fun initData() {
        presenter.getAllTasks().observe(this, Observer {
            taskAdapter.updateTasks(it)
        })
    }

    private fun initRecyclerView() {
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = taskAdapter
        taskAdapter.onItemClick = { task: Task, imgTask1: ImageView ->

            val intent = Intent(this@MainActivity, EditTaskActivity::class.java)
            intent.putExtra("task", task)

            if (task.image != null) {
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity,
                        imgTask1,
                        ViewCompat.getTransitionName(imgTask1)!!
                    )
                startActivity(intent, optionsCompat.toBundle())
            } else {
                startActivity(intent)
            }
        }

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                MaterialAlertDialogBuilder(this@MainActivity)
                    .setCancelable(true)
                    .setTitle("Are you sure you want to delete task?")
                    .setPositiveButton("Yes") {
                            dialog: DialogInterface, _ ->
                        presenter.deleteTask(taskAdapter.getTaskByPosition(viewHolder.adapterPosition))
                        taskAdapter.removeItem(viewHolder.adapterPosition)
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") {
                            _, _ ->
                        taskAdapter.notifyDataSetChanged()
                    }
                    .show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)


    }

}
