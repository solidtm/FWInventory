package com.flutterwave.fwinventory.features.inventory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.flutterwave.fwinventory.data.model.InventoryItem

@Composable
fun InventoryListScreen(navController: NavController, inventoryViewModel: InventoryViewModel = hiltViewModel()) {
    val items by inventoryViewModel.inventoryItems.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(
            onClick = { navController.navigate("add_inventory") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Item")
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(items) { item ->
                InventoryItemRow(item) {
                    navController.navigate("edit_inventory/${item.id}")
                }
            }
        }
    }
}

@Composable
fun InventoryItemRow(item: InventoryItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.name, style = MaterialTheme.typography.titleLarge)
            Text(text = "Price: ${item.price}")
            Text(text = "Stock: ${item.totalStock}")
        }
    }
}
