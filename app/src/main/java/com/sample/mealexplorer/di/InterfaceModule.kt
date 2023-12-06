package com.sample.mealexplorer.di

import com.sample.mealexplorer.data.network.MealsNetworkDataSource
import com.sample.mealexplorer.data.network.MealsNetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface InterfaceModule {

    @Binds
    fun bindMealsNetworkDataSource(
        mealsNetworkDataSourceImpl: MealsNetworkDataSourceImpl
    ): MealsNetworkDataSource


}