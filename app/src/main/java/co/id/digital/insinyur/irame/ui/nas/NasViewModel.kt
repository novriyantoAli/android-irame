package co.id.digital.insinyur.irame.ui.nas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.digital.insinyur.irame.data.models.NASResponse
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.RadiusRepository
import co.id.digital.insinyur.irame.util.Coroutines

class NasViewModel(private val ar: AuthenticateRepository, private val rr: RadiusRepository): ViewModel() {
    private val _result = MutableLiveData<NASResponse>()
    val result: LiveData<NASResponse> = _result

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun load() = Coroutines.main {
        try {
            val user = ar.getUser()
            _result.value = rr.getNAS(user.token)
        } catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }
    }

    fun save(host: String, secret: String) = Coroutines.main {
        try {
            val user = ar.getUser()
            rr.upsertNAS(user.token, host, secret)

            _message.value = "success to save"
        } catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }
    }
}