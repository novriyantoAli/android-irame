package co.id.digital.insinyur.irame.ui.nas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.RadiusRepository

class NasViewModelFactory(private val ar: AuthenticateRepository, private val rr: RadiusRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NasViewModel::class.java)){
            return NasViewModel(ar, rr) as T
        }

        throw IllegalArgumentException("unknown view model class")
    }
}