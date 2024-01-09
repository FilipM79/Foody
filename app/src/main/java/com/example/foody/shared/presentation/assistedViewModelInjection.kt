package com.example.foody.shared.presentation

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import dagger.hilt.android.lifecycle.withCreationCallback


@MainThread
inline fun <reified VM : ViewModel, T, VMF: AssistedViewModelFactory<VM, T>> Fragment.assistedViewModels(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null,
    noinline extrasProvider: () -> T
): Lazy<VM> {
    val owner by lazy(LazyThreadSafetyMode.NONE) { ownerProducer() }
    val extrasProducer: (() -> CreationExtras) = {
        val creationExtras = (owner as? HasDefaultViewModelProviderFactory)
            ?.defaultViewModelCreationExtras
            ?: defaultViewModelCreationExtras
        creationExtras.withCreationCallback<VMF> { assistedFactory ->
            assistedFactory.create(extrasProvider())
        }
    }
    return createViewModelLazy(
        VM::class,
        { owner.viewModelStore },
        extrasProducer,
        factoryProducer ?: {
            (owner as? HasDefaultViewModelProviderFactory)?.defaultViewModelProviderFactory
                ?: defaultViewModelProviderFactory
        }
    )
}
