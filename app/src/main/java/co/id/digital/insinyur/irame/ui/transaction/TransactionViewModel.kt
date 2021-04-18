package co.id.digital.insinyur.irame.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.digital.insinyur.irame.data.models.TransactionResponse
import co.id.digital.insinyur.irame.data.repository.AuthenticateRepository
import co.id.digital.insinyur.irame.data.repository.TransactionRepository
import co.id.digital.insinyur.irame.util.Coroutines

class TransactionViewModel(private val repository: TransactionRepository, private val ar: AuthenticateRepository): ViewModel() {

    private val _expendableData = MutableLiveData<ExpendableData>()
    val expendableData: LiveData<ExpendableData> = _expendableData

    private val _balance = MutableLiveData<BalanceData>()
    val balance: LiveData<BalanceData> = _balance

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun fetchReport(dateStart: String, dateEnd: String) = Coroutines.main {
        _loading.value = true
        try{
            val user = ar.getUser()
            val transactionResult = repository.report(user.token, dateStart, dateEnd)

            val map = HashMap<String, List<TransactionResponse>>()
            val group = ArrayList<String>()

            var balanceOUT = 0
            var balanceIN = 0
            val title = transactionResult.distinctBy { it.nameReseller }
            title.forEach {obj ->
                // filter transaction result
                group.add(obj.nameReseller)
                val filtered = transactionResult.filter { it.nameReseller == obj.nameReseller }
                filtered.forEach {
                    if (it.status == "IN") balanceIN += it.value else balanceOUT += it.value
                }
                // add to exp
                map[obj.nameReseller] = filtered
            }
            _balance.value = BalanceData(balanceIN = balanceIN, balanceOUT = balanceOUT)

            _expendableData.value = ExpendableData(title = group, data = map)

        }catch (e: Exception){
            e.printStackTrace()
        }

        _loading.value = false
    }
}