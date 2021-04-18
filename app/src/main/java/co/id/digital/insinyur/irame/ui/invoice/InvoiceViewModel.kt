package co.id.digital.insinyur.irame.ui.invoice

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.digital.insinyur.irame.data.models.InvoiceResponse
import co.id.digital.insinyur.irame.data.models.PageInvoiceResponse
import co.id.digital.insinyur.irame.data.models.PageUsersResponse
import co.id.digital.insinyur.irame.data.models.UsersResponse
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.InvoiceRepository
import co.id.digital.insinyur.irame.util.Constant
import co.id.digital.insinyur.irame.util.Coroutines

class InvoiceViewModel(private val ar: AuthenticateRepository, private val ir: InvoiceRepository): ViewModel() {

    private val _result = MutableLiveData<PageInvoiceResponse>()
    val result: LiveData<PageInvoiceResponse> = _result

    private val _operationResult = MutableLiveData<OperationResult>()
    val operationResult: LiveData<OperationResult> = _operationResult

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _loading = MutableLiveData<Int>()
    val loading: LiveData<Int> = _loading

    private val _mode = MutableLiveData<OperationResult>()
    val mode: LiveData<OperationResult> = _mode

//    fun mode(invoice: InvoiceResponse?) = Coroutines.main {
//        _operationResult.value = OperationResult(Constant.OPERATION_MODE, invoice)
//    }

    fun setMode(mode: String, invoice: InvoiceResponse?) = Coroutines.main {
        _mode.value = OperationResult(mode, invoice)
    }

    fun fetch(invoice: InvoiceResponse?) = Coroutines.main {
        _loading.value = View.VISIBLE
        try {
            val user = ar.getUser()
            _result.value = if (invoice == null){ ir.fetch(user.token) }
            else { ir.fetch(auth = user.token, id = invoice.id!!) }
        } catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }
        _loading.value = View.GONE
    }

    fun save(invoice: InvoiceResponse) = Coroutines.main {
        _loading.value = View.VISIBLE
        try {
            val user = ar.getUser()
            _operationResult.value = OperationResult(
                Constant.OPERATION_SAVE, ir.save(user.token, invoice)
            )
            _mode.value = null
        } catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }
        _loading.value = View.GONE
    }

    fun update(invoice: InvoiceResponse) = Coroutines.main {
        _loading.value = View.VISIBLE
        try {
            val user = ar.getUser()
            _operationResult.value = OperationResult(
                Constant.OPERATION_UPDATE, ir.update(user.token, invoice)
            )
            _mode.value = null
        } catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }
        _loading.value = View.GONE
    }

    fun delete(invoice: InvoiceResponse) = Coroutines.main {
        _loading.value = View.VISIBLE
        try {
            val user = ar.getUser()
            _operationResult.value = OperationResult(
                Constant.OPERATION_DELETE, ir.delete(user.token, invoice.id!!)
            )
            _mode.value = null
        } catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }
        _loading.value = View.GONE
    }
}