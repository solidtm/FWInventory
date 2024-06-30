package com.flutterwave.fwinventory.features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flutterwave.fwinventory.features.inventory.AddInventoryScreen
import com.flutterwave.fwinventory.features.inventory.EditInventoryScreen
import com.flutterwave.fwinventory.features.inventory.InventoryListScreen
import com.flutterwave.fwinventory.features.login.LoginScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") { LoginScreen(navController = navController) }
        composable("inventory_list") { InventoryListScreen(navController = navController) }
        composable("add_inventory") { AddInventoryScreen(navController = navController) }
        composable("edit_inventory/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")?.toInt() ?: 0
            EditInventoryScreen(navController = navController, itemId = itemId)
        }
    }
}
