package com.flutterwave.fwinventory.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flutterwave.fwinventory.data.model.InventoryItem

/**
 * The Room database class for the inventory app.
 * This class represents the SQLite database for the application and serves as the main access point
 * to the underlying persisted data. It includes the DAO to interact with the `Inventories` table.
 *
 * @property entities An array of entity classes associated with this database.
 * @property version The version of the database schema.
 * @property exportSchema Indicates whether to export the database schema for migration purposes.
 */
@Database(entities = [InventoryItem::class], version = 1, exportSchema = true)
abstract class InventoryDatabase : RoomDatabase() {

    /**
     * Abstract method to get the InventoryDao.
     *
     * @return The InventoryDao instance for accessing inventory-related database operations.
     */
    abstract fun inventoryDao(): InventoryDao
}
