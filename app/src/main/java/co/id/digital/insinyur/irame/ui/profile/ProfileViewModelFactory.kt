package co.id.digital.insinyur.irame.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.RadiusRepository

class ProfileViewModelFactory(private val radiusRepository: RadiusRepository, private val ar: AuthenticateRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(radiusRepository, ar) as T
        }

        throw IllegalArgumentException("unknown view model class")
    }
}