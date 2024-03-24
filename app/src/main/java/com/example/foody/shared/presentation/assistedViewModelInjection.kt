package com.example.foody.shared.presentation

import androidx.annotation.MainThread
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.withCreationCallback

// reified is used to be able to determine the function return type at runtime (and because of
// VM::class.java), thus, the function must be inline. We have here one more parameter "extras",
// which is used to get arguments when navigating.
@Composable
inline fun <reified VM : ViewModel, T, VMF: AssistedViewModelFactory<VM, T>> assistedHiltViewModel(
    extras: T,
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    key: String? = null,
): VM {
    // making VM with default Hilt VM factory (or null)
    val factory: ViewModelProvider.Factory? =
        if (viewModelStoreOwner is HasDefaultViewModelProviderFactory) {
            HiltViewModelFactory(
                context = LocalContext.current,
                delegateFactory = viewModelStoreOwner.defaultViewModelProviderFactory
            )
        } else {
            // Use the default factory provided by the ViewModelStoreOwner
            // and assume it is an @AndroidEntryPoint annotated fragment or activity
            null
        }
    // getting extras via assistedFactory (or empty)
    val creationExtras: CreationExtras =
        if (viewModelStoreOwner is HasDefaultViewModelProviderFactory) {
            viewModelStoreOwner.defaultViewModelCreationExtras
                .withCreationCallback<VMF> { assistedFactory ->
                    assistedFactory.create(extras)
                }
        } else {
            CreationExtras.Empty
        }
    // returning a VM with extras ...
    return viewModel(VM::class.java, viewModelStoreOwner, key, factory, creationExtras)
}

@Suppress("unused")
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
