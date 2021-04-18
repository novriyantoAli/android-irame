package co.id.digital.insinyur.irame.ui.users

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import co.id.digital.insinyur.irame.data.models.*
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.PackagesRepository
import co.id.digital.insinyur.irame.data.repository.RadiusRepository
import co.id.digital.insinyur.irame.util.Coroutines

class UsersViewModel(private val ar: AuthenticateRepository, private val radiusRepository: RadiusRepository, private val par: PackagesRepository, private val rr: RadiusRepository): ViewModel() {

    private val _result = MutableLiveData<PageUsersResponse>()
    val result: LiveData<PageUsersResponse> = _result

    private val _saveResult = MutableLiveData<UsersResponse>()
    val saveResult: LiveData<UsersResponse> = _saveResult

    private val _packages = MutableLiveData<List<PackageResponse>>()
    val packages: LiveData<List<PackageResponse>> = _packages

    private val _profile = MutableLiveData<List<RadusergroupResponse>>()
    val profile: LiveData<List<RadusergroupResponse>> = _profile

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _loading = MutableLiveData<Int>()
    val loading: LiveData<Int> = _loading

    private val _selected = MutableLiveData<UsersResponse?>()
    val selected: LiveData<UsersResponse?> = _selected

    fun setSelected(select: UsersResponse?){
        _selected.value = select
    }

    fun setMessage(string: String){
        _message.value = string
    }

    fun usersList() = radiusRepository.fetchPagedUsers().cachedIn(viewModelScope)

    fun fetch(last: UsersResponse?) = Coroutines.main {
        try {
            val user = ar.getUser()
            _result.value = if (last == null){
                radiusRepository.fetchUsers(user.token)
            } else {
                radiusRepository.fetchUsers(user.token, 10, last.id)
            }

        } catch (e: Exception){
            e.printStackTrace()
            _error.value = e.message
        }
    }

    fun save(usersResponse: UsersResponse) = Coroutines.main {
        try {
            val user = ar.getUser()
            radiusRepository.saveUsers(user.token, usersResponse)

            fetch(null)
            setSelected(null)
        }catch (e: Exception){
            e.printStackTrace()
            _error.value = e.message
        }
    }

    fun fetchPackage() = Coroutines.main {
        _loading.value = View.VISIBLE
        try {
            val user = ar.getUser()
            val packages = par.fetch(user.token)

            _packages.value = packages
        } catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }

        _loading.value = View.GONE
    }

    fun fetchProfile() = Coroutines.main {
        _loading.value = View.VISIBLE
        try{
            val user = ar.getUser()
            val profile = rr.fetchRadusergroup(user.token)

            _profile.value = profile

        }catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }

        _loading.value = View.GONE
    }
}