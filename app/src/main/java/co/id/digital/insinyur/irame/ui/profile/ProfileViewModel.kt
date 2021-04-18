package co.id.digital.insinyur.irame.ui.profile

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.digital.insinyur.irame.data.models.ProfileResponse
import co.id.digital.insinyur.irame.data.models.RadgroupResponse
import co.id.digital.insinyur.irame.data.models.RadusergroupResponse
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.RadiusRepository
import co.id.digital.insinyur.irame.util.Coroutines

class ProfileViewModel(private val radiusRepository: RadiusRepository, private val ar: AuthenticateRepository): ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _loading = MutableLiveData<Int>()
    val loading: LiveData<Int> = _loading

    private val _selected = MutableLiveData<RadusergroupResponse>()
    val selected: LiveData<RadusergroupResponse> = _selected

    // profile
    private val _resultProfile = MutableLiveData<List<RadusergroupResponse>>()
    val resultProfile: LiveData<List<RadusergroupResponse>> = _resultProfile

    private val _addProfile = MutableLiveData<RadusergroupResponse>()
    val addProfile: LiveData<RadusergroupResponse> = _addProfile

    private val _deleteProfile = MutableLiveData<RadusergroupResponse>()
    val deleteProfile: LiveData<RadusergroupResponse> = _deleteProfile

    private val _profile = MutableLiveData<ProfileResponse>()
    val profile: LiveData<ProfileResponse> = _profile

    fun setMessage(message: String?){
        _message.value = message
    }

    fun setSelected(select: RadusergroupResponse?){
        _selected.value = select
    }

    fun fetchProfile() = Coroutines.main {
        _loading.value = View.VISIBLE
        val user = ar.getUser()
        try {
            val profiles = radiusRepository.fetchRadusergroup(user.token)
            _resultProfile.value = profiles
        } catch (e: Exception) {
            e.printStackTrace()
            setMessage(e.message)
        }

        _loading.value = View.GONE
    }

    fun saveProfile(profile: ProfileResponse) = Coroutines.main {
        _loading.value = View.VISIBLE
        val user = ar.getUser()
        try {
            val radusergroup = radiusRepository.saveProfile(user.token, profile)
            _addProfile.value = radusergroup
            setSelected(null)
        }catch (e: Exception){
            e.printStackTrace()
            setMessage(e.message)
        }

        _loading.value = View.GONE
    }

    fun loadProfile(profile: String) = Coroutines.main {
        _loading.value = View.VISIBLE
        val user = ar.getUser()
        try {
            val profiles = radiusRepository.loadProfile(user.token, profile)
            _profile.value = profiles
        } catch (e: Exception){
            e.printStackTrace()
            setMessage(e.message)
        }
        _loading.value = View.GONE
    }

    fun deleteProfile(profile: RadusergroupResponse) = Coroutines.main {
        _loading.value = View.VISIBLE
        val user = ar.getUser()
        try {
            val response = radiusRepository.deleteRadusergroup(user.token, profile.username)
            _deleteProfile.value = response
        } catch (e: Exception){
            e.printStackTrace()
            setMessage(e.message)
        }

        _loading.value = View.GONE
    }
}