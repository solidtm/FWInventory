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
import com.flutterwave.fwinventory.features.login.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInventoryScreen(
    navController: NavHostController,
    viewModel: InventoryViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var totalStock by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf<String?>(null) }
    var totalStockError by remember { mutableStateOf<String?>(null) }
    var priceError by remember { mutableStateOf<String?>(null) }
    var descriptionError by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(id = R.string.add_inventory_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
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
                OutlinedTextFieldWithError(
                    value = mutableStateOf(name),
                    onValueChange = { name = it },
                    label = stringResource(id = R.string.item_name),
                    isError = nameError != null,
                    errorMessage = nameError,
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextFieldWithError(
                    value = mutableStateOf(totalStock),
                    onValueChange = { totalStock = it },
                    label = stringResource(id = R.string.total_stock),
                    isError = totalStockError != null,
                    errorMessage = totalStockError,
                    keyboardType = KeyboardType.Number
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextFieldWithError(
                    value = mutableStateOf(price),
                    onValueChange = { price = it },
                    label = stringResource(id = R.string.price),
                    isError = priceError != null,
                    errorMessage = priceError,
                    keyboardType = KeyboardType.Number
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextFieldWithError(
                    value = mutableStateOf(description),
                    onValueChange = { description = it },
                    label = stringResource(id = R.string.description),
                    isError = descriptionError != null,
                    errorMessage = descriptionError
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            var hasError = false
                            if (name.isBlank()) {
                                nameError = "Name is required"
                                hasError = true
                            } else if (viewModel.isNameDuplicate(name)) {
                                nameError = "Name must be unique"
                                hasError = true
                            }

                            val totalStockInt = totalStock.toIntOrNull()
                            if (totalStock.isBlank() || totalStockInt == null) {
                                totalStockError = "Total stock is required and must be a number"
                                hasError = true
                            }

                            val priceDouble = price.toDoubleOrNull()
                            if (price.isBlank() || priceDouble == null) {
                                priceError = "Price is required and must be a number"
                                hasError = true
                            }

                            if (description.isBlank() || description.split(" ").size < 3) {
                                descriptionError = "Description is required and must have at least three words"
                                hasError = true
                            }

                            if (!hasError) {
                                viewModel.addItem(
                                    InventoryItem(
                                        userId = authViewModel.getCurrentUserId(),
                                        name = name,
                                        totalStock = totalStockInt!!,
                                        price = priceDouble!!,
                                        description = description
                                    )
                                )
                                navController.popBackStack()
                            }
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(stringResource(id = R.string.add_item))
                }
            }
        }
    )
}


@Composable
fun OutlinedTextFieldWithError(
    value: MutableState<String>,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean,
    errorMessage: String?,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value.value,
            onValueChange = { onValueChange(it); value.value = it },
            label = { Text(label) },
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = modifier.fillMaxWidth()
        )
        errorMessage?.let {
            Text(
                text = errorMessage,
                modifier = Modifier.padding(start = 4.dp),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


