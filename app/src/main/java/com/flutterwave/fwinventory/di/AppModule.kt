package com.flutterwave.fwinventory.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.flutterwave.fwinventory.data.source.InventoryDao
import com.flutterwave.fwinventory.data.source.InventoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger module that provides instances for database, DAO, and SharedPreferences.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides a singleton instance of the [InventoryDatabase].
     *
     * @param app The application instance.
     * @return A configured instance of [InventoryDatabase].
     */
    @Provides
    @Singleton
    fun provideDatabase(app: Application): InventoryDatabase {
        return Room.databaseBuilder(app, InventoryDatabase::class.java, "inventory_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    /**
     * Provides an instance of the [InventoryDao].
     *
     * @param db The [InventoryDatabase] instance.
     * @return The DAO for accessing inventory data.
     */
    @Provides
    fun provideInventoryDao(db: InventoryDatabase): InventoryDao {
        return db.inventoryDao()
    }

    /**
     * Provides a singleton instance of the [SharedPreferences].
     *
     * @param app The application instance.
     * @return A configured instance of [SharedPreferences].
     */
    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("inventory_prefs", Context.MODE_PRIVATE)
    }
}
