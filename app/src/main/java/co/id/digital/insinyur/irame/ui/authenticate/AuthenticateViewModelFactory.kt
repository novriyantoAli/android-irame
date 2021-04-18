package co.id.digital.insinyur.irame.ui.authenticate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository

class AuthenticateViewModelFactory(private val repository: AuthenticateRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthenticateViewModel::class.java)){
            return AuthenticateViewModel(repository) as T
        }

        throw IllegalArgumentException("unknown view model class")
    }
}