package com.flutterwave.fwinventory.features.inventory.fakes

interface IAuthRepository {
    fun login(email: String)
    fun isLoggedIn(): Boolean
    fun logout()
    fun getCurrentUserId(): Int
}