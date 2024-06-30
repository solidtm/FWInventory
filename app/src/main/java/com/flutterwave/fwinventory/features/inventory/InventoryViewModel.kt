package com.flutterwave.fwinventory.features.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flutterwave.fwinventory.data.db.InventoryDao
import com.flutterwave.fwinventory.data.model.InventoryItem
import com.flutterwave.fwinventory.domain.AuthRepository
import com.flutterwave.fwinventory.domain.InventoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val inventoryRepository: InventoryRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _inventoryItems = MutableStateFlow<List<InventoryItem>>(emptyList())
    val inventoryItems: StateFlow<List<InventoryItem>> get() = _inventoryItems

    init {
        val userId = authRepository.getCurrentUserId()
        println(userId)
        viewModelScope.launch {
            inventoryRepository.getItems(userId)
                .onEach { items ->
                    _inventoryItems.value = items
                }
                .launchIn(this)
        }
    }

    fun addItem(item: InventoryItem) {
        viewModelScope.launch {
            inventoryRepository.insertItem(item)
        }
    }

    fun updateItem(item: InventoryItem) {
        viewModelScope.launch {
            inventoryRepository.updateItem(item)
        }
    }

    fun deleteItem(item: InventoryItem) {
        viewModelScope.launch {
            inventoryRepository.deleteItem(item)
        }
    }


    suspend fun getItem(id: Int): InventoryItem? {
        val userId = authRepository.getCurrentUserId() ?: return null
        return inventoryRepository.getItem(id, userId)
    }

    suspend fun isNameDuplicate(name: String): Boolean {
        return inventoryRepository.isNameDuplicate(name)
    }
}