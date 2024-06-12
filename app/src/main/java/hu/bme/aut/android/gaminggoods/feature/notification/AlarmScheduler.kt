package hu.bme.aut.android.gaminggoods.feature.notification

interface AlarmScheduler {
    fun setAlarm(immediate: Boolean = false)
    fun cancelAlarm()
}