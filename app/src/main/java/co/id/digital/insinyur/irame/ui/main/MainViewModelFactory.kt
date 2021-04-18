package co.id.digital.insinyur.irame.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.RadiusRepository
import co.id.digital.insinyur.irame.data.repository.ReportRepository

class MainViewModelFactory(private val ar: AuthenticateRepository, private val rr: RadiusRepository, private val rer: ReportRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(ar, rr, rer) as T
        }

        throw IllegalArgumentException("unknown class view model")
    }
}