package com.example.foody.shared.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


interface AssistedViewModelFactory<VM : ViewModel, T> : ViewModelProvider.Factory {
    fun create(input: T): VM
}
