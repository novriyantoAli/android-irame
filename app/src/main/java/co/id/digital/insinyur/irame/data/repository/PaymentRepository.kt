package co.id.digital.insinyur.irame.data.repository

import co.id.digital.insinyur.irame.data.db.DB
import co.id.digital.insinyur.irame.data.models.PaymentResponse
import co.id.digital.insinyur.irame.data.network.API
import co.id.digital.insinyur.irame.data.network.SafeAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PaymentRepository(private val db: DB, private val api: API): SafeAPI() {

    suspend fun find(auth: String, payment: PaymentResponse): List<PaymentResponse> {
        val bearer = "Bearer $auth"
        return withContext(Dispatchers.IO){
            apiRequest { api.findPayment(bearer, payment) }
        }
    }

    suspend fun save(auth: String, payment: PaymentResponse): PaymentResponse {
        val bearer = "Bearer $auth"
        return withContext(Dispatchers.IO){
            apiRequest { api.savePayment(bearer, payment) }
        }
    }

    suspend fun delete(auth: String, id: Int): PaymentResponse {
        val bearer = "Bearer $auth"
        return withContext(Dispatchers.IO){
            apiRequest { api.deletePayment(auth = bearer, id = id) }
        }
    }
}