package com.flutterwave.fwinventory.features.inventory.fakes

import com.flutterwave.fwinventory.data.model.InventoryItem
import kotlinx.coroutines.flow.Flow

interface IInventoryRepository {
    fun getItems(userId: Int): Flow<List<InventoryItem>>
    suspend fun insertItem(item: InventoryItem)
    suspend fun updateItem(item: InventoryItem)
    suspend fun deleteItem(item: InventoryItem)
    suspend fun getItem(id: Int, userId: Int): InventoryItem?
    suspend fun isNameDuplicate(name: String): Boolean
}