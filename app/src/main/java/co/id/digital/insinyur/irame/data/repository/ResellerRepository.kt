package co.id.digital.insinyur.irame.data.repository

import co.id.digital.insinyur.irame.data.db.DB
import co.id.digital.insinyur.irame.data.models.BalanceResponse
import co.id.digital.insinyur.irame.data.models.MessageResponse
import co.id.digital.insinyur.irame.data.models.ResellerResponse
import co.id.digital.insinyur.irame.data.network.API
import co.id.digital.insinyur.irame.data.network.SafeAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ResellerRepository(private val api: API, private val db: DB): SafeAPI() {

    suspend fun fetch(auth: String): List<ResellerResponse> {
        val bearer = "Bearer $auth"
        return withContext(Dispatchers.IO){
            apiRequest { api.fetchReseller(bearer) }
        }
    }

    suspend fun delete(auth: String, id: Int): ResellerResponse {
        val bearer = "Bearer $auth"
        return  withContext(Dispatchers.IO){
            apiRequest { api.deleteReseller(bearer, id) }
        }
    }

    suspend fun activated(auth: String, id: Int): ResellerResponse {
        val bearer = "Bearer $auth"
        return withContext(Dispatchers.IO){
            apiRequest { api.activatedReseller(bearer, id) }
        }
    }

    suspend fun balance(auth: String, id: Int): BalanceResponse {
        val bearer = "Bearer $auth"
        return withContext(Dispatchers.IO){
            apiRequest { api.balance(bearer, id) }
        }
    }

    suspend fun balanceRefill(auth: String, id: Int, balance: Int): MessageResponse {
        val bearer = "Bearer $auth"
        return withContext(Dispatchers.IO){
            apiRequest { api.balanceRefill(bearer, id, balance) }
        }
    }
}