package com.flutterwave.fwinventory.domain

import android.content.SharedPreferences
import java.util.UUID
import javax.inject.Inject

class AuthRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun login(email: String) {
        val userId = email.hashCode()  // Generate unique user ID
        sharedPreferences.edit().putString("email", email).apply()
        sharedPreferences.edit().putInt("user_id", userId).apply() // Save user ID
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getString("email", null) != null
    }

    fun logout() {
        sharedPreferences.edit().remove("email").apply()
        sharedPreferences.edit().remove("user_id").apply()
    }

    fun getCurrentUserId(): Int {
        return sharedPreferences.getInt("user_id", -1)
    }
}
