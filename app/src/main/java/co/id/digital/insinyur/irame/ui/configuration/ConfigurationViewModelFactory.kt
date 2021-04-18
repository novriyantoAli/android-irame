package co.id.digital.insinyur.irame.ui.configuration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.data.repository.ApplicationRepository

class ConfigurationViewModelFactory(private val applicationRepository: ApplicationRepository): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfigurationViewModel::class.java)){
            return ConfigurationViewModel(applicationRepository) as T
        }

        throw IllegalArgumentException("unknown view model class")
    }
}