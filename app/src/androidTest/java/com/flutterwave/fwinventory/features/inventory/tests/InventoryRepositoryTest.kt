package com.flutterwave.fwinventory.features.inventory.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flutterwave.fwinventory.data.model.InventoryItem
import com.flutterwave.fwinventory.features.inventory.fakes.FakeAuthRepository
import com.flutterwave.fwinventory.features.inventory.fakes.FakeInventoryRepository
import com.flutterwave.fwinventory.features.inventory.fakes.FakeInventoryViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat

@RunWith(AndroidJUnit4::class)
class InventoryRepositoryTest {

    private lateinit var authRepository: FakeAuthRepository
    private lateinit var inventoryRepository: FakeInventoryRepository
    private lateinit var viewModel: FakeInventoryViewModel

    @Before
    fun setup() {
        authRepository = FakeAuthRepository()
        inventoryRepository = FakeInventoryRepository()
        viewModel = FakeInventoryViewModel(authRepository, inventoryRepository)
    }

    @Test
    fun testInsertItem() = runBlocking {
        val item = InventoryItem(userId = authRepository.getCurrentUserId(), name = "item1", totalStock = 10, price = 100.0, description = "description1")
        viewModel.insertItem(item)

        val items = viewModel.inventoryItems.first()
        assertThat(items).contains(item)
    }

    @Test
    fun testGetItem() = runBlocking {
        val item = InventoryItem(userId = authRepository.getCurrentUserId(), name = "item1", totalStock = 10, price = 100.0, description = "description1")
        viewModel.insertItem(item)

        val retrievedItem = viewModel.getItem(item.id)
        assertThat(retrievedItem).isEqualTo(item)
    }

    @Test
    fun testUpdateItem() = runBlocking {
        val item = InventoryItem(userId = authRepository.getCurrentUserId(), name = "item1", totalStock = 10, price = 100.0, description = "description1")
        viewModel.insertItem(item)

        val updatedItem = item.copy(name = "updatedItem", totalStock = 20, price = 200.0, description = "updatedDescription")
        viewModel.updateItem(updatedItem)

        val items = viewModel.inventoryItems.first()
        assertThat(items).contains(updatedItem)
        assertThat(items).doesNotContain(item)
    }

    @Test
    fun testDeleteItem() = runBlocking {
        val item = InventoryItem(userId = authRepository.getCurrentUserId(), name = "item1", totalStock = 10, price = 100.0, description = "description1")
        viewModel.insertItem(item)

        viewModel.deleteItem(item)

        val items = viewModel.inventoryItems.first()
        assertThat(items).doesNotContain(item)
    }
}
