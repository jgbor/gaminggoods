package hu.bme.aut.android.gaminggoods.feature.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.gaminggoods.feature.notification.AlarmScheduler
import hu.bme.aut.android.gaminggoods.feature.notification.NotificationAlarmScheduler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SchedulerModule {

    @Singleton
    @Provides
    fun provideScheduler(
        @ApplicationContext app: Context
    ): AlarmScheduler = NotificationAlarmScheduler(app)
}