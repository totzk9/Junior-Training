package com.lig.juntrain.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.lig.juntrain.model.Task
import com.lig.juntrain.R
import com.lig.juntrain.presenter.EditTaskPresenter
import com.lig.juntrain.presenter.NewTaskPresenter
import com.lig.juntrain.presenter.TaskContract
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_edit_task.*
import kotlinx.android.synthetic.main.activity_edit_task.editTask
import kotlinx.android.synthetic.main.activity_edit_task.imgTask
import kotlinx.android.synthetic.main.activity_edit_task.rdDone
import kotlinx.android.synthetic.main.activity_edit_task.rdPending


class EditTaskActivity : AppCompatActivity(), TaskContract.View {

    private val presenter = EditTaskPresenter()

    private var imgUri: String? = null

    private var task: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        task = intent.getParcelableExtra("task") as Task

        presenter.setView(this)
        initView(task!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.task_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cancel -> {
                finish()
            }
            R.id.save -> {
                if (TextUtils.isEmpty(editTask.text!!.trim())) {
                    editTask.error = "This field is required"
                    editTask.requestFocus()
                } else {
                    if (rdDone.isChecked)
                        presenter.insertTask(Task(task!!.taskId, editTask.text.toString(), imgUri, "Done"))
                    else
                        presenter.insertTask(Task(task!!.taskId, editTask.text.toString(), imgUri, "Pending"))
                    Snackbar.make(editTask, "Task updated.", Snackbar.LENGTH_SHORT)
                    finish()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                imgUri = result.uri.toString()
                Picasso.get().load(imgUri).into(imgTask)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun initView(task: Task) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        toolbar?.setNavigationOnClickListener {
            finish()
        }

        editTask.setText(task.task)
        when (task.status) {
            "Pending" -> {
                rdDone.isChecked = false
                rdPending.isChecked = true
            }
            "Done" -> {
                rdDone.isChecked = true
                rdPending.isChecked = false
            }
        }

        if (task.image != null) {
            Picasso.get().load(task.image).into(imgTask)
        } else {
            lblChangeImage.text = "Add image"
        }

        lblChangeImage.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this)
        }
    }
}
