package co.id.digital.insinyur.irame.ui.authenticate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.util.Coroutines
import co.id.digital.insinyur.irame.util.lazyDeferred

class AuthenticateViewModel(val repository: AuthenticateRepository): ViewModel() {

    private var _message = MutableLiveData<String?>()
    var message: LiveData<String?> = _message

    val user by lazyDeferred {
        repository.checkState()
    }
    fun login(username: String, password: String){
        Coroutines.io {
            val result = repository.login(username, password)
            Coroutines.main { _message.value = result }
        }
    }
}