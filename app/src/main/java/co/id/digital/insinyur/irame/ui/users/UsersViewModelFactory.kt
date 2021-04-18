package co.id.digital.insinyur.irame.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.PackagesRepository
import co.id.digital.insinyur.irame.data.repository.RadiusRepository

@Suppress("UNCHECKED_CAST")
class UsersViewModelFactory(private val ar: AuthenticateRepository, private val radiusRepository: RadiusRepository, private val pr: PackagesRepository, private val rr: RadiusRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)){
            return UsersViewModel(ar, radiusRepository, pr, rr) as T
        }

        throw IllegalArgumentException("unknown view model class")
    }
}