package co.id.digital.insinyur.irame.ui.packages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.digital.insinyur.irame.data.models.PackageResponse
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.PackagesRepository
import co.id.digital.insinyur.irame.util.Coroutines

class PackagesViewModel(private val repository: PackagesRepository, private val authenticateRepository: AuthenticateRepository): ViewModel() {

    private val _message = MutableLiveData<String>()
    var message: LiveData<String> = _message

    private val _loading = MutableLiveData<Boolean>()
    var loading: LiveData<Boolean> = _loading

    private val _result = MutableLiveData<List<PackageResponse>>()
    var result: LiveData<List<PackageResponse>> = _result

    private val _selected = MutableLiveData<PackageResponse>()
    val selected: LiveData<PackageResponse> = _selected

    private val _upsert = MutableLiveData<UpsertModel>()
    var upsert: LiveData<UpsertModel> = _upsert

    fun setMessage(msg: String?){
        _message.value = msg
    }

    fun setLoading(loading: Boolean){
        _loading.value = loading
    }

    fun fetch() = Coroutines.main {
        setLoading(true)
        try {
            val user = authenticateRepository.getUser()
            val result = repository.fetch(user.token)

            _result.value = result

        } catch (e: Exception){
            e.printStackTrace()
            setMessage(e.message)
        }
        setLoading(false)
    }

    fun select(packages: PackageResponse){
        _selected.value = packages
    }

    fun insert(packageResponse: PackageResponse) = Coroutines.main{
        setLoading(true)
        try {
            val user = authenticateRepository.getUser()
            val pr = repository.save(user.token, packageResponse)
            val result = UpsertModel(1, pr)

            _upsert.value = result

        } catch (e: Exception){
            e.printStackTrace()
            setMessage(e.message)
        }
        setLoading(false)

        _selected.value = null
    }

    fun update(packageResponse: PackageResponse) = Coroutines.main {
        setLoading(true)
        try {
            val user = authenticateRepository.getUser()
            val pr = repository.update(user.token, packageResponse.id, packageResponse)
            val result = UpsertModel(2, pr)

            _upsert.value = result
        } catch (e: Exception) {
            e.printStackTrace()
            setMessage(e.message)
        }
        setLoading(false)

        _selected.value = null
    }

    fun delete(packageResponse: PackageResponse) = Coroutines.main {
        setLoading(true)
        try {
            val user = authenticateRepository.getUser()
            val message = repository.delete(user.token, packageResponse.id)

            _message.value = message.message

            val result = UpsertModel(0, packageResponse)

            _upsert.value = result
        } catch (e: Exception){
            e.printStackTrace()
            setMessage(e.message)
        }
        setLoading(false)
    }

}