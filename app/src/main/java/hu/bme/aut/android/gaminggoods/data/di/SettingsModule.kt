package hu.bme.aut.android.gaminggoods.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.gaminggoods.data.settings.Settings
import hu.bme.aut.android.gaminggoods.data.settings.SettingsImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext app: Context
    ): Settings = SettingsImpl(app)

}