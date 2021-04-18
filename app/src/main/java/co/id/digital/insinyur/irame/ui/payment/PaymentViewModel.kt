package co.id.digital.insinyur.irame.ui.payment

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.digital.insinyur.irame.data.models.PaymentResponse
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.PaymentRepository
import co.id.digital.insinyur.irame.util.Constant
import co.id.digital.insinyur.irame.util.Coroutines

class PaymentViewModel(private val ar: AuthenticateRepository, private val pr: PaymentRepository): ViewModel() {

    private val _result = MutableLiveData<List<PaymentResponse>>()
    var result: LiveData<List<PaymentResponse>> = _result

    private val _operation = MutableLiveData<OperationResult>()
    var operation: LiveData<OperationResult> = _operation


    private val _loading = MutableLiveData<Int>()
    var loading: LiveData<Int> = _loading

    private val _message = MutableLiveData<String>()
    var message: LiveData<String> = _message

    private val _mode = MutableLiveData<Boolean>()
    val mode: LiveData<Boolean> = _mode

    private val _data = MutableLiveData<Int>()
    var data: LiveData<Int> = _data

    fun setMode(boolean: Boolean){
        _mode.value = boolean
    }

    fun setData(data: Int){
        _data.value = data
    }

    fun setMessage(message: String) {
        _message.value = message
    }

    fun find(payment: PaymentResponse) = Coroutines.main {
        _loading.value = View.VISIBLE
        try {
            val user = ar.getUser()
            _result.value = pr.find(user.token, payment)
        } catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }
        _loading.value = View.GONE
    }

    fun save(noInvoice: Int, nominal: Int) = Coroutines.main {
        _loading.value = View.VISIBLE
        try {
            val user = ar.getUser()


            val data = pr.save(user.token, PaymentResponse(noInvoice = noInvoice.toString(), nominal = nominal))
            val operation = OperationResult(mode = Constant.OPERATION_SAVE, data = data)

            _operation.value = operation

        } catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }
        _loading.value = View.GONE
    }

    fun delete(payment: PaymentResponse) = Coroutines.main {
        _loading.value = View.VISIBLE
        try {
            val user = ar.getUser()
            pr.delete(user.token, payment.id!!)

            val operation = OperationResult(mode = Constant.OPERATION_DELETE, data = payment)
            _operation.value = operation

        } catch (e: Exception){
            e.printStackTrace()
            _message.value = e.message
        }
        _loading.value = View.GONE
    }
}