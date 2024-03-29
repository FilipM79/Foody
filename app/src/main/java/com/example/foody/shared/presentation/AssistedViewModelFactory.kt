package com.example.foody.shared.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// custom VM factory with generic types, for assisted injection
interface AssistedViewModelFactory<VM : ViewModel, T> : ViewModelProvider.Factory {
    fun create(input: T): VM
}
