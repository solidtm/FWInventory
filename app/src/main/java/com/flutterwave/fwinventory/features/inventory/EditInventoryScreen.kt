package com.flutterwave.fwinventory.features.inventory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
    var showDialog by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf(false) }
    var totalStockError by remember { mutableStateOf(false) }
    var priceError by remember { mutableStateOf(false) }
    var descriptionError by remember { mutableStateOf(false) }
    var uniqueNameError by remember { mutableStateOf(false) }

    LaunchedEffect(itemId) {
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
            TopAppBar(
                title = { Text("Edit Inventory Item") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    Button(
                        onClick = { showDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Delete Item")
                    }
                }
            )
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
                    onValueChange = {
                        name = it
                        nameError = it.isEmpty()
                        uniqueNameError = viewModel.inventoryItems.value.any { item -> item.name == it && item.id != itemId }
                    },
                    label = { Text("Item Name") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = nameError || uniqueNameError
                )
                if (nameError) {
                    Text("Name is required", color = MaterialTheme.colorScheme.error)
                }
                if (uniqueNameError) {
                    Text("Name must be unique", color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = totalStock,
                    onValueChange = {
                        totalStock = it
                        totalStockError = it.toIntOrNull() == null
                    },
                    label = { Text("Total Stock") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = totalStockError
                )
                if (totalStockError) {
                    Text("Total stock must be a number", color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = price,
                    onValueChange = {
                        price = it
                        priceError = it.toDoubleOrNull() == null
                    },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = priceError
                )
                if (priceError) {
                    Text("Price must be a number", color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                        descriptionError = it.split(" ").size < 3
                    },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = descriptionError
                )
                if (descriptionError) {
                    Text("Description must have at least three words", color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val isNameValid = name.isNotEmpty() && viewModel.inventoryItems.value.none { it.name == name && it.id != itemId }
                        val isTotalStockValid = totalStock.toIntOrNull() != null
                        val isPriceValid = price.toDoubleOrNull() != null
                        val isDescriptionValid = description.split(" ").size >= 3

                        if (isNameValid && isTotalStockValid && isPriceValid && isDescriptionValid) {
                            inventoryItem?.let {
                                viewModel.updateItem(
                                    it.copy(
                                        name = name,
                                        totalStock = totalStock.toInt(),
                                        price = price.toDouble(),
                                        description = description
                                    )
                                )
                                navController.popBackStack()
                            }
                        } else {
                            nameError = !isNameValid
                            uniqueNameError = !isNameValid && viewModel.inventoryItems.value.any { it.name == name && it.id != itemId }
                            totalStockError = !isTotalStockValid
                            priceError = !isPriceValid
                            descriptionError = !isDescriptionValid
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Update Item")
                }
            }
        }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Delete Item") },
            text = { Text("Are you sure you want to delete this item?") },
            confirmButton = {
                Button(
                    onClick = {
                        scope.launch {
                            inventoryItem?.let {
                                viewModel.deleteItem(it)
                                navController.navigate("inventory_list") {
                                    popUpTo("inventory_list") { inclusive = true }
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("No")
                }
            }
        )
    }
}

