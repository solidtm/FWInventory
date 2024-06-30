package com.flutterwave.fwinventory.features.inventory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.flutterwave.fwinventory.R
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
    var nameError by remember { mutableStateOf(false) }
    var totalStockError by remember { mutableStateOf(false) }
    var priceError by remember { mutableStateOf(false) }
    var descriptionError by remember { mutableStateOf(false) }
    var uniqueNameError by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

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
                title = { Text(stringResource(id = R.string.edit_inventory_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            nameError = it.isEmpty()
                            uniqueNameError = viewModel.inventoryItems.value.any { item -> item.name == it && item.id != itemId }
                        },
                        label = { Text(stringResource(id = R.string.name)) },
                        modifier = Modifier.fillMaxWidth(),
                        isError = nameError || uniqueNameError
                    )
                    if (nameError) {
                        Text(stringResource(id = R.string.name_required_error), color = MaterialTheme.colorScheme.error)
                    }
                    if (uniqueNameError) {
                        Text(stringResource(id = R.string.name_unique_error), color = MaterialTheme.colorScheme.error)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = totalStock,
                        onValueChange = {
                            totalStock = it
                            totalStockError = it.toIntOrNull() == null
                        },
                        label = { Text(stringResource(id = R.string.total_stock)) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = totalStockError
                    )
                    if (totalStockError) {
                        Text(stringResource(id = R.string.total_stock_number_error), color = MaterialTheme.colorScheme.error)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = price,
                        onValueChange = {
                            price = it
                            priceError = it.toDoubleOrNull() == null
                        },
                        label = { Text(stringResource(id = R.string.price)) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = priceError
                    )
                    if (priceError) {
                        Text(stringResource(id = R.string.price_number_error), color = MaterialTheme.colorScheme.error)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = {
                            description = it
                            descriptionError = it.split(" ").size < 3
                        },
                        label = { Text(stringResource(id = R.string.description)) },
                        modifier = Modifier.fillMaxWidth(),
                        isError = descriptionError
                    )
                    if (descriptionError) {
                        Text(stringResource(id = R.string.description_words_error), color = MaterialTheme.colorScheme.error)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(stringResource(id = R.string.update_item))
                    }
                    Button(
                        onClick = { showDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text(stringResource(id = R.string.delete_item))
                    }
                }
            }
        }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(id = R.string.confirm_delete_title)) },
            text = { Text(stringResource(id = R.string.confirm_delete_message)) },
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
                    Text(stringResource(id = R.string.yes))
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text(stringResource(id = R.string.no))
                }
            }
        )
    }
}


