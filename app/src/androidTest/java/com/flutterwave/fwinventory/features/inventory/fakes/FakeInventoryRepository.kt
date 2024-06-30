package com.flutterwave.fwinventory.features.inventory.fakes

import com.flutterwave.fwinventory.data.model.InventoryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeInventoryRepository : IInventoryRepository {
    private val items = mutableListOf<InventoryItem>()

    override fun getItems(userId: Int): Flow<List<InventoryItem>> {
        return flow { emit(items.filter { it.userId == userId }) }
    }

    override suspend fun insertItem(item: InventoryItem) {
        items.add(item)
    }

    override suspend fun updateItem(item: InventoryItem) {
        items.removeIf { it.id == item.id }
        items.add(item)
    }

    override suspend fun deleteItem(item: InventoryItem) {
        items.removeIf { it.id == item.id }
    }

    override suspend fun getItem(id: Int, userId: Int): InventoryItem? {
        return items.find { it.id == id && it.userId == userId }
    }

    override suspend fun isNameDuplicate(name: String): Boolean {
        return items.any { it.name == name }
    }
}
