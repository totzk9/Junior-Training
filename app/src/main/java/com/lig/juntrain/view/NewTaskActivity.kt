package com.lig.juntrain.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.lig.juntrain.R
import com.lig.juntrain.alarm.NotificationUtils
import com.lig.juntrain.model.Task
import com.lig.juntrain.presenter.NewTaskContract
import com.lig.juntrain.presenter.NewTaskPresenter
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_new_task.*
import kotlinx.android.synthetic.main.activity_new_task.imgTask
import java.util.*

private const val fiveMins: Long = 300000
private const val tenMins: Long = 600000

class NewTaskActivity : AppCompatActivity(), NewTaskContract.View {

    private val presenter = NewTaskPresenter()

    private var imgUri: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Create New Task"
        toolbar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        toolbar?.setNavigationOnClickListener {
            finish()
        }


        spinner.adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.notify)
        )

        materialCardView.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this)
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when {
                rdDone.isChecked -> doneButtonPressed()
                rdPending.isChecked -> pendingButtonPressed()
            }
        }

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
                val status = when {
                    rdDone.isChecked -> "Done"
                    rdPending.isChecked -> "Pending"
                    else -> "Pending"
                }

                presenter.validateText(editTask.text!!.trim())
                if (TextUtils.isEmpty(editTask.text!!.trim())) {
                    showError()
                } else {
                    presenter.insertTask(Task(0, editTask.text.toString(), imgUri, status))

                    when (spinner.selectedItemPosition) {
                        1 -> NotificationUtils().setNotification(
                            Calendar.getInstance().timeInMillis + fiveMins, this@NewTaskActivity)
                        2 -> NotificationUtils().setNotification(
                            Calendar.getInstance().timeInMillis + tenMins, this@NewTaskActivity)
                    }

                    Snackbar.make(editTask, "Task saved.", Snackbar.LENGTH_SHORT)
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
                Picasso.get().load(imgUri).fit().into(imgTask)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun doneButtonPressed() {
        spinner.visibility = View.GONE
        spinner.setSelection(0)
        lblNotify.visibility = View.GONE
    }

    override fun pendingButtonPressed() {
        spinner.visibility = View.VISIBLE
        lblNotify.visibility = View.VISIBLE
    }

    override fun showError() {
        editTask.error = "This field is required"
        editTask.requestFocus()
    }
}
