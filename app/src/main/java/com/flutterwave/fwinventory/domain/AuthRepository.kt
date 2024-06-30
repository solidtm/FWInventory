package com.flutterwave.fwinventory.domain

import android.content.SharedPreferences
import java.util.UUID
import javax.inject.Inject

/**
 * Repository class for handling authentication related operations.
 *
 * @property sharedPreferences SharedPreferences instance for storing user data.
 */
class AuthRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {

    /**
     * Logs in a user by storing their email and a generated user ID in SharedPreferences.
     *
     * @param email The email address of the user.
     */
    fun login(email: String) {
        val userId = email.hashCode()  // Generate unique user ID
        sharedPreferences.edit().putString("email", email).apply()
        sharedPreferences.edit().putInt("user_id", userId).apply() // Save user ID
    }

    /**
     * Checks if a user is logged in by verifying the presence of an email in SharedPreferences.
     *
     * @return `true` if the user is logged in, `false` otherwise.
     */
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getString("email", null) != null
    }

    /**
     * Logs out the user by removing their email and user ID from SharedPreferences.
     */
    fun logout() {
        sharedPreferences.edit().remove("email").apply()
        sharedPreferences.edit().remove("user_id").apply()
    }

    /**
     * Retrieves the current logged-in user's ID from SharedPreferences.
     *
     * @return The user ID, or -1 if no user is logged in.
     */
    fun getCurrentUserId(): Int {
        return sharedPreferences.getInt("user_id", -1)
    }
}