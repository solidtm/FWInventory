package com.flutterwave.fwinventory.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.flutterwave.fwinventory.data.model.InventoryItem
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface for accessing inventory-related operations in the database.
 * This interface provides methods to perform CRUD operations on the `Inventories` table.
 */
@Dao
interface InventoryDao {

    /**
     * Inserts an inventory item into the database. If the item already exists, it replaces it.
     *
     * @param item The inventory item to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: InventoryItem)

    /**
     * Counts the number of items with the specified name in the database.
     *
     * @param name The name of the inventory item to be counted.
     * @return The count of items with the specified name.
     */
    @Query("SELECT COUNT(*) FROM Inventories WHERE name = :name")
    suspend fun countItemsWithName(name: String): Int

    /**
     * Updates an existing inventory item in the database.
     *
     * @param item The inventory item to be updated.
     */
    @Update
    suspend fun updateItem(item: InventoryItem)

    /**
     * Deletes an inventory item from the database.
     *
     * @param item The inventory item to be deleted.
     */
    @Delete
    suspend fun deleteItem(item: InventoryItem)

    /**
     * Retrieves a list of inventory items for a specific user from the database.
     *
     * @param userId The ID of the user whose inventory items are to be retrieved.
     * @return A Flow object that emits a list of inventory items for the specified user.
     */
    @Query("SELECT * FROM Inventories WHERE userId = :userId")
    fun getItems(userId: Int): Flow<List<InventoryItem>>

    /**
     * Retrieves an inventory item by its ID and user ID from the database.
     *
     * @param id The ID of the inventory item to be retrieved.
     * @param userId The ID of the user who owns the inventory item.
     * @return The inventory item with the specified ID and user ID, or null if not found.
     */
    @Query("SELECT * FROM Inventories WHERE id = :id AND userId = :userId")
    suspend fun getItem(id: Int, userId: Int): InventoryItem?
}
