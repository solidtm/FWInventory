package com.flutterwave.fwinventory.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.flutterwave.fwinventory.data.db.InventoryDao
import com.flutterwave.fwinventory.data.db.InventoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Database instance
    @Provides
    @Singleton
    fun provideDatabase(app: Application): InventoryDatabase {
        return Room.databaseBuilder(app, InventoryDatabase::class.java, "inventory_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    //Dao instance
    @Provides
    fun provideInventoryDao(db: InventoryDatabase): InventoryDao {
        return db.inventoryDao()
    }

    //SharedPreferences instance
    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("inventory_prefs", Context.MODE_PRIVATE)
    }
}
