package co.id.digital.insinyur.irame.ui.configuration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.digital.insinyur.irame.data.db.entities.AppState
import co.id.digital.insinyur.irame.data.repository.ApplicationRepository
import co.id.digital.insinyur.irame.util.Coroutines

class ConfigurationViewModel(private val applicationRepository: ApplicationRepository): ViewModel() {

    private val _app = MutableLiveData<AppState>()
    val app: LiveData<AppState> = _app

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun load() = Coroutines.main {
        try {
            _app.value = applicationRepository.getApp()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun save(link: String, port: String) = Coroutines.main {
        try {
            applicationRepository.saveApp(link, port)

            _message.value = "success to save data"
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}