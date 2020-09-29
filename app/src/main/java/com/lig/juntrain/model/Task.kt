package com.lig.juntrain.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "task")
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true) @NonNull val taskId: Int,
    val task: String,
    val image: String?,
    val status: String
) : Parcelable