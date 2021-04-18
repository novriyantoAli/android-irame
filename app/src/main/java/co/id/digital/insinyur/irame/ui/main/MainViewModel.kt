package co.id.digital.insinyur.irame.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.digital.insinyur.irame.data.models.FinanceReportResponse
import co.id.digital.insinyur.irame.data.models.NASResponse
import co.id.digital.insinyur.irame.data.models.UsersResponse
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.RadiusRepository
import co.id.digital.insinyur.irame.data.repository.ReportRepository
import co.id.digital.insinyur.irame.util.ApiException
import co.id.digital.insinyur.irame.util.Coroutines

class MainViewModel(val ar: AuthenticateRepository, private val rr: RadiusRepository, private val rer: ReportRepository): ViewModel() {

    private val _logout = MutableLiveData<Boolean>()
    val resultLogout: LiveData<Boolean> = _logout

    private val _menu = MutableLiveData<Boolean>()
    val menu: LiveData<Boolean> = _menu

    private val _activity = MutableLiveData<Class<*>>()
    val activity: LiveData<Class<*>> = _activity

    private val _nas = MutableLiveData<NASResponse>()
    val nas: LiveData<NASResponse> = _nas

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _expiration = MutableLiveData<List<UsersResponse>>()
    val expiration: LiveData<List<UsersResponse>> = _expiration

    private val _month = MutableLiveData<List<FinanceReportResponse>>()
    val month: LiveData<List<FinanceReportResponse>> = _month

    private val _year = MutableLiveData<List<FinanceReportResponse>>()
    val year: LiveData<List<FinanceReportResponse>> = _year

    fun checkNAS() = Coroutines.main {
        try {
            val user = ar.getUser()
            _nas.value = rr.getNAS(user.token)
        } catch (e: ApiException){
            e.printStackTrace()
            _message.value = e.message
        } catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }
    }

    fun defaultNAS() = Coroutines.main { _nas.value = NASResponse() }

    fun saveNAS(host: String, port: String) = Coroutines.main {
        try {
            val user = ar.getUser()
            _nas.value = rr.upsertNAS(user.token, host, port)
        }  catch (e: ApiException){
            e.printStackTrace()
            _message.value = e.message
            _nas.value = NASResponse()
        } catch (e: Exception){
            e.printStackTrace()
            _nas.value = NASResponse()
        }
    }

    fun requestUsername() = Coroutines.main {
        try {
            val user = ar.getUser()
            _username.value = "Hello ${user.username}!"
        }  catch (e: ApiException){
            e.printStackTrace()
            _message.value = e.message
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun currentMonthReport() = Coroutines.main {
        try {
            val user = ar.getUser()
            _month.value = rer.financeCurrentMonth(user.token)
        } catch (e: ApiException){
            e.printStackTrace()
            _message.value = e.message
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun currentYearReport() = Coroutines.main {
        try {
            val user = ar.getUser()
            _year.value = rer.financeCurrentYear(user.token)
        } catch (e: ApiException){
            e.printStackTrace()
            _message.value = e.message
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun fetchExpiredToday() = Coroutines.main {
        try {
            val user = ar.getUser()
            _expiration.value = rer.expirationToday(user.token)
        }  catch (e: ApiException){
            e.printStackTrace()
            _message.value = e.message
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun logout() = Coroutines.main {
        _logout.value = ar.logout()
    }

    fun setMenu(params: Boolean) {
        _menu.value = params
    }

    fun moveActivity(cls: Class<*>){
        _activity.value = cls
    }
}