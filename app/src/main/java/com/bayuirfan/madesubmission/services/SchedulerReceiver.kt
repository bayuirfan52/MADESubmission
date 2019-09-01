package com.bayuirfan.madesubmission.services

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.Toast
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.features.MainActivity
import com.bayuirfan.madesubmission.features.details.movie.DetailMovieActivity
import com.bayuirfan.madesubmission.model.data.MovieModel
import com.bayuirfan.madesubmission.model.remote.MovieCatalogueService
import com.bayuirfan.madesubmission.utils.Constant.EXTRA_DETAIL
import com.bayuirfan.madesubmission.utils.Constant.EXTRA_TYPE
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import java.text.SimpleDateFormat
import java.util.*

class SchedulerReceiver: BroadcastReceiver() {
    private val channelName = "Catalogue Reminder"

    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getIntExtra(EXTRA_TYPE, 0)

        context?.let {
            if (id == ID_DAILY){
                showDailyNotification(it, it.getString(R.string.daily_reminder_message))
            } else if (id == ID_RELEASE) {
                loadReleaseUpdates(context)
            }
        }
    }

    fun setReminder(context: Context, TYPE: Int){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val intent = Intent(context, SchedulerReceiver::class.java)
        val calendar = Calendar.getInstance()
        var pendingIntent: PendingIntent? = null

        when(TYPE){
            ID_DAILY -> {
                intent.putExtra(EXTRA_TYPE, ID_DAILY)

                calendar.set(Calendar.HOUR_OF_DAY, 7)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)

                pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, 0)
                Toast.makeText(context, context.getString(R.string.daily_reminder_active), Toast.LENGTH_SHORT).show()
            }
            ID_RELEASE -> {
                intent.putExtra(EXTRA_TYPE, ID_RELEASE)

                calendar.set(Calendar.HOUR_OF_DAY, 17)
                calendar.set(Calendar.MINUTE, 8)
                calendar.set(Calendar.SECOND, 0)

                pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0)
                Toast.makeText(context, context.getString(R.string.release_reminder_active), Toast.LENGTH_SHORT).show()
            }
        }

        alarmManager?.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    fun cancelReminder(context: Context, type: Int){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val intent = Intent(context, SchedulerReceiver::class.java)
        val requestCode: Int

        if (type == ID_DAILY) {
            requestCode = ID_DAILY
            Toast.makeText(context, context.getString(R.string.daily_reminder_not_active), Toast.LENGTH_SHORT).show()
        }
        else {
            requestCode = ID_RELEASE
            Toast.makeText(context, context.getString(R.string.release_reminder_not_active), Toast.LENGTH_SHORT).show()
        }

        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()

        alarmManager?.cancel(pendingIntent)
    }

    private fun showDailyNotification(context: Context, message: String){
        val channelId = "Catalouge_Reminder_1"

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(context.getString(R.string.daily_reminder))
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId,channelName, NotificationManager.IMPORTANCE_DEFAULT)

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(channelId)
            notificationManager?.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManager?.notify(NOTIFICATION_ID, notification)
    }

    private fun showReleaseNotification(context: Context, data: MovieModel){
        val channelId = "Catalogue_Reminder_2"
        val intent = Intent(context, DetailMovieActivity::class.java)
        intent.putExtra(EXTRA_DETAIL, data)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_movies)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_movies)
                .setContentTitle(context.getString(R.string.release_today_reminder))
                .setContentText(data.title)
                .setLargeIcon(largeIcon)
                .setContentIntent(pendingIntent)
                .setGroup(GROUP_KEY_RELEASE)
                .setSound(alarmSound)
                .setAutoCancel(true)



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)

            builder.setChannelId(channelId)

            notificationManager?.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManager?.notify(NOTIFICATION_ID, notification)
    }

    private fun showReleaseGroupNotification(context: Context, list: ArrayList<MovieModel>){
        val channelId = "Catalogue_Reminder_2"
        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_movies)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val inboxStyle = NotificationCompat.InboxStyle()
                .setBigContentTitle(context.getString(R.string.release_reminder_message))
        for (i in list.indices){
            inboxStyle.addLine(list[i].title)
        }

        val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_movies)
                .setContentTitle(context.getString(R.string.release_today_reminder))
                .setContentText(context.getString(R.string.release_reminder_message))
                .setLargeIcon(largeIcon)
                .setGroup(GROUP_KEY_RELEASE)
                .setGroupSummary(true)
                .setStyle(inboxStyle)
                .setSound(alarmSound)
                .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)

            builder.setChannelId(channelId)

            notificationManager?.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManager?.notify(NOTIFICATION_ID, notification)
    }

    private fun loadReleaseUpdates(context: Context){
        val compositeSubscription = CompositeSubscription()
        val service = MovieCatalogueService.getClient()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date()
        val currentDate = dateFormat.format(date)
        val subscription = service.getMovieReleaseToday(currentDate, currentDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({data ->
                    if (data.status_code == null){
                        if (data.results.size < 2)
                            showReleaseNotification(context, data.results[0])
                        else {
                            showReleaseGroupNotification(context, data.results as ArrayList<MovieModel>)
                        }
                    }
                },{error ->
                    Log.e("SCHEDULER", error.message)
                })

        compositeSubscription.add(subscription)
    }

    companion object {
        private const val NOTIFICATION_ID = 0x01
        private const val GROUP_KEY_RELEASE = "group_key_release"

        const val ID_DAILY = 101
        const val ID_RELEASE = 102
    }
}