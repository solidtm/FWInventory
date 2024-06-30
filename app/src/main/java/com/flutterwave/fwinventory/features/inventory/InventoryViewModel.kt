package com.flutterwave.fwinventory.features.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flutterwave.fwinventory.data.db.InventoryDao
import com.flutterwave.fwinventory.data.model.InventoryItem
import com.flutterwave.fwinventory.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val inventoryDao: InventoryDao,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _inventoryItems = MutableStateFlow<List<InventoryItem>>(emptyList())
    val inventoryItems: StateFlow<List<InventoryItem>> get() = _inventoryItems

    init {
        val userId = authRepository.getCurrentUserId()
        println(userId)
        if (userId != null) {
            viewModelScope.launch {
                inventoryDao.getItems(userId)
                    .onEach { items ->
                        _inventoryItems.value = items
                    }
                    .launchIn(this)
            }
        }
    }

    fun addItem(item: InventoryItem) {
        viewModelScope.launch {
            inventoryDao.insertItem(item)
        }
    }

    fun updateItem(item: InventoryItem) {
        viewModelScope.launch {
            inventoryDao.updateItem(item)
        }
    }

    fun deleteItem(item: InventoryItem) {
        viewModelScope.launch {
            inventoryDao.deleteItem(item)
        }
    }

    suspend fun getItem(id: Int): InventoryItem? {
        val userId = authRepository.getCurrentUserId() ?: return null
        return inventoryDao.getItem(id, userId)
    }
}
