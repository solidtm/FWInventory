package com.flutterwave.fwinventory.features.inventory.di

import com.flutterwave.fwinventory.features.inventory.fakes.FakeAuthRepository
import com.flutterwave.fwinventory.features.inventory.fakes.FakeInventoryRepository
import com.flutterwave.fwinventory.features.inventory.fakes.IAuthRepository
import com.flutterwave.fwinventory.features.inventory.fakes.IInventoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestModule {
    @Provides
    @Singleton
    fun provideAuthRepository(): IAuthRepository = FakeAuthRepository()

    @Provides
    @Singleton
    fun provideInventoryRepository(): IInventoryRepository = FakeInventoryRepository()
}
