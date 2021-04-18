package co.id.digital.insinyur.irame.ui.trace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.RadiusRepository

class TraceViewModelFactory(private val ar: AuthenticateRepository, private val repository: RadiusRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TraceViewModel::class.java)){
            return TraceViewModel(ar, repository) as T
        }

        throw IllegalArgumentException("unknown view model class")
    }
}