package co.id.digital.insinyur.irame.data.repository

import co.id.digital.insinyur.irame.data.db.DB
import co.id.digital.insinyur.irame.data.models.TransactionResponse
import co.id.digital.insinyur.irame.data.network.API
import co.id.digital.insinyur.irame.data.network.SafeAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionRepository(private val api: API, private val db: DB): SafeAPI() {

    suspend fun report(auth: String, dateStart: String, dateEnd: String): List<TransactionResponse>{
        val bearer = "Bearer $auth"
        return withContext(Dispatchers.IO){
            apiRequest { api.transactionReport(bearer, dateStart, dateEnd) }
        }
    }
}