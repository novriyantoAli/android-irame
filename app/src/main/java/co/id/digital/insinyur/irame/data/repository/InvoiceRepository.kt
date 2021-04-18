package co.id.digital.insinyur.irame.data.repository

import co.id.digital.insinyur.irame.data.db.DB
import co.id.digital.insinyur.irame.data.models.InvoiceResponse
import co.id.digital.insinyur.irame.data.models.PageInvoiceResponse
import co.id.digital.insinyur.irame.data.network.API
import co.id.digital.insinyur.irame.data.network.SafeAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InvoiceRepository(private val api: API, private val db: DB): SafeAPI() {

    suspend fun fetch(auth: String, limit: Int = 10, id: Int = 0): PageInvoiceResponse {
        return  withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.fetchInvoice(bearer, id, limit) }
        }
    }

    suspend fun save(auth: String, body: InvoiceResponse): InvoiceResponse {
        return withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.saveInvoice(bearer, body) }
        }
    }

    suspend fun update(auth: String, body: InvoiceResponse): InvoiceResponse {
        return withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.updateInvoice(bearer, body) }
        }
    }

    suspend fun delete(auth: String, id: Int): InvoiceResponse {
        return withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.deleteInvoice(bearer, id) }
        }
    }
}