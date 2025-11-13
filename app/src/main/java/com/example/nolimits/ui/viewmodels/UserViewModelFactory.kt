package com.example.nolimits.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nolimits.data.local.dao.AppUserDao

class AuthViewModelFactory(
    private val dao: AppUserDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
