package hu.bme.aut.android.gaminggoods

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.gaminggoods.data.settings.Settings
import hu.bme.aut.android.gaminggoods.feature.notification.AlarmScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootCompletedReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationAlarmScheduler: AlarmScheduler
    @Inject
    lateinit var settings: Settings

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == Intent.ACTION_BOOT_COMPLETED){
            CoroutineScope(Dispatchers.Main).launch {
                if(settings.isNotificationOn.first() == true)
                    notificationAlarmScheduler.setAlarm(immediate = true)
            }
        }
    }
}