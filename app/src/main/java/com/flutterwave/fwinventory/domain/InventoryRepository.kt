package com.flutterwave.fwinventory.domain

import com.flutterwave.fwinventory.data.db.InventoryDao
import com.flutterwave.fwinventory.data.model.InventoryItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InventoryRepository @Inject constructor(private val inventoryDao: InventoryDao) {

    fun getItems(userId: Int): Flow<List<InventoryItem>> {
        return inventoryDao.getItems(userId)
    }

    suspend fun insertItem(item: InventoryItem) {
        inventoryDao.insertItem(item)
    }

    suspend fun updateItem(item: InventoryItem) {
        inventoryDao.updateItem(item)
    }

    suspend fun deleteItem(item: InventoryItem) {
        inventoryDao.deleteItem(item)
    }

    suspend fun getItem(id: Int, userId: Int): InventoryItem? {
        return inventoryDao.getItem(id, userId)
    }

    suspend fun isNameDuplicate(name: String): Boolean {
        return inventoryDao.countItemsWithName(name) > 0
    }
}
