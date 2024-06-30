package com.flutterwave.fwinventory.features.login

import androidx.lifecycle.ViewModel
import com.flutterwave.fwinventory.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    fun login(email: String) {
        authRepository.login(email)
    }

    fun isLoggedIn(): Boolean {
        return authRepository.isLoggedIn()
    }

    fun logout() {
        authRepository.logout()
    }

    fun getCurrentUserId(){
        authRepository.getCurrentUserId()
    }
}
