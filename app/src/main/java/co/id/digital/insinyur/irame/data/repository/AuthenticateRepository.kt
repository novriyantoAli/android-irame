package co.id.digital.insinyur.irame.data.repository

import androidx.lifecycle.LiveData
import co.id.digital.insinyur.irame.data.db.DB
import co.id.digital.insinyur.irame.data.db.entities.UserState
import co.id.digital.insinyur.irame.data.network.API
import co.id.digital.insinyur.irame.data.network.SafeAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthenticateRepository(private val api: API, private val db: DB): SafeAPI() {

    suspend fun login(username: String, password: String): String? {
        return try {
            val loginResponse = apiRequest { api.login(username, password) }

            db.getUserState().upsert( UserState(username = username, token = loginResponse.token) )

            null
        } catch (e: Exception){ e.message }
    }

    suspend fun checkState(): LiveData<UserState?>{
        return withContext(Dispatchers.IO){
            db.getUserState().getUsers()
        }
    }

    suspend fun getUser(): UserState {
        return withContext(Dispatchers.IO){
            db.getUserState().getUsersSync()
        }
    }

    suspend fun logout(): Boolean {
        return withContext(Dispatchers.IO){
            try { db.getUserState().deleteUsers(); true }
            catch (e: Exception) { e.printStackTrace(); false}
        }
    }
}