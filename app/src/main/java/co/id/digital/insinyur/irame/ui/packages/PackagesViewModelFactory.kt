package co.id.digital.insinyur.irame.ui.packages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.PackagesRepository

class PackagesViewModelFactory(private val repository: PackagesRepository, private val ar: AuthenticateRepository): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PackagesViewModel::class.java)){
            return PackagesViewModel(repository, ar) as T
        }

        throw IllegalArgumentException("unknown view model class")
    }
}