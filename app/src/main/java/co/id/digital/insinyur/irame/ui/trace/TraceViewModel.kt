package co.id.digital.insinyur.irame.ui.trace

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.digital.insinyur.irame.data.models.PageRadpostauthResponse
import co.id.digital.insinyur.irame.data.models.RadpostauthResponse
import co.id.digital.insinyur.irame.data.models.UsersResponse
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.RadiusRepository
import co.id.digital.insinyur.irame.util.Coroutines

class TraceViewModel(private val ar: AuthenticateRepository, private val repository: RadiusRepository): ViewModel() {

    private val _result = MutableLiveData<PageRadpostauthResponse>()
    val result: LiveData<PageRadpostauthResponse> = _result

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _loading = MutableLiveData<Int>()
    val loading: LiveData<Int> = _loading

    fun get(rpar: RadpostauthResponse) = Coroutines.main {
        _loading.value = View.VISIBLE
        try {
            val user = ar.getUser()
            _result.value = repository.fetchTrace(
                    auth = user.token, username = rpar.username!!, id = rpar.id!!
            )
        } catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }
        _loading.value = View.GONE
    }
}