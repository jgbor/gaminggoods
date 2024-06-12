package hu.bme.aut.android.gaminggoods.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.gaminggoods.network.deals.DealRepository
import hu.bme.aut.android.gaminggoods.network.deals.GamingAPI
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDealRepository(): DealRepository = GamingAPI()

}