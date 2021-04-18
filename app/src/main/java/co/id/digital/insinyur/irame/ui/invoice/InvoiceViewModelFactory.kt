package co.id.digital.insinyur.irame.ui.invoice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.InvoiceRepository

class InvoiceViewModelFactory(private val ar: AuthenticateRepository, private val ir: InvoiceRepository): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InvoiceViewModel::class.java)){
            return InvoiceViewModel(ar, ir) as T
        }

        throw IllegalArgumentException("unknown view model class")
    }
}