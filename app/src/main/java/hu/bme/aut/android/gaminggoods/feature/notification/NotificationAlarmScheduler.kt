package hu.bme.aut.android.gaminggoods.feature.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import hu.bme.aut.android.gaminggoods.NotificationReceiver
import hu.bme.aut.android.gaminggoods.R
import java.time.LocalDateTime
import java.time.ZoneId

class NotificationAlarmScheduler(
    private val context: Context
): AlarmScheduler {
    private val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

    override fun setAlarm(immediate: Boolean) {
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("message", context.getString(R.string.notification_message))
            putExtra("title", context.getString(R.string.have_a_look_at_these))
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            R.string.notification_message.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setInexactRepeating(
            AlarmManager.RTC,
            if(immediate)
                LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000
            else
                LocalDateTime.now().plusDays(7).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            AlarmManager.INTERVAL_DAY * 7,
            pendingIntent
        )
    }

    override fun cancelAlarm() {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            R.string.notification_message.hashCode(),
            Intent(context, NotificationReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}