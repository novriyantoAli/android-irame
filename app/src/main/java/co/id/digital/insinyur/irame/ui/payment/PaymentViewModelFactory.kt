package co.id.digital.insinyur.irame.ui.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.PaymentRepository

class PaymentViewModelFactory(private var ar: AuthenticateRepository, private val pr: PaymentRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentViewModel::class.java)){
            return PaymentViewModel(ar, pr) as T
        }

        throw IllegalArgumentException("unknown view model class")
    }
}