package com.flutterwave.fwinventory.features.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                authViewModel.login(email)
                navController.navigate("inventory_list")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    MaterialTheme {
//        LoginScreen(onLogin = { email, password ->
//            // Handle login logic here, for preview we can just print the email and password
//            println("Logging in with $email and $password")
//        })
//    }
//}
