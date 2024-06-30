package com.flutterwave.fwinventory.features.inventory.fakes

class FakeAuthRepository : IAuthRepository {
    private var isLoggedIn = false
    private var currentUserId = -1

    override fun login(email: String) {
        isLoggedIn = true
        currentUserId = email.hashCode()
    }

    override fun isLoggedIn(): Boolean {
        return isLoggedIn
    }

    override fun logout() {
        isLoggedIn = false
        currentUserId = -1
    }

    override fun getCurrentUserId(): Int {
        return currentUserId
    }
}




