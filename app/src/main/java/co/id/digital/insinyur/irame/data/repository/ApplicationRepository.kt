package co.id.digital.insinyur.irame.data.repository

import androidx.lifecycle.LiveData
import co.id.digital.insinyur.irame.data.db.DB
import co.id.digital.insinyur.irame.data.db.entities.AppState
import co.id.digital.insinyur.irame.data.db.entities.UserState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApplicationRepository(private val db: DB) {

    suspend fun getApp(): AppState? {
        return withContext(Dispatchers.IO){
            db.getAppState().getAppSync()
        }
    }

    suspend fun getAppLive(): LiveData<AppState?> {
        return withContext(Dispatchers.IO){
            db.getAppState().getApp()
        }
    }

    suspend fun saveApp(link: String, port: String): Boolean {
        return try {
            val app  = AppState(link = link, port = port)
            db.getAppState().upsert(app)

            true
        } catch (e: Exception){
            e.printStackTrace()

            false
        }
    }
}