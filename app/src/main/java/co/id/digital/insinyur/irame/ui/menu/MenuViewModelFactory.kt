package co.id.digital.insinyur.irame.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.MenuRepository
import co.id.digital.insinyur.irame.data.repository.PackagesRepository
import co.id.digital.insinyur.irame.data.repository.RadiusRepository

class MenuViewModelFactory(private val repository: MenuRepository, private val ar: AuthenticateRepository, private val pr: PackagesRepository, private val rr: RadiusRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuViewModel::class.java)){
            return MenuViewModel(repository, ar, pr, rr) as T
        }

        throw IllegalArgumentException("unknown class view model")
    }
}