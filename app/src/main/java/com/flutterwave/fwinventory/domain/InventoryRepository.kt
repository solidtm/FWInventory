package com.flutterwave.fwinventory.domain

import com.flutterwave.fwinventory.data.model.InventoryItem
import com.flutterwave.fwinventory.data.source.InventoryDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository class for handling inventory related operations.
 *
 * @property inventoryDao The Data Access Object for inventory items.
 */
class InventoryRepository @Inject constructor(private val inventoryDao: InventoryDao) {

    /**
     * Retrieves a flow of inventory items for a specific user.
     *
     * @param userId The ID of the user whose inventory items are to be retrieved.
     * @return A flow of a list of inventory items.
     */
    fun getItems(userId: Int): Flow<List<InventoryItem>> {
        return inventoryDao.getItems(userId)
    }

    /**
     * Inserts a new inventory item into the database.
     *
     * @param item The inventory item to be inserted.
     */
    suspend fun insertItem(item: InventoryItem) {
        inventoryDao.insertItem(item)
    }

    /**
     * Updates an existing inventory item in the database.
     *
     * @param item The inventory item to be updated.
     */
    suspend fun updateItem(item: InventoryItem) {
        inventoryDao.updateItem(item)
    }

    /**
     * Deletes an inventory item from the database.
     *
     * @param item The inventory item to be deleted.
     */
    suspend fun deleteItem(item: InventoryItem) {
        inventoryDao.deleteItem(item)
    }

    /**
     * Retrieves a specific inventory item for a user.
     *
     * @param id The ID of the inventory item to be retrieved.
     * @param userId The ID of the user whose inventory item is to be retrieved.
     * @return The inventory item if found, or null otherwise.
     */
    suspend fun getItem(id: Int, userId: Int): InventoryItem? {
        return inventoryDao.getItem(id, userId)
    }

    /**
     * Checks if an inventory item name is a duplicate.
     *
     * @param name The name of the inventory item to check.
     * @return `true` if the name is a duplicate, `false` otherwise.
     */
    suspend fun isNameDuplicate(name: String): Boolean {
        return inventoryDao.countItemsWithName(name) > 0
    }
}

