package co.id.digital.insinyur.irame.ui.reseller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.digital.insinyur.irame.data.models.ResellerResponse
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.ResellerRepository
import co.id.digital.insinyur.irame.util.Coroutines

class ResellerViewModel(private val repository: ResellerRepository, private val ar: AuthenticateRepository): ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _resultBalance = MutableLiveData<Int>()
    val resultBalance: LiveData<Int> = _resultBalance

    private val _resultReseller = MutableLiveData<List<ResellerResponse>>()
    val resultReseller: LiveData<List<ResellerResponse>> = _resultReseller

    private val _result = MutableLiveData<Map<Int, ResellerResponse>>()
    val result: LiveData<Map<Int, ResellerResponse>> = _result

    private val _resultDelete = MutableLiveData<ResellerResponse>()
    val resultDelete: LiveData<ResellerResponse> = _resultDelete

    fun fetch() = Coroutines.main {
        _loading.value = true
        try {
            val user = ar.getUser()
            _resultReseller.value = repository.fetch(user.token)
        } catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }

        _loading.value = false
    }

    fun delete(id: Int) = Coroutines.main {
        _loading.value = true

        try {
            val user = ar.getUser()
            _resultDelete.value = repository.delete(user.token, id)
        } catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }

        _loading.value = false
    }

    fun activated(id: Int, index: Int) = Coroutines.main {
        _loading.value = true
        try {
            val user = ar.getUser()
            _result.value = mapOf(index to repository.activated(user.token, id))
        } catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }

        _loading.value = false
    }

    fun balance(id: Int) = Coroutines.main {
        _loading.value = true
        try {
            val user = ar.getUser()
            val msgBalance = repository.balance(user.token, id)
            _resultBalance.value = msgBalance.balance
        }catch (e:Exception){
            e.printStackTrace()
            _message.value = e.message
        }

        _loading.value = false
    }

    fun balanceRefill(id: Int, balance: Int) = Coroutines.main {
        _loading.value = true
        try {
            val user = ar.getUser()
            val message = repository.balanceRefill(user.token, id, balance)
            _message.value = message.message
        } catch (e: Exception) {
            e.printStackTrace()
            _message.value = e.message
        }
        _loading.value = false
    }

    fun showLoading(params: Boolean){
        _loading.value = params
    }
}