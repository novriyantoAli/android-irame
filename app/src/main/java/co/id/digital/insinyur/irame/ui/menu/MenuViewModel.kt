package co.id.digital.insinyur.irame.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.digital.insinyur.irame.data.models.MenuResponse
import co.id.digital.insinyur.irame.data.models.PackageResponse
import co.id.digital.insinyur.irame.data.models.RadusergroupResponse
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.MenuRepository
import co.id.digital.insinyur.irame.data.repository.PackagesRepository
import co.id.digital.insinyur.irame.data.repository.RadiusRepository
import co.id.digital.insinyur.irame.util.Coroutines

class MenuViewModel(private val repository: MenuRepository, private val ar: AuthenticateRepository, private val pr: PackagesRepository, private val rr: RadiusRepository): ViewModel() {

    private val _message = MutableLiveData<String>()
    var message: LiveData<String> = _message

    private val _loading = MutableLiveData<Boolean>()
    var loading: LiveData<Boolean> = _loading

    private val _menu = MutableLiveData<List<MenuResponse>>()
    var menu: LiveData<List<MenuResponse>> = _menu

    private val _selectedMenu = MutableLiveData<MenuResponse?>()
    var selectedMenu: LiveData<MenuResponse?> = _selectedMenu

    private val _resultMenu = MutableLiveData<MenuResponse>()
    val resultMenu: LiveData<MenuResponse> = _resultMenu

    private val _deleteMenu = MutableLiveData<MenuResponse>()
    val deleteMenu: LiveData<MenuResponse> = _deleteMenu

    private val _packages = MutableLiveData<List<PackageResponse>>()
    val packages: LiveData<List<PackageResponse>> = _packages

    private val _profile = MutableLiveData<List<RadusergroupResponse>>()
    val profile: LiveData<List<RadusergroupResponse>> = _profile

    fun showLoading(params: Boolean){
        _loading.value = params
    }

    fun showMessage(params: String){
        _message.value = params
    }

    fun fetchMenu() = Coroutines.main {
        try {
            val user = ar.getUser()
            val menu = repository.fetch(user.token)

            this._menu.value = menu
        } catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }

        _loading.value = false
    }

    fun fetchPackage() = Coroutines.main {
        try {
            val user = ar.getUser()
            val packages = pr.fetch(user.token)

            _packages.value = packages
        } catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }

        _loading.value = false
    }

    fun fetchProfile() = Coroutines.main {
        try{
            val user = ar.getUser()
            val profile = rr.fetchRadusergroup(user.token)

            _profile.value = profile

        }catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }

        _loading.value = false
    }

    fun selectedMenu(menu: MenuResponse?){
        _selectedMenu.value = menu
    }

    fun store(menu: MenuResponse)= Coroutines.main {
        try {
            val user = ar.getUser()
            val m = repository.store(user.token, menu)
            _resultMenu.value = m

            _message.value = "data has been saved..."
        } catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }

        _loading.value = false
    }

    fun update(menu: MenuResponse, id: Int) = Coroutines.main {
        try {
            val user = ar.getUser()
            val m = repository.update(user.token, menu, id)
            _resultMenu.value = m

            _message.value = "data has been updated..."
        }catch (e: Exception) {
            e.printStackTrace()
            _message.value = e.message
        }

        _loading.value = false
    }

    fun delete(id: Int) = Coroutines.main {
        try {
            val user = ar.getUser()
            val m = repository.delete(user.token, id)
            _deleteMenu.value = m

            _message.value = "data has been deleted..."
        } catch (e: Exception) {
            e.printStackTrace()
            _message.value = e.message
        }
    }

}