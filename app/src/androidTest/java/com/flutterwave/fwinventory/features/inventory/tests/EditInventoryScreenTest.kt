package com.flutterwave.fwinventory.features.inventory.tests

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flutterwave.fwinventory.data.model.InventoryItem
import com.flutterwave.fwinventory.features.inventory.EditInventoryScreen
import com.flutterwave.fwinventory.features.inventory.InventoryViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import io.mockk.coEvery
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class EditInventoryScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: InventoryViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = mockk<InventoryViewModel>(relaxed = true)

        // Define your test items
        val items = listOf(
            InventoryItem(id = 1, userId = 1, name = "Item 1", totalStock = 10, price = 15.99, description = "Sample item 1"),
            InventoryItem(id = 2, userId = 1, name = "Item 2", totalStock = 5, price = 25.99, description = "Sample item 2")
        )

        // Mock viewModel's inventoryItems StateFlow
        val flow = MutableStateFlow(items)
        coEvery { viewModel.inventoryItems } returns flow as StateFlow<List<InventoryItem>>

        // Mock viewModel's getItem suspend function
        coEvery { viewModel.getItem(1) } returns items.first()
    }

    @Test
    fun testDeleteConfirmationPopUpIsShown() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            EditInventoryScreen(navController = navController, itemId = 1, viewModel = viewModel)
        }

        // Click the delete button
        composeTestRule.onNodeWithText("Delete Item").performClick()

        // Check if the confirmation dialog is displayed
        composeTestRule.onNodeWithText("Are you sure you want to delete this item?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Yes").assertIsDisplayed()
        composeTestRule.onNodeWithText("No").assertIsDisplayed()
    }
}

