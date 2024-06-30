package com.flutterwave.fwinventory.features.inventory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.flutterwave.fwinventory.data.model.InventoryItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditInventoryScreen(
    navController: NavHostController,
    itemId: Int,
    viewModel: InventoryViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    var inventoryItem by remember { mutableStateOf<InventoryItem?>(null) }
    var name by remember { mutableStateOf("") }
    var totalStock by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(inventoryItem) {
        scope.launch {
            val item = viewModel.getItem(itemId)
            if (item != null) {
                inventoryItem = item
                name = item.name
                totalStock = item.totalStock.toString()
                price = item.price.toString()
                description = item.description
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Edit Inventory Item") })
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Item Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = totalStock,
                    onValueChange = { totalStock = it },
                    label = { Text("Total Stock") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        inventoryItem?.let {
                            viewModel.updateItem(
                                it.copy(
                                    name = name,
                                    totalStock = totalStock.toIntOrNull() ?: 0,
                                    price = price.toDoubleOrNull() ?: 0.0,
                                    description = description
                                )
                            )
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Update Item")
                }
            }
        }
    )
}
