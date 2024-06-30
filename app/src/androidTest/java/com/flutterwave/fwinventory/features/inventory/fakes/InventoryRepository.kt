package com.flutterwave.fwinventory.features.inventory.fakes

import com.flutterwave.fwinventory.data.source.InventoryDao
import com.flutterwave.fwinventory.data.model.InventoryItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InventoryRepository @Inject constructor(private val inventoryDao: InventoryDao) :
    IInventoryRepository {

    override fun getItems(userId: Int): Flow<List<InventoryItem>> {
        return inventoryDao.getItems(userId)
    }

    override suspend fun insertItem(item: InventoryItem) {
        inventoryDao.insertItem(item)
    }

    override suspend fun updateItem(item: InventoryItem) {
        inventoryDao.updateItem(item)
    }

    override suspend fun deleteItem(item: InventoryItem) {
        inventoryDao.deleteItem(item)
    }

    override suspend fun getItem(id: Int, userId: Int): InventoryItem? {
        return inventoryDao.getItem(id, userId)
    }

    override suspend fun isNameDuplicate(name: String): Boolean {
        return inventoryDao.countItemsWithName(name) > 0
    }
}