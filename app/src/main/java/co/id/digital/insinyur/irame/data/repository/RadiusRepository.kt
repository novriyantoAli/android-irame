package co.id.digital.insinyur.irame.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import co.id.digital.insinyur.irame.data.datasource.UsersPagingSource
import co.id.digital.insinyur.irame.data.db.DB
import co.id.digital.insinyur.irame.data.models.*
import co.id.digital.insinyur.irame.data.network.API
import co.id.digital.insinyur.irame.data.network.SafeAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RadiusRepository(private val api: API, private val db: DB): SafeAPI() {

    suspend fun getNAS(auth: String): NASResponse {
        return withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.getNAS(bearer) }
        }
    }

    suspend fun upsertNAS(auth: String, nasname: String, secret: String): NASResponse {
        return withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.upsertNAS(bearer, nasname, secret) }
        }
    }

    suspend fun fetchRadusergroup(auth: String): List<RadusergroupResponse> {
        return withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.fetchRadusergroup(bearer) }
        }
    }

    suspend fun loadProfile(auth: String, profile: String): ProfileResponse {
        return withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.loadProfile(bearer, profile) }
        }
    }

    suspend fun saveProfile(auth: String, profile: ProfileResponse): RadusergroupResponse {
        return withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.saveProfile(bearer, profile) }
        }
    }

    suspend fun deleteRadusergroup(auth: String, username: String): RadusergroupResponse {
        return withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.deleteRadusergroup(bearer, username) }
        }
    }

    suspend fun fetchUsers(auth: String, limit: Int = 10, id: Int = 0): PageUsersResponse {
        return  withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.fetchUsers(bearer, id, limit) }
        }
    }

    fun fetchPagedUsers(): Flow<PagingData<UsersResponse>> {
        val bearer = "Bearer ${db.getUserState().getUsersSync().token}"
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { UsersPagingSource(api, bearer, "") }
        ).flow
    }

    suspend fun saveUsers(auth: String, users: UsersResponse): UsersResponse {
        return withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.saveUsers(bearer, users) }
        }
    }

    suspend fun fetchTrace(auth: String, username: String, id: Int = 0, limit: Int = 10): PageRadpostauthResponse {
        return withContext(Dispatchers.IO){
            val bearer = "Bearer $auth"
            apiRequest { api.fetchRadpostauth(bearer, username, id, limit) }
        }
    }
}