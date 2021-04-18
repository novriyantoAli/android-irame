package co.id.digital.insinyur.irame.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.id.digital.insinyur.irame.data.db.entities.AppState

@Dao
interface AppStateDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(appState: AppState): Long

    @Query("SELECT * FROM appstate LIMIT 1")
    fun getApp(): LiveData<AppState?>

    @Query("SELECT * FROM appstate LIMIT 1")
    fun getAppSync(): AppState?
}