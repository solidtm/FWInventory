package com.flutterwave.fwinventory.features.inventory.fakes

import android.content.SharedPreferences
import javax.inject.Inject

class AuthRepository @Inject constructor(private val sharedPreferences: SharedPreferences) :
    IAuthRepository {

    override fun login(email: String) {
        val userId = email.hashCode()  // Generate unique user ID
        sharedPreferences.edit().putString("email", email).apply()
        sharedPreferences.edit().putInt("user_id", userId).apply() // Save user ID
    }

    override fun isLoggedIn(): Boolean {
        return sharedPreferences.getString("email", null) != null
    }

    override fun logout() {
        sharedPreferences.edit().remove("email").apply()
        sharedPreferences.edit().remove("user_id").apply()
    }

    override fun getCurrentUserId(): Int {
        return sharedPreferences.getInt("user_id", -1)
    }
}