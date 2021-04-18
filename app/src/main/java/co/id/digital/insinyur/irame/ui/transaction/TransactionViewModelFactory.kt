package co.id.digital.insinyur.irame.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.TransactionRepository

class TransactionViewModelFactory(private val repository: TransactionRepository, private val ar: AuthenticateRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)){
            return TransactionViewModel(repository, ar) as T
        }

        throw IllegalArgumentException("unknown view model class")
    }
}