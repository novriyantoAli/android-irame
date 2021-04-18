package co.id.digital.insinyur.irame.ui.reseller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.ResellerRepository

class ResellerViewModelFactory(private val repository: ResellerRepository, private val ar: AuthenticateRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResellerViewModel::class.java)){
            return ResellerViewModel(repository, ar) as T
        }

        throw IllegalArgumentException("unknown view model class")
    }
}