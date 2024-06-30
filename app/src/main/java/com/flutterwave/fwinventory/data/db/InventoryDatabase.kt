package com.flutterwave.fwinventory.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flutterwave.fwinventory.data.model.InventoryItem

@Database(entities = [InventoryItem::class], version = 1, exportSchema = true)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun inventoryDao(): InventoryDao
}
