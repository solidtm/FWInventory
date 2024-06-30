package com.flutterwave.fwinventory.features.inventory.fakes

import androidx.lifecycle.ViewModel
import com.flutterwave.fwinventory.data.model.InventoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class FakeInventoryViewModel(
    private val authRepository: IAuthRepository = FakeAuthRepository(),
    private val inventoryRepository: IInventoryRepository = FakeInventoryRepository()
) : ViewModel() {

    private val _inventoryItems = MutableStateFlow<List<InventoryItem>>(emptyList())
    val inventoryItems: StateFlow<List<InventoryItem>> = _inventoryItems

    init {
        // Load initial data for testing purposes
        runBlocking {
            val items = inventoryRepository.getItems(authRepository.getCurrentUserId()).first()
            _inventoryItems.value = items
        }
    }

    suspend fun insertItem(item: InventoryItem) {
        inventoryRepository.insertItem(item)
        _inventoryItems.value = inventoryRepository.getItems(authRepository.getCurrentUserId()).first()
    }

    suspend fun updateItem(item: InventoryItem) {
        inventoryRepository.updateItem(item)
        _inventoryItems.value = inventoryRepository.getItems(authRepository.getCurrentUserId()).first()
    }

    suspend fun deleteItem(item: InventoryItem) {
        inventoryRepository.deleteItem(item)
        _inventoryItems.value = inventoryRepository.getItems(authRepository.getCurrentUserId()).first()
    }

    suspend fun getItem(id: Int): InventoryItem? {
        return inventoryRepository.getItem(id, authRepository.getCurrentUserId())
    }

    suspend fun isNameDuplicate(name: String): Boolean {
        return inventoryRepository.isNameDuplicate(name)
    }
}
