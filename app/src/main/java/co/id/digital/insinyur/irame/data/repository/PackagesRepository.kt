package co.id.digital.insinyur.irame.data.repository

import co.id.digital.insinyur.irame.data.db.DB
import co.id.digital.insinyur.irame.data.models.MessageResponse
import co.id.digital.insinyur.irame.data.models.PackageResponse
import co.id.digital.insinyur.irame.data.network.API
import co.id.digital.insinyur.irame.data.network.SafeAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PackagesRepository(private val api: API, private val db: DB): SafeAPI() {

    suspend fun fetch(auth: String): List<PackageResponse> {
        return  withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.fetchPackage(bearer) }
        }
    }

    suspend fun save(auth: String, packageResponse: PackageResponse): PackageResponse {
        return withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.storePackage(bearer, packageResponse) }
        }
    }

    suspend fun update(auth: String, id: Int, packageResponse: PackageResponse): PackageResponse {
        return withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.updatePackage(bearer, id, packageResponse) }
        }
    }

    suspend fun delete(auth: String, id: Int): MessageResponse {
        return withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.deletePackage(bearer, id) }
        }
    }
}