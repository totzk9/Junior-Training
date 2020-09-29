package com.lig.juntrain.alarm

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import com.lig.juntrain.R
import com.lig.juntrain.view.MainActivity
import java.util.*

class NotificationService: IntentService("NotificationService") {

    private lateinit var notification: Notification
    private val notificationId: Int = 1000

    companion object {

        const val CHANNEL_ID = "com.lig.juntrain.alarm.10001"
        const val CHANNEL_NAME = "task_notification"
    }

    @SuppressLint("NewApi")
    private fun createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = this.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationChannel.lightColor = Color.parseColor("#424242")
            notificationChannel.description = "Do your task"

            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

            notificationManager.createNotificationChannel(notificationChannel)

        }

    }


    override fun onHandleIntent(intent: Intent?) {
        createChannel()

        var timeStamp: Long = 0

        if (intent != null && intent.extras != null)
            timeStamp = intent.extras!!.getLong("timestamp")

        if (timeStamp > 0) {

            val notifyIntent = Intent(this, MainActivity::class.java)

            val title = "You have pending tasks."

            notifyIntent.putExtra("title", title)
            notifyIntent.putExtra("notification", true)

            notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeStamp

            val pendingIntent = PendingIntent.getActivity(
                this.applicationContext, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )

            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                notification = Notification.Builder(this, CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_task)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .build()
            } else {
                notification = Notification.Builder(this)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_task)
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setSound(uri)
                    .build()
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.notify(notificationId, notification)



        }
    }

}