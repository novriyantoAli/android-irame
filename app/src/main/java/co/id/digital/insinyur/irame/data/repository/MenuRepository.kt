package co.id.digital.insinyur.irame.data.repository

import androidx.lifecycle.LiveData
import co.id.digital.insinyur.irame.data.db.DB
import co.id.digital.insinyur.irame.data.models.MenuResponse
import co.id.digital.insinyur.irame.data.models.MessageResponse
import co.id.digital.insinyur.irame.data.network.API
import co.id.digital.insinyur.irame.data.network.SafeAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MenuRepository(private val api: API, val db: DB): SafeAPI() {

    suspend fun fetch(auth: String): List<MenuResponse> {
        return withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.fetchMenu(bearer) }
        }
    }

    suspend fun store(auth: String, menuResponse: MenuResponse): MenuResponse {
        return withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.storeMenu(bearer, menuResponse) }
        }
    }

    suspend fun update(auth: String, menuResponse: MenuResponse, id: Int): MenuResponse {
        return withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.updateMenu(bearer, id, menuResponse) }
        }
    }

    suspend fun delete(auth: String, id: Int): MenuResponse {
        return withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.deleteMenu(bearer, id) }
        }
    }
}